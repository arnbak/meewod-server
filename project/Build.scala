import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "meewod-server"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.webjars" % "webjars-play" % "2.1.0",
    "org.webjars" % "bootstrap" % "2.3.1",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "com.typesafe.slick" %% "slick" % "1.0.1",
    "rome" % "rome" % "1.0")

  val main = play.Project(appName, appVersion, appDependencies).settings( 
      // Add your own project settings here 
  )
}
