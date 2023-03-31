import java.nio.file.Paths
import scala.sys.process._
import sbt.ForkOptions
import sbt.Keys.streams

name := "spark-playground"
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.13.7"

val sparkVersion = settingKey[String]("spark version")
val hadoopVersion = settingKey[String]("hadoop version")
val javaTargetVersion = settingKey[String]("java target version")
val runtimeJavaHome = taskKey[Option[String]]("runtime JAVA_HOME")
val runMemoryLimit = settingKey[Option[Long]]("running memory limit")
ThisBuild / javaTargetVersion := "1.8"
javacOptions ++= Seq("-target", javaTargetVersion.value)
scalacOptions += s"-target:jvm-${javaTargetVersion.value}"

ThisBuild / sparkVersion := "3.3.1"
ThisBuild / hadoopVersion := "3.3.1"


scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
	"spark-core",
	"spark-streaming",
	"spark-sql",
	"spark-avro",
	"spark-kubernetes",
	"spark-streaming-kafka-0-10",
	"spark-kubernetes",
	"spark-hadoop-cloud"
).map("org.apache.spark" %% _ % sparkVersion.value)
// libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC5"
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.3.5"
libraryDependencies ++= Seq("hadoop-hdfs", "hadoop-common", "hadoop-client", "hadoop-aws")
	.map("org.apache.hadoop" % _ % hadoopVersion.value)
libraryDependencies += "org.rogach" %% "scallop" % "4.1.0"


scalacOptions ++= Seq("-deprecation", "-feature")

assembly / assemblyMergeStrategy := {
	{
		case PathList("META-INF", xs @ _*) => MergeStrategy.discard
		case x => MergeStrategy.first
	}
}

assembly / assemblyMergeStrategy ~= { (old) =>
	{
		case PathList("META-INF", "services", xs @ _*) => MergeStrategy.filterDistinctLines
		case v => old(v)
	}
}