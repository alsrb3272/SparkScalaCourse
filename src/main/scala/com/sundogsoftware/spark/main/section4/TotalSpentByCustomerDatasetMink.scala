package com.sundogsoftware.spark.main.section4

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{avg, round, sum}
import org.apache.spark.sql.types.{DoubleType, FloatType, IntegerType, StringType, StructType}

// Load the data/customer-orders.csv file as DataFrame with a schema
// Group by cust_id
// Sum by amount_spent (bonus : round to 2 decimal places)
// Sort by total spent
// Show the results

object TotalSpentByCustomerDatasetMink {

  case class TotalSpentByCustomer(cust_id : Int, item_id: Int, amount_spent: Double)

  def main(args:Array[String]){

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder
      .appName("TotalSpentByCustomer")
      .master("local[*]")
      .getOrCreate()

    val totalSpentByCustomerType = new StructType()
      .add("cust_id", IntegerType, nullable = true)
      .add("item_id", IntegerType, nullable = true)
      .add("amount_spent", DoubleType, nullable = true)

    import spark.implicits._
    val ts = spark.read
      .schema(totalSpentByCustomerType)
      .csv("data/customer-orders.csv")
      .as[TotalSpentByCustomer]

      val TotalSpentByCustomerContent = ts.select("cust_id", "amount_spent")

      val as = TotalSpentByCustomerContent.groupBy("cust_id")
        .agg(round(sum("amount_spent"),2).alias("total_spent"))

      val res = as.sort("total_spent")
      res.show(as.count.toInt)


  }

}
