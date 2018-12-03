import scala.collection.mutable.Map
object map {
  def main(args: Array[String]): Unit = {
    var map = Map('a' -> 2, 'b' -> 3)
    println(map)
    map('a') = 12
    println(map)
    map('c') = 3
    println(map)
    map += (('a', 15))
    map.put('l', 12)
    println(map)
    map += ('d' -> 16)
    println(map)
    map ++= Map('f' -> 17)
    println(map)

    val mm = Map(("a", 1), ("b", 2))
    println(mm)

  }
}