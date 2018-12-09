package scdx.scala.day02

object Closure {
  def main(args: Array[String]): Unit = {
   var function1 = closureYanshi(5)

    println(function1(5))

    colihua(2)("hello ")(3)
  }

  def closureYanshi(x:Int) ={
    var i :Int = 5

    var func = (y:Int) => x + y

     func
  }


  def colihua(int:Int)(str:String)(int_01:Int): Unit ={ //柯里化

    println(int + str + int_01)
  }
}
