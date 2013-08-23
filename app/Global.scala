import play.api._

object MeeGlobal extends GlobalSettings {
  
	override def onStart(app: Application) {
	  //lazy val database = Datab
	  
	  Logger.info("Start Actors")
	}
	
	override def onStop(app: Application) {
	  Logger.info("Stop Actor System")
	  controllers.WorkoutApi.system.shutdown
	}

}