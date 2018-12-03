

class Bird extends  LowAnimal with Fly with Luansheng {

  println("bird..............")
  override def run(): Unit = {}
}//从new的地方往上开始找，一直找到父类，从最高的父类开始往下初始化，遇到with了无从左往右处刷，走后才是初始化的Bird


object  Bird extends  App {

  println(new Bird())

}