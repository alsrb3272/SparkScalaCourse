package com.sundogsoftware.spark.Section4

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object SparkSQLDataset {

  // 클래스를 압축적으로 정의
  case class Person(id: Int, name: String, age: Int, friends: Int)

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Use SparkSession interface

    val spark = SparkSession
      .builder
      .appName("SparkSQL")
      .master("local[*]")
      .getOrCreate()

    // Load each line of the source data into an Dataset
    // header 로우는 클래스를 압축했던 내용과 일치해야한다.
    import spark.implicits._
    val schemaPeople = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/fakefriends.csv")
    // 더 나은 컴파일과 파일 오류를 활용할 수 있는 방법
      .as[Person]

    schemaPeople.printSchema()

    // People로 구성
    schemaPeople.createOrReplaceTempView("people")

    // SQL 쿼리문으로 나이 13~19세 조회하기
    val teenagers = spark.sql("SELECT * FROM people WHERE age >= 13 AND age <= 19")

    val results = teenagers.collect()

    results.foreach(println)

    spark.stop()
  }
}
