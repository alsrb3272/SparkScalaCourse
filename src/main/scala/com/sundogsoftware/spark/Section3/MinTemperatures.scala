package com.sundogsoftware.spark.Section3

/** Find the minimum temperature by weather station */
import breeze.linalg.min
import org.apache.log4j._
import org.apache.spark._

object MinTemperatures {

  // 포맷 관련된 기능
  // 이름 그리고 type 순서 다른 언어들과 반대의 형태
  def parseLine(line: String): (String, String, Float) = {
    val fields = line.split(",")
    val stationID = fields(0)
    val entryType = fields(2)
    val temperature = fields(3).toFloat * 0.1f * (9.0f / 5.0f) + 32.0f
    // 새로운 tuple
    (stationID, entryType, temperature)
  }

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    // 로그기록 중 에러 메세지 처리
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "MinTemperatures")

    // Read each line of input data
    // 모든 파일을 RDD 각각의 라인으로 읽어드림
    val lines = sc.textFile("data/1800.csv")

    // Convert to (stationID, entryType, temperature) tuples
    //
    val parsedLines = lines.map(parseLine)

    // Filter out all but TMIN entries
    // 두 번째 entrytype에서 TMIN과 매칭되는 줄을 필터링함
    val minTemps = parsedLines.filter(x => x._2 == "TMIN")

    // Convert to (stationID, temperature)
    // station_id, 세번째 필드 temperature.부동소수점 수
    val stationTemps = minTemps.map(x => (x._1, x._3.toFloat))

    // Reduce by stationID retaining the minimum temperature found
    //stationtemp 모든 줄을 검사하고 최소 value를 확인. stationID 즉 key값을 제거해가며 최하 온도를 알아내는 검사방법
    val minTempsByStation = stationTemps.reduceByKey((x, y) => min(x, y))

    // Collect, format, and print the results
    val results = minTempsByStation.collect()

    // 줄마다 검색하는 for-loop문.
    for (result <- results.sorted) {
      val station = result._1
      val temp = result._2
      val formattedTemp = f"$temp%.2f F"
      println(s"$station minimum temperature: $formattedTemp")
    }

  }
}
