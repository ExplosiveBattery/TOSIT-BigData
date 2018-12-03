package scdx.scala.patternmatch

object CaseDemo04 extends App{
  
  val list = List(0)
  
  list match{
    case 0::Nil => println("0 start >>>>>")
    case x:: y:: Nil => println(s"x: $x , y: $y")
    case 0::a => println(s"0 start a = $a")
    case _ => println("something else")
  }
  
}