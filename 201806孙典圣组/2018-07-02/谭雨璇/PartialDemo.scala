package main.scala.bigdata.scala.day01.homework72

object PartialDemo extends App {

  println(partialFunct(2))

  def partialFunct: PartialFunction[Int, Int] = {

    case 1 => 1
    case _ => 3
  }

  def partialAppFun(int: Int, int_01: Int, int_02: Int):

  Int = {

    int + int_01 + int_02
  }


  var parApp = partialAppFun(int = 1, int_01 = 2, _: Int);

    println(parApp(3)
  )

}