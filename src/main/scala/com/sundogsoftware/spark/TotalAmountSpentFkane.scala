package com.sundogsoftware.spark

object TotalAmountSpentFkane {

  // Split each comma-delimited line into fields

  // Map each line to key/value pairs of customer ID and dollar amount
  // Use reduceByKey to add up amount spent by customer ID
  // collect() this results and print them

  // Review Previous examples
  // Split comma-delimited fields:
  // val fields = line.split(",")
  // Treat field 0 as an integer, and field 2 as a floating point number:
  // (fields (0).toInt, fields(2).toFloat)
}
