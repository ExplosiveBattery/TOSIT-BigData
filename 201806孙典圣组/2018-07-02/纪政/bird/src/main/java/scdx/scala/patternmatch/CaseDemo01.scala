package scdx.scala.patternmatch

object CaseDemo01 extends App {
  import scala.util.Random
  val arr = Array("zhangsan" , "lisi" ,"wangwu")
  
  val name = arr(Random.nextInt(arr.length))
  
  println(name)
  
  name match{
    case "zhangsan" => println("zhangsan laoshi hao .......")
    case "lisi" => println("lisi laoshi hao .....")
    case _ => println("wangwulaoshihao ..........")
  }  
}