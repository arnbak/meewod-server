name := "Planidag-app"

version := "1.0-SNAPSHOT"


lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.webjars" %% "webjars-play" % "2.2.1-2",
  "org.postgresql" % "postgresql" % "9.3-1101-jdbc4",
  "com.typesafe.slick" %% "slick" % "2.0.2",
  "rome" % "rome" % "1.0"
)