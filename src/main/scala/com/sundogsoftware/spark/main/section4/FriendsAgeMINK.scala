package com.sundogsoftware.spark.main.section4

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark._
import org.apache.spark.sql.functions.{avg, round}

object FriendsAgeMINK {

  case class Person(id: Int, name : String, age : Int, friends : Int)

  def main(args : Array[String]): Unit ={

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder
      .appName("FriendAge")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    val people = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/fakefriends.csv")
      .as[Person]

    val friendsAge = people.select("age", "friends")

    println("나이별 평균친구")
    friendsAge.groupBy("age").avg("friends").show()

    println("나이로 정렬된 평균친구")
    friendsAge.groupBy("age").avg("friends").sort("age").show()

    println("평균 소수점 2이하로 반올림한 그룹화한 해당 나이")
    friendsAge.groupBy("age").agg(round(avg("friends"),2)).sort("age").show()

    println("컬럼이름 변경")
    friendsAge.groupBy("age").agg(round(avg("friends"),2).alias("friends_avg")).sort("age").show()

    spark.stop()
  }
}
