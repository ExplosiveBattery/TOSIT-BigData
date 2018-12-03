package main.scala.bigdata.scala.day01.homework72

object ImplicitDemo {

  def main(args: Array[String]): Unit = {
  var list =List(1,2,3)
    list.:+(3)
    import Context._
    implicitValues(str = "hahah")
  }


  def implicitValues(str:String)(implicit name_0: String):Unit={

    println("this is "+ str + "hello"+name_0)

  }
}

object Context
{
  implicit var name:String="lilei"

}