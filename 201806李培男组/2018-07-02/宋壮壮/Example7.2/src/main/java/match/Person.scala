
class Person (){

  def this (name_1:String , gender_1:String){
    this ()

    this.name =name_1
    this.gender =gender_1
    println("this is second constuctor")

  }

  println("this is default constructor")
  var name:String= _
  var gender:String= _

  private  var weight:Int= _

  private  [this] var password:String="123456"

  def  requestPassword(flag:Boolean):String={
    var pwd:String=if (flag) password else  "不知道"
    pwd

  }

}


object  Person extends  App {
//  def main(args: Array[String]): Unit = {
//
////    var zhangsan =new  Person(name_1 = "zhangsan" ,gender_1 = "male")
////
////    zhangsan.weight =45
////    var password =zhangsan.requestPassword( flag = true)
////    println("your password is " +password)
////    println("What is your tizhong? "+zhangsan.weight+"kg")
////    println("your name is " + zhangsan.name)
//
//    var lisi =Person("lishi","male")
//
//    println("your name is "+ lisi.name)
//  }

  println("app")

  def apply(name :String): Person = {
    new Person()
  }

  def apply(name:String ,gender :String): Person = {
    new Person (name , gender)
  }


}

