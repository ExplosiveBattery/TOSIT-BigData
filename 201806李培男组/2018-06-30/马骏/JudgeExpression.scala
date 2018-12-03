package bigdata.scala.day1

object JudgeExpression {





  def main(args: Array[String]): Unit = {

    var int :Int = 2

    var int_ = if (int == 2) 3

    println("the variable int_ equal " + int_)

    var int_01 = if(int == 34 ) 3 else 5

    println("the variable int_01 equal " + int_01)

    var str = if (int == 2) 3 else "string hello"

    println("the variable str equal " + str )

    var str_01 = if(int == 2) () else if(int == 3)   3 else  "string"

    println("the variable str_01 equals" + str_01)
  }
}