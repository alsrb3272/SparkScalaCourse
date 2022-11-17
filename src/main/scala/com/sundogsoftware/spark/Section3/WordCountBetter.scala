package com.sundogsoftware.spark.Section3

/** Count up how many of each word occurs in a book, using regular expressions. */
// 대소문자 구분없고 구두점을 제거한 입력된 데이터들을 각각의 정규표현식으로 문자 정렬
object WordCountBetter {

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "WordCountBetter")

    // Load each line of my book into an RDD
    val input = sc.textFile("data/book.txt")

    // Split using a regular expression that extracts words
    val words = input.flatMap(x => x.split("\\W+"))

    // Normalize everything to lowercase
    val lowercaseWords = words.map(x => x.toLowerCase())

    // Count of the occurrences of each word
    val wordCounts = lowercaseWords.countByValue()

    // Print the results
    wordCounts.foreach(println)
  }

}
