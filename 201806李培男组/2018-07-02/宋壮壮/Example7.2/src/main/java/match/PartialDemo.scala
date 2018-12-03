
object PartialDemo extends App {

  println(partialFunct(2))

  def  partialFunct:PartialFunction[Int,Int]={
    case 1=>1
    case _ =>3
  }




  def partialYinyongFun(int:Int ,int_01 :   Int , int_02:Int):Int={

    int + int_01 +int_02

  }

  var parYingyong =partialYinyongFun( int =  1 , int_01 = 2, _:Int)   ;

  println(parYingyong(3))

}
