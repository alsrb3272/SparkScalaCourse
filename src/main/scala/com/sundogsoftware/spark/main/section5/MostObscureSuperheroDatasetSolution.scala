package com.sundogsoftware.spark.main.section5

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, min, size, split, sum}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}


// List the names of all superheroes with only ONE connection
// Extra credit : compute the actual smallest number of connections in the data set instead of assuming it is one

// 코드풀이 방식
// Start with a copy of the MostPopularSuperheroDataset script (can copy/ paste in Intellij and give it a new name in the process)
// Can be largely unchanged up to point where the "connections" dataset is constructed
// Filter the connections to find rows with only one connection
// Join the results with the names dataset
// Select the names column and show it

// Syntax Snippets 힌트
// Datasetname.filter(%"columnname" === somevalue)
// Datasetname.join(someOtherDatasetWithACommonColumnName, usingColumn="commonColumnName")
// agg(min("columnName").first().getLong(0)


/** Find the superhero with the most co-appearances. */
object MostObscureSuperheroDatasetSolution {

  case class SuperHeroNames(id: Int, name: String)

  case class SuperHero(value: String)

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkSession using every core of the local machine
    val spark = SparkSession
      .builder
      .appName("MostObscureHero")
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

    val minConnectionCount = connections.agg(min("connections")).first().getLong(0)

    val minConnections = connections.filter($"connections" === minConnectionCount)

    val minConnectionsWithNames = minConnections.join(names, usingColumn = "id")

    println("The following characters hava only" + minConnectionCount + " connection(s):")

    minConnectionsWithNames.select("name").show()


  }
}
