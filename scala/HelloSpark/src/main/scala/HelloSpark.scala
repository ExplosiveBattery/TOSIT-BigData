import org.apache.spark.SparkContext

object HelloSpark {
    def main(args: Array[String]): Unit = {
        //spark ip
//        val conf = new SparkConf().setAppName("hellospark").setMaster("spark://172.18.0.6:7077")
//        val sc = new SparkContext(conf)
        val sc = new SparkContext("spark://172.18.0.6:7077","hellospark")
        sc.addJar("/media/vega/0D6C051A0D6C051A/all/Program/Scala/HelloSpark/out/artifacts/HelloSpark_jar/HelloSpark.jar")
//        print( sc.textFile("hdfs://172.18.0.2:9000/words.txt").collect.mkString("\n") )
        sc.textFile("hdfs://172.18.0.2:9000/words.txt").flatMap(_.split(" ")).map((_,1)).reduceByKey(_ + _).sortBy(_._2, false).saveAsTextFile("hdfs://172.18.0.2:9000/words_spark_idea")
//        sc.stop()
    }
}