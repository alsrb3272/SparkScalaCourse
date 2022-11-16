package com.sundogsoftware.spark.main

import org.apache.spark._
import org.apache.log4j._


object TotalAmountSpentMink {
  // 고객별 주문한 금액의 합 구하기

  // Strategy
  // add up amount spent by customer
  // Split each comma-delimited line into fields
  // Map each line to key/value pairs of customer ID and dollar amount
  // Use reduceByKey to add up amount spent by customer ID
  // collect() the results and print them

  def amountSpentMink(line:String):(Int, Float) = {
    val fields = line.split(",")
    (fields(0).toInt, fields(2).toFloat)
  }

  def main(args: Array[String]){

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "TotalAmountSpentMink")

    val lines = sc.textFile("data/customer-orders.csv")
    val parsedLines = lines.map(amountSpentMink)

    val Station = parsedLines.reduceByKey((x,y) => x + y)
    val result = Station.collect()

    result.sorted.foreach(println)
  }


}
