object tupple {
  def main(args: Array[String]): Unit = {
    var t = (1, 3.14, "pai")
    println(t)
    println(t.toString()) //1
    println(t._1 + t._2) //2


    val t2 = new Tuple2("www.google.com", "www.runoob.com") //3
    println("交换后的元组: " + t2.swap ) //4

    val t3 = new Tuple3(1, "hello", Console) //5


  }
}


