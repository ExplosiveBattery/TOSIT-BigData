package scdx.scala.day02

object PartialDemo extends App {

 println(partialFunct(2))

  def partialFunct:PartialFunction[Int,Int] ={ // 这个是偏函数
    case 1 => 1
    case _ => 3

  }


  //偏应用函数

  def partialYinyongFun(int:Int , int_01 :  Int ,int_02:Int): Int = {
    int + int_01 + int_02


  }


  var parYinyong = partialYinyongFun(1,2,_:Int) ; // 这种用法就是偏应用函数

  println(parYinyong(3))
}
