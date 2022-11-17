package com.sundogsoftware.spark.main.section3

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

// 내가 생각한 해결방안
object TotalAmountSpentMink {
  // 고객별 주문한 금액의 합 구하기

  // Strategy
  // add up amount spent by customer
  // Split each comma-delimited line into fields
  // Map each line to key/value pairs of customer ID and dollar amount
  // Use reduceByKey to add up amount spent by customer ID
  // collect() the results and print them

  def amountSpentMink(line: String): (Int, Float) = {
    val fields = line.split(",")
    val customerID = fields(0).toInt
    val dollar_total = fields(2).toFloat
    (customerID, dollar_total)
  }

  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "TotalAmountSpentMink")

    val lines = sc.textFile("data/customer-orders.csv")
    val parsedLines = lines.map(amountSpentMink)

    val Station = parsedLines.reduceByKey((x, y) => x + y)
    val result = Station.collect()

    result.sorted.foreach(println)
  }


}
