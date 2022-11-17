package com.sundogsoftware.spark.Section3

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/** Compute the average number of friends by age in a social network. */
object FriendsByAge {

  /** A function that splits a line of input into (age, numFriends) tuples. */
  def parseLine(line: String): (Int, Int) = {
    // Split by commas
    // 콤마로 나뉜 value값이므로 각 줄에 구분
    val fields = line.split(",")
    // Extract the age and numFriends fields, and convert to integers
    // 필드 2,3 인트형태로 저장
    val age = fields(2).toInt
    val numFriends = fields(3).toInt
    // Create a tuple that is our result.
    // 튜플 생성
    (age, numFriends)
  }

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    // 로그 스팸을 받아내지 않기위해서
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkContext using every core of the local machine
    // 모든 CPU 코어를 이용해서 local machine 실행 설정하고 앱 이름 지정
    val sc = new SparkContext("local[*]", "FriendsByAge")

    // Load each line of the source data into an RDD
    // 자료 호출 RDD
    val lines = sc.textFile("data/fakefriends-noheader.csv")

    // Use our parseLines function to convert to (age, numFriends) tuples
    val rdd = lines.map(parseLine)

    // Lots going on here...
    // We are starting with an RDD of form (age, numFriends) where age is the KEY and numFriends is the VALUE
    // We use mapValues to convert each numFriends value to a tuple of (numFriends, 1)
    // Then we use reduceByKey to sum up the total numFriends and total instances for each age, by
    // adding together all the numFriends values and 1's respectively.
    // key는 age이며 친구 수를 value로 설정하여 얼마나 많은 사람이 나이대에 있는 지 mapValues를 통해 RDD저장
    val totalsByAge = rdd.mapValues(x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))

    // So now we have tuples of (age, (totalFriends, totalInstances))
    // To compute the average we divide totalFriends / totalInstances for each age.
    // mapValeus를 통해 친구 수와 총 사람 수를 key마다 가진 친구의 수로 저장
    val averagesByAge = totalsByAge.mapValues(x => x._1 / x._2)

    // Collect the results from the RDD (This kicks off computing the DAG and actually executes the job)
    val results = averagesByAge.collect()

    // Sort and print the final results.
    results.sorted.foreach(println)
  }

}
