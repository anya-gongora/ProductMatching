name := "ProductMatching"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6",
  "joda-time" % "joda-time" % "2.9.9",
  "com.typesafe.play" %% "play-json" % "2.6.7",
  "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"
)
