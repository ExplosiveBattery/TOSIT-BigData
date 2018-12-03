package scdx.scala.day02

import java.io.File

import scala.io.Source

object ImplicitDemo {
  def main(args: Array[String]): Unit = {
    var list = List(1,2,3)
    list.:+(3)
    import  Context._
    implicitValues("asdg")
   var file = new File("D:\\projecthome\\bigdatahadoopmr\\scdx.scala.day02\\src\\main\\java\\scdx\\scala\\day02\\Animal.scala") // scala 和java 之间可以互相调用
    println(file.read())
  }

  def implicitValues(str:String)(implicit name_0: String) : Unit = {// 隐式导入，是通过数据类型导入
    println("this is  " + str + ":: ni hao  " + name_0)
  }

}


object Context {

  implicit var name:String = "lilei"
  implicit  def fileToRichFile(file:File):RichFile={
    new RichFile(file)
  }
}

class  RichFile(file:File){

  def read() = {

    Source.fromFile(file).mkString
  }
}