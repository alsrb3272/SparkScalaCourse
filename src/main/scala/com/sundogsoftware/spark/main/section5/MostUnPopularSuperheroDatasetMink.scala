package com.sundogsoftware.spark.main.section5

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, min, size, split, sum}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}


// 코드풀이 방식
// MostPopularSuperheroDataset 스크립트의 복사본으로 시작합니다(Intellij에서 복사/붙여넣기 및 프로세스에서 새 이름을 지정할 수 있음).
// "connections" 데이터 세트가 구성되는 지점까지 크게 변경되지 않을 수 있습니다.
// 연결이 하나뿐인 행을 찾기 위해 연결 필터링
// 결과를 이름 데이터 세트와 결합
// 이름 열을 선택하고 표시

// Syntax Snippets 힌트
// Datasetname.filter(%"columnname" === somevalue)
// Datasetname.join(someOtherDatasetWithACommonColumnName, usingColumn="commonColumnName")
// agg(min("columnName").first().getLong(0)


/** Find the superhero with the most co-appearances. */
object MostUnPopularSuperheroDatasetMink {

  case class SuperHeroNames(id: Int, name: String)

  case class SuperHero(value: String)

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkSession using every core of the local machine
    val spark = SparkSession
      .builder
      .appName("UnPopularSuperhero")
      .master("local[*]")
      .getOrCreate()

    // Create schema when reading Marvel-names.txt
    val superHeroNamesSchema = new StructType()
      .add("id", IntegerType, nullable = true)
      .add("name", StringType, nullable = true)

    // Build up a hero ID -> name Dataset
    import spark.implicits._
    val names = spark.read
      .schema(superHeroNamesSchema)
      .option("sep", " ")
      .csv("data/Marvel-names.txt")
      .as[SuperHeroNames]

    val lines = spark.read
      .text("data/Marvel-graph.txt")
      .as[SuperHero]

    val connections = lines
      .withColumn("id", split(col("value"), " ")(0))
      .withColumn("connections", size(split(col("value"), " ")) - 1)
      .groupBy("id").agg(sum("connections").alias("connections"))
//--------------------여기까진 동일 ------------------------------------------------------------

    // Datasetname.filter($"columnname" === somevalue)
    // Datasetname.join(someOtherDatasetWithACommonColumnName, usingColumn="commonColumnName")
    // agg(min("columnName")).first().getLong(0)
    // 커넥션 칼럼에 있는 연결점 최솟값들 데이터들을 불러온 후 first를 통해 싱글 row로 전환하고 row 객체를 갖게되면 긴 정수로 표출
    val a = connections.agg(min("connections")).first().getLong(0)
    // id값들로 필터링하여 추출.
    val ab = connections.filter($"connections" === a)
    // id들을 통하여 names로 조인
    val abc = ab.join(names, usingColumn = "id");
    // 이름열 선택
    abc.select("name").show()
  }
}
