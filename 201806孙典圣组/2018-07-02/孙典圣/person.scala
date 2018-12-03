package main.scala.bigdata.scala.day01.homework72

class person() {

  def this(name_1:String , gender_1:String){
    //println("")
    this()
    this.name=name_1
    this.gender=gender_1
    println("this is the 2th constructor")
  }


  println("this is the default constructor")
  var name: String = _
  var gender: String = _

  private var wight: Int = _ //伴生对象

  private[this] var password:String = "000123"

  def requestPassword(flag :Boolean) : String = {

    var passw:String = if(flag) password else "unknow"
    passw
  }
}

object person extends App{
   println("app")


//  def main(args: Array[String]): Unit = {
//
//        var zhangsan = new person(name_1="zhangsan" , gender_1="male ")
//        zhangsan.wight=998
//        var password = zhangsan.requestPassword(flag = true)
//        println("你的体重是多少: " + zhangsan.wight+"kg")
//        println("your password is: " + password)
//        println("your name is "+zhangsan.name)
//
//    var lily = person("lily","female")
//    println("name: "+lily.name)
//  }

  def apply(name:String): person = {
    new person()
  }
  def apply(name:String,gender:String): person = {
    new person(name,gender)
  }

}
