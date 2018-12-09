import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStream {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf().setAppName("SparkStream").setMaster("local[3]")
        val streamingContext = new StreamingContext(conf, Seconds(3));
        streamingContext.checkpoint("/tmp");
        val inputStream = streamingContext.socketTextStream("localhost", 8888)
        val updateFunc = (seq: Seq[Int], op: Option[Int]) => Some(op.getOrElse(0) + seq.sum)
        val countStream = inputStream.flatMap(_.split(" ")).countByWindow(Seconds(6), Seconds(3))
        countStream.print()

        streamingContext.start()
        streamingContext.awaitTermination()
    }
}