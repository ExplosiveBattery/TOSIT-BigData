import org.joda.time.DateTime

object Test {
    def main(args: Array[String]): Unit = {
//        val time = new DateTime("2004-01-01 13:31:40")
        val time = new DateTime(2004,1,1,0,1,1,1)
        println(time.getHourOfDay)
        println(time.getDayOfMonth)
        //Bytes.toBytes 转Int与转Long是不一样的结果
    }
}
