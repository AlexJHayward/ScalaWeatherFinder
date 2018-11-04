name := "Weather"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "com.snowplowanalytics" %% "scala-weather" % "0.4.0",
  "com.koddi"             %% "geocoder"      % "1.1.0",
  "org.scalatest"         %% "scalatest"     % "3.0.5" % "test"
)
