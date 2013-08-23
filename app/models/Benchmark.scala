package models

import anorm._
import anorm.SqlParser._
import play.api.libs.json._
import org.joda.time.DateTime
import play.api.db._
import play.api.Play.current

case class Benchmark(
  id: Int,
  name: String,
  benchmarktype: String,
  description: String,
  workout: String)

object Benchmark {
  
  val listAll = SQL("select a.id, a.name, a.type, a.description, a.workout from wods.benchmark a order by a.id asc")
  
    val benchmark: RowParser[Benchmark] = {
    get[Int]("benchmark.id") ~
      get[String]("benchmark.name") ~
      get[String]("benchmark.type") ~
      get[String]("benchmark.description")~
      get[String]("benchmark.workout") map {
        case id ~ name ~ benchmarktype ~ description ~ workout => Benchmark(id, name, benchmarktype, description, workout)
      }
  }
  
  
  def list(): Seq[Benchmark] = {
    DB.withConnection {
      implicit conn => listAll.as(benchmark *)
    }
  }
  
  
  
  implicit object BenchmarkFormat extends Format[Benchmark] {

    def reads(json: JsValue): JsSuccess[Benchmark] = JsSuccess(Benchmark(
      (json \ "id").as[Int],
      (json \ "name").as[String],
      (json \ "benchmarktype").as[String],
      (json \ "description").as[String],
      (json \ "workout").as[String]))

    def writes(w: Benchmark): JsValue = {
      JsObject(Seq(
        Some("id" -> JsNumber(w.id.toInt)),
        Some("name" -> JsString(w.name)),
        Some("benchmarktype" -> JsString(w.benchmarktype)),
        Some("description" -> JsString(w.description)),
        Some("workout" -> JsString(w.workout))).filter(_.isDefined).map(_.get))
    }
  }
}