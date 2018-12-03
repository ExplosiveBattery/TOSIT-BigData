object array {
  def main(args: Array[String]): Unit = {
    var arr =Array(1,2,3,4,5,6)
    println(arr.length)
    var arr2=arr.map((x:Int)=>x+2)
    println(arr2.toBuffer)
    var arr3=arr.filter(_%3==0)
    println(arr3.toBuffer)
    println(arr.sortBy(x=>x-1).toBuffer)
    println(arr.sortWith(_ > _).toBuffer)
    println(arr.filter(_%2==0).map((x:Int)=>x+3).toBuffer)
  }

}
