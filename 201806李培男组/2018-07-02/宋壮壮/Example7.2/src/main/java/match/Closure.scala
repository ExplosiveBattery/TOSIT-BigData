
object Closure {

  def main(args: Array[String]): Unit = {

    var function1=closureExample(5)

    println(function1(5))

  }



  def  closureExample(x:Int):Int=>Int ={


    var i:Int =5
    var func =(y:Int)=> x + y

    func

  }
def  colihua (int: Int)(str:String)(int_01:Int):Unit={//

}

}
