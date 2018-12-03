package scdx.scala.day2

object OptionDemo {
  var map = Map('a' -> 1)

  println(map.getOrElse('c',2))
}
