package com.sundogsoftware.spark.main

import org.apache.log4j._
import org.apache.spark._


  /*
  누가 가장 큰 손이
  총금액에따라서 정렬하기
  포맷해서 소수점 두자리까지 보이게
  총금액에 따라 결과값 정리리
  */

object TotalAmountSpentSortMink {

  def amountSpentMink(line:String):(Int, Float) = {
    val fields = line.split(",")
    val customerID = fields(0).toInt
    val dollar_total = fields(2).toFloat
    (customerID, dollar_total)
  }



  def main(args: Array[String]): Unit ={

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "TotalAmountSpentMink")

    val input = sc.textFile("data/customer-orders.csv")
    val appendInput = input.map(amountSpentMink)

    val totalByCustomer = appendInput.reduceByKey((x,y) => x + y)

    // values -> key , key -> values 로 뒤집음
    // 총금액으로 정렬
    val a1 = totalByCustomer.map(x => (x._2, x._1)).sortByKey()

    val result = a1.collect()

    result.foreach(println)
    }
}
