object list {
  def main(args: Array[String]): Unit = {
    val nums: List[Int] = List(1, 2, 3, 4)
    println(nums.min) //1
    println(nums.max) //2
    println( nums.mkString("{",",","}") ) //3
    println(nums.drop(1)) //4
    println(nums)
    val nums2 = List.fill(10)(2)
    println(nums2)
  }
}