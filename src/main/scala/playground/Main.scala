package playground

import org.rogach.scallop.ScallopConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

class MainConf extends ScallopConf {
  // any cli arg stuff you can put in here
  verify()
}


object Main {
	def main(args: Array[String]): Unit = {
		val config = new MainConf
		val spark = SparkSession
      .builder()
      .master("local")
      // .config()
      .getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._

    val mixedMsges = sc.parallelize(Seq[Either[Boolean, Boolean]](Left(true),Right(false),Left(true),Right(false),Left(true),Right(false),Left(true),Right(false),Left(true),Right(false)))
      .persist(StorageLevel.DISK_ONLY)

    val trueMsges = mixedMsges
      .flatMap {
        case Left(v) => Some(v)
        case _ => None
      }
    val falseMsges = mixedMsges
      .flatMap {
        case Right(v) => Some(v)
        case _ => None
      }

    println(s"mixedMsges count: ${mixedMsges.count()}\ntrueMsges count: ${trueMsges.count()}\nfalseMsges count: ${falseMsges.count()}")
    scala.io.StdIn.readLine()
	}
}