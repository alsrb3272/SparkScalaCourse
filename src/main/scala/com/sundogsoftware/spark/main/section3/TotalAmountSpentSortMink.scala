package com.sundogsoftware.spark.main.section3

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object TotalAmountSpentSortMink {

  def amountSpentMink(line: String): (Int, Float) = {
    val fields = line.split(",")
    val customerID = fields(0).toInt
    val dollar_total = fields(2).toFloat
    (customerID, dollar_total)
  }


  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "TotalAmountSpentMink")

    val input = sc.textFile("data/customer-orders.csv")
    val appendInput = input.map(amountSpentMink)

    val totalByCustomer = appendInput.reduceByKey((x, y) => x + y)

    // values -> key , key -> values 로 뒤집음
    // 총금액으로 정렬
    val a1 = totalByCustomer.map(x => (x._2, x._1)).sortByKey()

    val result = a1.collect()

    result.foreach(println)
  }
}
