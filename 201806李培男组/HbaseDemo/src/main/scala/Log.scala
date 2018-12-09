import com.alibaba.fastjson.annotation.JSONField

class AppUse {
  @JSONField(name="package")
  private[this] var packageName = "com.app"
  def getPackage=packageName
  def setPackage(packageName:String)=this.packageName=packageName

  private[this] var activetime = 0L
  def getActivetime=activetime
  def setActivetime(activetime:Long)=this.activetime=activetime
}

class Log {
  private[this] var userid = 0
  def getUserid=userid
  def setUserid(userid:Int)=this.userid=userid

  private[this] var day = "1997-10-30"
  def getDay=day
  def setDay(day:String)=this.day=day

  private[this] var begintime,endtime=878140800000L
  def getBegintime=begintime
  def setBegintime(begintime:Long)=this.begintime=begintime
  def getEndtime=endtime
  def setEndtime(endtime:Long)=this.endtime=endtime

  private[this] var data:Array[AppUse] = Array()
  def getData=data
  def setData(data:Array[AppUse])=this.data=data
}

