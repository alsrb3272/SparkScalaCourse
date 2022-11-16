// IMPORT WHAT WE NEED
package com.sundogsoftware.spark


// 스파크 함수 호출
// log4j를 이용하여 오류 수준과 로깅을 조정
import org.apache.spark._
import org.apache.log4j._

/** Count up how many of each star rating exists in the MovieLens 100K data set. */

object RatingsCounter {
 
  /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // SET UP OUR CONTEXT
    // Create a SparkContext using every core of the local machine, named RatingsCounter
    // 어플리케이션 이름과 로컬기계로 작동하는 새로운 콘텍스를 생성
    val sc = new SparkContext("local[*]", "RatingsCounter")

    // LOAD THE DATA
    // Load up each line of the ratings data into an RDD
    // 데이터 파일을 텍스트 파일로 RDD라인을 호출
    val lines = sc.textFile("data/ml-100k/u.data")

    // EXTRACT(MAP) THE DATA WE CARE ABOUT
    // Convert each line to a string, split it out by tabs, and extract the third field.
    // (The file format is userID, movieID, rating, timestamp)
    // RDD로부터 관심있는 정보를 추출, 라인의 모든 함숭 적용하기위해 map을 사용.
    // 2 -> 세번째 칼럼
    // 새로운 RDD생성
    val ratings = lines.map(x => x.split("\t")(2))

    // PERFORM AN ACTION: COUNT BY VALUE
    // Count up how many times each value (rating) occurs
    // 각 고유값이 나타낼 수 있는 rating을 카운팅한다.
    val results = ratings.countByValue()

    // SORT AND DISPLAY THE RESULTS
    // Sort the resulting map of (rating, count) tuples
    // sequence데이터 구조로 설정 후 정렬해서 별점을 받은 레이팅의 갯수를 정렬.
    val sortedResults = results.toSeq.sortBy(_._1)
    
    // Print each result on its own line.
    // 각각의 구조로 출력
    sortedResults.foreach(println)
  }
}
