package controllers

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Actor
import play.api.Logger

import models._

class DailyWorkoutActor extends Actor {

  def receive = {

    case "GetDailyList" => {
      val dailyList = Workout.workoutList()
      //Logger.info("List from actor " + dailyList)
      sender ! Workout.workoutList
    }

    case _ => sender ! null
  }
}