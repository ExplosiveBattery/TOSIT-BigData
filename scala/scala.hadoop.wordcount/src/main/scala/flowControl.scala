import java.lang.Iterable
import java.io.{DataInput, DataOutput, IOException}
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io._
import org.apache.hadoop.mapred.Partitioner
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat


object flowControl {

    class Flow extends WritableComparable[Flow] {
        var upFlow,downFlow,sumFlow : Int = 0

        def this(upFlow:Int, downFlow:Int) {
            this()
            this.upFlow  = upFlow
            this.downFlow = downFlow
            this.sumFlow = upFlow+downFlow
        }

        def compareTo(o: Flow): Int = {
            return if (this.sumFlow > o.sumFlow) -1 else 1
        }

        @throws[IOException] //没有进行处理，所以才会告知抛出
        def readFields(dataInput: DataInput): Unit = {
            this.upFlow = dataInput.readInt
            this.downFlow = dataInput.readInt
            this.sumFlow = upFlow+downFlow
        }

        @throws[IOException]
        def write(dataOutput: DataOutput): Unit = {
            dataOutput.writeInt(this.upFlow)
            dataOutput.writeInt(this.downFlow)
            dataOutput.writeInt(this.sumFlow)
        }

        def +=(other: Flow): Unit = {
            this.upFlow += other.upFlow
            this.downFlow += other.downFlow
            this.sumFlow += other.sumFlow
        }

        override def toString: String = {
            return upFlow+"\t"+downFlow+"\t"+sumFlow
        }

    }

//    class FlowWritableDecreasingComparator extends  Text.Comparator {
//
//    }


//    class PhonePartitioner extends Partitioner[Text,Flow] {
//        def getPartition(key: Text, value: Flow, numPartitions: Int): Int = {
//
//        }
//    }


    //keyIn的类型还可以是Object
    class FlowControlMapper extends Mapper[LongWritable, Text, Text, Flow] {
        override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text, Flow]#Context) {
            try {
                val words = value.toString().split("\t")
                val flow = new Flow(words(words.length-3).toInt, words(words.length-2).toInt)

                context.write(new Text(words(1)), flow)
            }
            catch {
                case e: Exception => e.printStackTrace()
            }
        }
    }
    class FlowControlReducer extends Reducer[Text, Flow, Text, Text]{
        override def reduce(key: Text, value: Iterable[Flow], context: Reducer[Text, Flow, Text, Text]#Context) {
            try {
                var result = new Flow(0,0)
                value.forEach(result.+=)

                context.write(key, new Text(result.toString))
                println(key.toString+"\t"+result.toString)
            }
            catch {
                case e: Exception => e.printStackTrace()
            }
        }
    }



    def main(args: Array[String]) {
        val conf = new Configuration()

        val job = Job.getInstance(conf)
        job.setJobName("flowControl-vega")

        job.setNumReduceTasks(1)

        //如果是打包成jar文件，那么下面就需要
        //job.setJarByClass(this.getClass)
        FileInputFormat.addInputPath(job, new Path(classOf[Nothing].getClassLoader.getResource("flow.log").getPath()))
        FileOutputFormat.setOutputPath(job, new Path("hdfs://172.18.0.2:9000/output"))//如何setOutputPath是向文件末尾增加内容


        //设置Map,Combine,Reduce处理类
        job.setMapperClass(classOf[flowControl.FlowControlMapper])
//        job.setCombinerClass(classOf[flowControl.flowControlReducer])
//        job.setSortComparatorClass(classOf[FlowWritableDecreasingComparator])
        job.setReducerClass(classOf[flowControl.FlowControlReducer])

        //设置输出类型
        job.setMapOutputKeyClass(classOf[Text])
        job.setMapOutputValueClass(classOf[Flow])
        job.setOutputKeyClass(classOf[Text])
        job.setOutputValueClass(classOf[Text])


        //delete exists dir
        deleteOutDir(conf, "hdfs://172.18.0.2:9000/output")
        System.exit(if (job.waitForCompletion(true)) 0 else 1)
    }

    private def deleteOutDir(configuration: Configuration, outUrl: String): Unit = {
        val fileSystem = FileSystem.get(new URI(outUrl), configuration)
        if (fileSystem.exists(new Path(outUrl))) fileSystem.delete(new Path(outUrl), true)
    }
}

