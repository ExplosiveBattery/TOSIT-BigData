import scala.collection.mutable.HashSet
object set {
  def main(args: Array[String]): Unit = {
    val set = new HashSet[Int]()
    set += 2
    println(set)
    set.add(3)
    set ++= Set(3,4,5)
    println(set)
    set -= 5
    set.remove(2)
    println(set)

}
