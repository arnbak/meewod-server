package controllers

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import play.Logger
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.libs.json.Json
import models.Workout
import models.Benchmark
import scala.concurrent.duration.Duration

object WorkoutApi extends Controller {

  val system = ActorSystem("Daily")
  val actor = system.actorOf(Props[DailyWorkoutActor])

  implicit val timeout: Timeout = Timeout(Duration(10, "seconds"))

  def dailyList = Action {

    val result = Await.result(actor ? "GetDailyList", timeout.duration).asInstanceOf[List[Workout]]
    //val result = Workout.workoutList
    Ok(Json.toJson(result)).as("application/json")

  }

  def benchmarkList = Action {
    Ok(Json.toJson(Benchmark.list)).as("application/json")
  }

}