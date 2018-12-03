package bigdata.scala.day01

object CommonType {

  def main(args: Array[String]): Unit = {
    /**
      * 总共有3组
      */
  // 1

    var byte :Byte = 12
    println("the variable byte equal " + byte)
    var char:Char = 'a'
    var short :Short = 3434


    println("the variable char equal " + char )
    var int : Int = 343
    var float :Float = 234.34f // 需要加上一个f

    var long :Long = 344353454
    var double :Double = 234.45435

   // 2
    var boolean :Boolean = true
    println("the variable boolean equal " + boolean)

    var string :String = "sdfsdfsd"

    var unit :Unit = ()
    println("the variable unit equal " + unit)
    var null_ = null

    // 3

    var any :Any = 3243
    var any_01 :Any = ""
    var any_02:Any = 343.544f ///z这个是scala语言里面的顶级类，相当于java 里面的Object

    var anyRef :AnyRef = "sdgsdf"

    var anyVal :AnyVal = 34

  }
}
