package data.scdx.biadata.mr

object readme {
  def main(args: Array[String]): Unit = {
    //创建一个List
    val lst0 = List(1,7,9,8,0,3,5,4,6,2)
    //将lst0中每个元素乘以10后生成一个新的集合
    val lst1 = lst0.map(n => n*10)
    println("lst0:  " + lst0)
    println("lst1: " +lst1)
    //将lst0中的偶数取出来生成一个新的集合
    val lst0_even = lst0.filter(n => n%2==0)
    println("lst0_even: " + lst0_even)
    //println("lst0:  " + lst0)
    //将lst0排序后生成一个新的集合
    val lst0_sort = lst0.sorted
    println("lst0_sort: " + lst0_sort)
    //println("lst0:  " + lst0)
    //反转顺序
    val lst_reverse = lst0_sort.reverse
    println("lst_reverse:  " + lst_reverse)
    //println("lst0:  " + lst0)
    //将lst0中的元素4个一组,类型为Iterator[List[Int]]
    val it = lst0.grouped(4)
    //将Iterator转换成List
    val lst_from_it = it.toList
    println("lst_from_it: " + lst_from_it)
    //将多个list压扁成一个List
    val lst_merge = lst_from_it.flatten
    println(lst_merge)

    val lines = List("hello tom hello jerry", "hello jerry", "hello kitty")
    //先按空格切分，在压平 , 计算每个单词的个数
    var words = lines.flatMap(_.split(" "))
    println("words: " + words)
    val lines_03 =  words.map((_,1))
    val group = lines_03.groupBy(_._1) //按照关键字分组
    val group_01 = group.mapValues(_.foldLeft(0)(_+_._2)) //将相同关键字的数量累加
    println(group_01)



    //并行计算求和
    val lst0_sum = lst0.par.sum
    println("lst0_sum:  " + lst0_sum)

    //化简：reduce
    val lst0_sum_red = lst0.par.reduce(_ + _)
    println("lst0_sum_red:  " + lst0_sum_red)

    //将非特定顺序的二元操作应用到所有元素
    val red = lst0.reduce((x, y) => x+y)
    println("red: " + red)

    //按照特定的顺序
    val red_order = lst0.reduceLeft(_ + _)
    println("red_order: " + red_order)

    //折叠：有初始值（无特定顺序） 要并行
    val lst0_fold_1 = lst0.par.fold(123)((x, y) => x+y)
    println("lst0_fold_1: " + lst0_fold_1)

    //折叠：有初始值（有特定顺序） 不并行
    val lst0_fold_2 = lst0.foldLeft(123)((x, y) => x+y)
    println("lst0_fold_2: " + lst0_fold_2)


    //聚合
    val arr = List(List(1, 2, 3), List(3, 4, 5), List(2), List(0))
    val arr_agg = arr.aggregate(10)((a:Int, b:List[Int]) => a+b.sum, (a:Int, b:Int) => a+b)
    println("arr_agg: " + arr_agg)

    val l1 = List(5,6,4,7)
    val l2 = List(1,2,3,4)
    //求并集
    val res_union = l1.union(l2)
    println("res_union: " + res_union)

    //求交集
    val res_inter = l1.intersect(l2)
    println("res_inter: " + res_inter)

    //求差集
    val res_dif = l1.diff(l2)
    println("res_dif: " + res_dif)

  }

}
