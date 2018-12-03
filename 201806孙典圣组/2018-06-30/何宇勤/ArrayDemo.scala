package bigdata.scala.homework

import scala.collection.mutable.ArrayBuffer

object ArrayDemo {
  def main(args: Array[String]): Unit ={
    arrayBufferDemo()
    arrayMethod()
  }
  def arrayDef(): Unit = {

    var arr = new Array[Int](10)
    var arr_1 = arr.+:(2)   //在头部添加元素
    arr.++:(Array(1,2,3,4))
    for(a <- arr_1){
      println(a)
    }

    var arr_2 = Array(1,2,3,45,6)

    arr_2(2) = 1000
  }

  def arrayBufferDemo(): Unit = {
    var arrayBuffer = new Array[Int](10)
    arrayBuffer.++=(Array(1,3,4,6,7))  // ++ that , 这是可变的数组

    var fuc = (a:Int) => a * 10
    var arrayBuffer_01 = arrayBuffer.map(fuc)
    println(arrayBuffer_01)
    println(arrayBuffer)
    var filter_fuc = (a:Int) => a % 2 != 0
    var arrayBuffer_02 = arrayBuffer.filter(_ % 2 != 0) //_表示的是占位符，传入的参数，表示的是集合里面的每一个元素
    println(arrayBuffer_02)
    println(arrayBuffer)
  }

  def arrayMethod(): Unit = {
    var arr = Array[Int](2,1,6,22,7,3,9,10)

    var arr_sort01 = arr.sortBy(_ * 5) //sortBy[B](f: (A) ⇒ B)(implicit ord: math.Ordering[B]): List[A]按照应用函数f之后产生的元素进行排序

    println(arr_sort01.toList, arr.toList)

    var arr_sort02 = arr.sorted.reverse  //按照元素自身排序，并降序输出
    println(arr_sort02.toList)

    var arr_sort03 = arr.sortWith(_ > _)  //使用自定义的比较函数进行排序，比较函数boolean
    println(arr_sort03.toList)

  }

}
