package com.sundogsoftware.spark.Section4

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructType}
import org.apache.spark.sql.functions._

/** Count up how many of each word occurs in a book, using regular expressions and sorting the final results */
object WordCountBetterSortedDataset {

  case class Book(value: String)

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkSession using every core of the local machine
    val spark = SparkSession
      .builder
      .appName("WordCount")
      .master("local[*]")
      .getOrCreate()

    // Read each line of my book into an Dataset
    import spark.implicits._
    val input = spark.read.text("data/book.txt").as[Book]

    // Split using a regular expression that extracts words
    // 텍스트를 한열에 저장하는 방식.
    // 모든단어가 자체 행에 있는 단어 열이 있는 새 dataset에 있어야함

    val words = input
      .select(explode(split($"value", "\\W+")).alias("word"))
      .filter($"word" =!= "")

    // Normalize everything to lowercase
    // 소문자로 변경
    val lowercaseWords = words.select(lower($"word").alias("word"))

    // Count up the occurrences of each word
    // 고유한 단어들을 그룹화
    val wordCounts = lowercaseWords.groupBy("word").count()

    // Sort by counts
    val wordCountsSorted = wordCounts.sort("count")

    // Show the results.
    wordCountsSorted.show(wordCountsSorted.count.toInt)


    // ANOTHER WAY TO DO IT (Blending RDD's and Datasets)
    // 대체 접근 방식.
    val bookRDD = spark.sparkContext.textFile("data/book.txt")
    //
    val wordsRDD = bookRDD.flatMap(x => x.split("\\W+"))
    // RDD를 Dataset으로 변경
    val wordsDS = wordsRDD.toDS()

    val lowercaseWordsDS = wordsDS.select(lower($"value").alias("word"))
    val wordCountsDS = lowercaseWordsDS.groupBy("word").count()
    val wordCountsSortedDS = wordCountsDS.sort("count")
    wordCountsSortedDS.show(wordCountsSortedDS.count.toInt)

  }
}
