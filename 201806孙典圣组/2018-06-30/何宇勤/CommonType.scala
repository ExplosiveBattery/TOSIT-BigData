package bigdata.scala.homework

object CommonType {
  def main(args: Array[String]): Unit = {
    // 1
    var byte :Byte = 12
    println("the variable byte equal " + byte)

    var char :Char = 'a'
    println("the variable byte equal " + char)

    var short :Short = 3100
    println("the variable byte equal " + short)

    var int :Int = 10
    var float :Float = 233.33f
    var double :Double = 233.33333
    var long :Long = 23333333

    //  2
    var boolean :Boolean = true
    println("the variable byte equal " + boolean)

    var string :String = "aaaaaaaaaa"

    var unit :Unit = ()
    println("the variable byte equal " + unit)

    var null_ = null

    //  3
    var any :Any = 12354
    var any_01 :Any = ""
    var any_02:Any = 233.333f  // 顶级类，相当于java的Object
    println("the variable byte equal " + any_02)

    var anyRef :AnyRef = "aaaaa"

    var anyVal :AnyVal = 10
  }

}
