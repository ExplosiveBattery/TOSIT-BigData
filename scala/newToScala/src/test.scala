object test {
  def m(x: Int) = x + 3
  val f = (x: Int) => x + 3
  def main(args: Array[String]): Unit = {
    println(m(1)+f(1)) //8
  }
}

