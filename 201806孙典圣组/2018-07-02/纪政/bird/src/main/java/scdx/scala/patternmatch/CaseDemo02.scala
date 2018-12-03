package scdx.scala.patternmatch

object CaseDemo02 extends App{
  import scala.util.Random
  val arr = Array("hello" , 1 ,-2.0, CaseDemo02)
  val element = arr(Random.nextInt(arr.length))
  
  println(element)
  
  element match{
    
    case x:Int => println("Int .......")
    case y: Double if (y>=0 ) => println("Double .........")
    case z:String => println("String ............")
    case _ => throw new Exception("meiyoupipei")
  }
  
}