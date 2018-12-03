package main.scala.bigdata.scala.day01.homework72

object Closure {

  def main(args: Array[String]): Unit = {
    var function1 = partialAppFun(5)

    println(function1(5))
    colia(int =2)(str="hello")(int_01 = 3)

  }

 def partialAppFun(x:Int)={
   var i : Int=5

   var func=(y:Int)=>x+y

    func


 }

  def colia( int: Int)( str: String)(int_01:Int ):Unit = {

    println(int+str+int_01)


  }
}
