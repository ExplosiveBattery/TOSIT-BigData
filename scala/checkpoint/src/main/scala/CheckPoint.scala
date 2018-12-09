import org.apache.spark.SparkContext

object CheckPoint {
    def main(args: Array[String]): Unit = {
        val sc = new SparkContext("spark://172.18.0.6:7077","CheckPoint")
        sc.addJar("/media/vega/0D6C051A0D6C051A/all/Program/Scala/checkpoint/out/artifacts/checkpoint_jar/checkpoint.jar")
        sc.setCheckpointDir("hdfs://172.18.0.2:9000/")

        val rdd = sc.parallelize(List("1","2","3","4","5","1","5","3"),3).map((_,1)).reduceByKey(_+_)
        println(rdd.isCheckpointed)
        rdd.checkpoint // mark this RDD for checkpointing
        println(rdd.isCheckpointed)
        rdd.cache // Memory_Only persist
        println(rdd.isCheckpointed)
        rdd.collect
        println(rdd.isCheckpointed)
        println(rdd.getCheckpointFile)
    }
}