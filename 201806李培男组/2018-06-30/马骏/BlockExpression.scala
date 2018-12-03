package bigdata.scala.day1

object BlockExpression {


  def main(args: Array[String]): Unit = {
    var blockExpression = {var a  = 1 ; a = a+5 ;  a + "hellow "}

    println("the variable blockExpression equals " + blockExpression)
  }
}