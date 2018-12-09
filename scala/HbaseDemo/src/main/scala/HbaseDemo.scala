import com.alibaba.fastjson.JSON
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.joda.time.DateTime

object HbaseDemo {
    def main(args: Array[String]): Unit = {
        /* Get data from kafka by a consumer */
        val sparkConf = new SparkConf().setMaster("local[3]").setAppName("FromKafka")//.setJars()
        val ssc = new StreamingContext(sparkConf, Seconds(3))
        val kafkaParams = Map[String, Object](
            "bootstrap.servers" -> "172.18.0.10:9092", //阻塞在poll ,192.168.27.102:9092,192.168.27.103:9092,192.168.27.104:9092
            //      "zookeeper.connect" -> "192.168.27.101:2181,192.168.27.102:2181,192.168.27.103:2181,192.168.27.104",
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> "testGroupID",
            "auto.offset.reset" -> "latest",
            "enable.auto.commit" -> (false: java.lang.Boolean)
        )
        val topics = Array("test")
        val stream = KafkaUtils.createDirectStream[String, String](
            ssc,
            PreferConsistent,
            Subscribe[String, String](topics, kafkaParams)
        )

        //local[3]改为spark://192.168.27.101:7077
        //bootstrap.servers 与 zookeeper参数真的是一样的吗？
        //居然不是靠回调函数将处理存储起来，那么是如何判断是哪部分是处理代码？
        //建表的时候设置好region个数，优化？
        /* prepare for hbase */

        /* handle data */
        val jsonRDDs = stream.map(record =>record.value)
//        jsonRDDs.foreachRDD(_.foreach(json=>{
//
//        }))
        jsonRDDs.foreachRDD(_.foreachPartition(iter =>{
            val conf: Configuration = HBaseConfiguration.create
            conf.set("hbase.zookeeper.quorum","172.18.0.2")
            conf.set("hbase.rootdir", "hdfs://172.18.0.2:9000/hbase")
            val conn =ConnectionFactory.createConnection(conf)
            val admin  = conn.getAdmin


            iter.foreach(json=>{
                val log = JSON.parseObject(json,classOf[Log])
                val dateArray = log.getDay.split("-") //2017-03-01 我这手字符串转换效率应该比Joda快
                val month = dateArray(0).concat(dateArray(1)) //201703
                log.setDay(month.concat(dateArray(2))) //20170301


                /* Create tables  via sync */
//                if( !admin.tableExists(TableName.valueOf("behavior_user_day_time_"+month)) ) {
//                    println("1=======================================================================================================================")
//                    admin.createTable(TableDescriptorBuilder.newBuilder(TableName.valueOf("behavior_user_app_"+month)).setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("timeLen")).build()).build())
//                    admin.createTable(TableDescriptorBuilder.newBuilder(TableName.valueOf("behavior_user_hour_time_"+month)).setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("timeLen")).build()).build())
//                    admin.createTable(TableDescriptorBuilder.newBuilder(TableName.valueOf("behavior_user_day_time_"+month)).setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("timeLen")).build()).build())
//                    admin.createTable(TableDescriptorBuilder.newBuilder(TableName.valueOf("behavior_user_hour_app_time_"+month)).setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("timeLen")).build()).build())
//
//                }else {
//                    println("2=======================================================================================================================")
//                }


                /* record data */
                updateAppTable(conn.getTable(TableName.valueOf("behavior_user_app_"+month)),log)
                updateHourTimeTable(conn.getTable(TableName.valueOf("behavior_user_hour_time_"+month)),log)
                updateDayTime(conn.getTable(TableName.valueOf("behavior_user_day_time_"+month)),log)
                updateHourAppTime(conn.getTable(TableName.valueOf("behavior_user_hour_app_time_"+month)),log)
            })
            conn.close()
        }))


        // Start the computation
        ssc.start()
        ssc.awaitTermination()
    }


    /*存入数据库的时间都是Int，但是是秒*/

    /*此函数需要累加
    * 此函数因为数据随机可能发生溢出 */
    def updateAppTable(table:Table, log:Log): Unit = {
        val rowKey=log.getUserid.toString+":"+log.getDay
        /* get data */
        val result = table.get(new Get(Bytes.toBytes(rowKey)))
        for( appuse <- log.getData ) {
            /* get exists data */
            val value = result.getValue(Bytes.toBytes("timeLen"),Bytes.toBytes("timeLen."+appuse.getPackage))
            val alreadyActiveTime = if(value==null) 0 else Bytes.toInt(value)
            /* write new data */
            val put = new Put(Bytes.toBytes(rowKey))
            val allTime = if(Int.MaxValue-alreadyActiveTime<(appuse.getActivetime/1000).toInt) Int.MaxValue else alreadyActiveTime+(appuse.getActivetime/1000).toInt //处理溢出
            put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes("timeLen."+appuse.getPackage), Bytes.toBytes(allTime))
            table.put(put)
        }
        table.close
    }

    /*
    * 考虑累加
    * 但是因为数据是随机生成的所以可能同一时间段的数据会有多次，不然按照时间递进，不存在对统一时间段的重复编辑。需要设置一个上限。*/
    def updateHourTimeTable(table:Table, log:Log): Unit = {
        val rowKey=log.getUserid.toString+":"+log.getDay
        val beginTime = new DateTime(log.getBegintime)
        val endTime = new DateTime(log.getEndtime)
        val result = table.get(new Get(Bytes.toBytes(rowKey)))

        if( beginTime.getHourOfDay==endTime.getHourOfDay ) {
            val value = result.getValue(Bytes.toBytes("timeLen"),Bytes.toBytes(beginTime.getHourOfDay))
            val alreadyTime = if(value==null) 0 else Bytes.toInt(value)
            var allTime: Int = alreadyTime+((endTime.getMillis-beginTime.getMillis)/1000).toInt
            allTime = if(allTime>=3600) 3600 else allTime //处理溢出

            val put = new Put(Bytes.toBytes(rowKey))
            put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes(beginTime.getHourOfDay), Bytes.toBytes(allTime))
            table.put(put)
        }else {
            for( hour <- beginTime.getHourOfDay to endTime.getHourOfDay  ) {
                var secondOfHour = 0
                if(hour==beginTime.getHourOfDay) {
                    secondOfHour = 3600-beginTime.getMinuteOfHour*60-beginTime.getSecondOfMinute
                    val value = result.getValue(Bytes.toBytes("timeLen"),Bytes.toBytes(hour))
                    val alreadyTime = if(value==null) 0 else Bytes.toInt(value)
                    secondOfHour = alreadyTime+secondOfHour
                    secondOfHour = if(secondOfHour>=3600) 3600 else secondOfHour //处理溢出
                }else if(hour<endTime.getHourOfDay){
                    secondOfHour = 3600
                }else {
                    secondOfHour = endTime.getMinuteOfHour*60+endTime.getSecondOfMinute
                }
                val put = new Put(Bytes.toBytes(rowKey))
                put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes(hour), Bytes.toBytes(secondOfHour))
                table.put(put)
            }
        }
        table.close
    }

    /*
    * 考虑累加
    * 但是因为数据是随机生成的所以可能同一时间段的数据会有多次，不然按照时间递进，不存在对统一时间段的重复编辑。需要设置一个上限。*/
    def updateDayTime(table:Table, log:Log): Unit = {
        val rowKey=log.getUserid.toString
        val put = new Put(Bytes.toBytes(rowKey))
        var allTime = ((log.getEndtime-log.getBegintime)/1000L).toInt
        allTime = if(allTime>=24*3600) 24*3600 else allTime //处理溢出
        put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes("timeLen."+log.getDay.substring(6,8)), Bytes.toBytes(allTime))
        table.put(put)
        table.close
    }

    /*
    * 本来这个函数也应该考虑累加，但是容易导致在同一小时不同packagename总和大于一小时
    * 因为数据是随机生成的所以可能同一时间段的数据会有多次，不然按照时间递进，不存在对统一时间段的重复编辑
    * 所以采取复写，而这又会导致数据上的不匹配 */
    def updateHourAppTime(table:Table, log:Log): Unit = {
        for( appuse <- log.getData ) {
            val rowKey=log.getUserid.toString+":"+log.getDay+":"+appuse.getPackage
            val beginTime = new DateTime(log.getBegintime)
            val endTime = new DateTime(log.getEndtime)

            for( hour <- beginTime.getHourOfDay to endTime.getHourOfDay  ) {
                val put = new Put(Bytes.toBytes(rowKey))
                put.addColumn(Bytes.toBytes("timeLen"),Bytes.toBytes("timeLen."+hour), Bytes.toBytes(((log.getEndtime-log.getBegintime)/1000L).toInt))
                table.put(put)
            }
        }
    }
}