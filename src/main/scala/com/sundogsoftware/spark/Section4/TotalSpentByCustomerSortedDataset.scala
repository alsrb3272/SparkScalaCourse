package com.sundogsoftware.spark.Section4

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{round, sum}
import org.apache.spark.sql.types.{DoubleType, FloatType, IntegerType, StructType}

// Load the data/customer-orders.csv file as DataFrame with a schema
// Group by cust_id
// Sum by amount_spent (bonus : round to 2 decimal places)
// Sort by total spent
// Show the results

object TotalSpentByCustomerSortedDataset {

  case class CustomerOrders(cust_id : Int, item_id: Int, amount_spent: Double)

  def main(args:Array[String]){

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder
      .appName("TotalSpentByCustomer")
      .master("local[*]")
      .getOrCreate()

    val customerOrdersSchema = new StructType()
      .add("cust_id", IntegerType, nullable = true)
      .add("item_id", IntegerType, nullable = true)
      .add("amount_spent", DoubleType, nullable = true)

    import spark.implicits._
    val customerDS = spark.read
      .schema(customerOrdersSchema)
      .csv("data/customer-orders.csv")
      .as[CustomerOrders]


    val totalByCustomer =
       customerDS.groupBy("cust_id")
      // 고객의 총 비용을 합한 값을 소수점 2자리까지 표출
      // round 평균
      // agg Method: 여러 집계 처리 한번에 지정 가능, 집계에 표현식 사용 가능
         // 단일 열 합 집계
        .agg(round(sum("amount_spent"),2)
      // 컬럼 이름 변경하기
        .alias("total_spent"))

    val totalByCustomerSorted = totalByCustomer.sort("total_spent")

    totalByCustomerSorted.show(totalByCustomer.count.toInt)

  }

}
