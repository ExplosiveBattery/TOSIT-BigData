package scdx.scala.patternmatch

object CaseDemo03 extends App{
  val arr = Array(0,1,5)
  
  arr match{
    
    case Array(0,x,y) => println(x +" " + y )
    case Array(0,1,5) => println("0 , 1, 5")
    case Array(0,_*) => println("_____*")
  }
  
}