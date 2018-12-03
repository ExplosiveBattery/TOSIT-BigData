package main.scala.bigdata.scala.day01.homework72

class Bird extends LowAnimal with Fly with egg {

//Map()
println("Bird")
override def run(): Unit = {}

}
object Bird extends  App {

  println(new Bird())
}