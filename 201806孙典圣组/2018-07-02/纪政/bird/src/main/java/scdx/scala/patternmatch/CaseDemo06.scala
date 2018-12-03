package scdx.scala.patternmatch

import scala.util.Random
/*
 * 演示样例类

 */
case class SubmitTask(id: String, name: String) // 样例类主要用于模式匹配
case class HeartBeat(time: Long)
case object CheckTimeOutTask

object CaseDemo06 extends App{

  //println(HeartBeat(12345).time)
  // 样例类初始化可以不用NEW ， 也不推荐使用new
  val arr = Array(CheckTimeOutTask, HeartBeat(1234) , SubmitTask("001","task-001"))
  //Random.nextInt(arr.length)
  val case_ = arr(Random.nextInt(arr.length))
  
  case_ match {
    // 疑惑点： 这里的SubmitTask是实例对象吗 ？
    case SubmitTask("001", name) => println(s"submittask  id ,$name ....")
    
    case HeartBeat(time) => println(s"heartbeat $time ...............")
    
    case CheckTimeOutTask => println(s"CheckTimeOutTask .............")
  }
}