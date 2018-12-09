import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQL {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setMaster("spark://172.18.0.6:7077").setAppName("CheckPoint").setJars(Array("/media/vega/0D6C051A0D6C051A/all/Program/Scala/SparkSQL/out/artifacts/SparkSQL_jar/SparkSQL.jar"))
        val sc = new SparkContext(conf)
        val sqlContext = SparkSession.builder.config(conf).getOrCreate()

        val schema = StructType(List(
            StructField("id" , IntegerType , false),
            StructField("name" ,StringType,true),
            StructField("age" ,IntegerType,true)
        ))
        val recordRDD = sc.parallelize(List(List("0","vega","21"),List("1","Battery","21")))
        //spark.read.json 也可以从本机读取json数据
        val personRDD=recordRDD.map(x => Row(x(0).toInt , x(1).toString,x(2).toInt))


        val personDF = sqlContext.createDataFrame(personRDD,schema)
        //将dataframe注册成为临时表
        personDF.createOrReplaceTempView("person")
        // 使用sql的方式
        personDF.show
        sqlContext.sql("select * from person where age> 20 order by age desc").show()
        sc.stop
    }
}