import scala.collection.mutable.Map
object map {
  def main(args: Array[String]): Unit = {
    var map = Map('a' -> 1, 'b' -> 1)
    println(map)
    println(map.empty) //1
    println( map.contains( 'a' ) ) //2
    map.put('c', 3) // 3
    println(map)
    println(map.isEmpty) //4
    map += ('d' -> 4)
    map ++= Map('e' -> 5)
    println(map)
  }
}
