import Array._

object array {
  def main(args: Array[String]): Unit = {
    var arr = Array.iterate(0,3)(a=>a+1) //1
    println(arr.length) //2
    println(arr.toBuffer) //3
    var arr2=arr.map((x:Int)=>x+3) //4
    println(arr2.toBuffer)
    println(  concat(arr, arr2 ).toBuffer ) //5
//    arr.mkString()  mkString这个函数很棒，高级版toString
  }
}
