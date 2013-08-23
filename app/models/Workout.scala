package models

import anorm._
import anorm.SqlParser._
import play.api.Logger
import java.util.Date
import play.api.libs.json._
import org.joda.time.DateTime
import utils.AnormExtension._
import play.api.db._
import play.api.Play.current
import utils.RSS
import scala.collection.mutable.ListBuffer

case class Workout(
  published: DateTime,
  title: String,
  text: String,
  uri: String)

object Workout {

  val listAll = SQL("SELECT a.title, a.description, a.published, a.uri FROM wods.daily a ORDER BY a.published DESC")
  val insertElement = SQL("INSERT INTO wods.daily(title, description, published, uri) VALUES ({title}, {description}, {published},{uri})")

  val workout: RowParser[Workout] = {
    get[DateTime]("daily.published") ~
      get[String]("daily.title") ~
      get[String]("daily.description") ~
      get[String]("daily.uri") map {
        case published ~ title ~ text ~ uri => Workout(published, title, text, uri)
      }
  }

  def workoutList(): Seq[Workout] = {

    val localList = list()

    val todayExist = localList.exists(_.published.toLocalDate().equals(new DateTime().toLocalDate()))

    if (!todayExist) newList(localList)
    else localList

  }

  private def newList(l: Seq[Workout]): Seq[Workout] = {
    val remote = RSS.parse.diff(l);

    remote.map {
      e =>
        l.find(!_.published.equals(e.published)).getOrElse(e)
        insert(e);
    }

    list
  }

  private def list(): Seq[Workout] = {
    DB.withConnection { implicit conn =>
      listAll.as(workout *)
    }
  }

  def insert(e: Workout) = {
    DB.withConnection { implicit conn =>
      insertElement.on('title -> e.title, 'published -> e.published, 'description -> e.text, 'uri -> e.uri).executeUpdate()
    }
  }

  implicit object WorkoutFormat extends Format[Workout] {

    def reads(json: JsValue): JsSuccess[Workout] = JsSuccess(Workout(
      new DateTime((json \ "published").as[DateTime]),
      (json \ "title").as[String],
      (json \ "text").as[String],
      (json \ "uri").as[String]))

    def writes(w: Workout): JsValue = {
      JsObject(Seq(
        Some("published" -> JsString(w.published.toString)),
        Some("title" -> JsString(w.title)),
        Some("text" -> JsString(w.text)),
        Some("uri" -> JsString(w.uri))).filter(_.isDefined).map(_.get))
    }
  }
}