package scdx.scala.patternmatch

object CaseDemo05 extends App {
  
  val tup = (6,3,5 )
  
  tup match{
    case (1,x,y) => println(s" hello 123 $x,$y")
    case (_,z,5) => println(z)
    case _ => println("else")
  }
  
}