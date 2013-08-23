package utils

import org.joda.time._
import org.joda.time.format._
import anorm._
import anorm.MayErr.eitherToError

//http://stackoverflow.com/questions/11388301/joda-datetime-field-on-play-framework-2-0s-anorm/11975107#11975107
object AnormExtension {

  val dateFormatGeneration = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  implicit def rowToDateTime: Column[DateTime] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case ts: java.sql.Timestamp => Right(new DateTime(ts.getTime))
      case d: java.sql.Date => Right(new DateTime(d.getTime))
      case str: java.lang.String => Right(dateFormatGeneration.parseDateTime(str))
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass) )
    }
  }
  implicit val dateTimeToStatement = new ToStatement[DateTime] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: DateTime): Unit = {
      if(aValue == null) {
        s.setTimestamp(index, null)
      } else {
        s.setTimestamp(index, new java.sql.Timestamp(aValue.withMillisOfSecond(0).getMillis()) )
      }
    }
  }
}