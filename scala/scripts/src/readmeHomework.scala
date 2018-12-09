object readmeHomework {
    def main(args: Array[String]): Unit = {
        //创建一个List
        val lst0 = List(1,7,9,8,0,3,5,4,6,2)
        //将lst0中每个元素乘以10后生成一个新的集合
        val lst1 = lst0.map( (i:Int)=>i*10 )
        println(lst1)
        //将lst0中的偶数取出来生成一个新的集合else Unit
        val lst2 = lst0.filter(_%2==0)
        println(lst2)
        //将lst0排序后生成一个新的集合
        val lst3 = lst0.sorted
        println(lst3)
        //反转顺序
        println( lst0.reverse )
        //将lst0中的元素4个一组,类型为Iterator[List[Int]]
        println( lst0.grouped(4) )
        //将Iterator转换成List
        println( lst0.grouped(4).toList )
        //将多个list压扁成一个List
        println( lst0.grouped(4).toList.flatten )

        val lines = List("hello tom hello jerry", "hello jerry", "hello kitty")
        //先按空格切分，在压平 , 计算每个单词的个数
        //lines.map(_.split(" ")).flatten
        println( lines.flatMap(_.split(" ")) )
        println( lines.flatMap(_.split(" ")).length )
    }
}
