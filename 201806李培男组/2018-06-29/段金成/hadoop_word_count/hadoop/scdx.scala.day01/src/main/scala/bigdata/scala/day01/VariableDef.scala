package bigdata.scala.day01

object VariableDef {

  def main(args: Array[String]): Unit = {
    /**
      * scala 里面定义变量有2种，一种可变的， 一种不可变的
      */
     // 1.
    val i : Int  = 9 ;
     print("this variable i equal " + i )
    // i = 0 ; 这种变量是不能够改动


    //  2
    var ii  : String  = "hello var"

    println("this varable ii equal " + ii )

    ii = "............."

    var iii  = "yes "

    var int = 23423
  }

}
