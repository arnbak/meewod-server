package utils
import com.sun.syndication.io._
import com.sun.syndication.feed.synd._
import java.net.URL
import scala.collection.JavaConversions._
import models._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import scala.collection.mutable.ListBuffer

object RSS {

  def parse(): Seq[Workout] = {
    
    var input = new SyndFeedInput()
    
    
    
    val urls = List("http://www.crossfit.com/index.rdf")

    val list = new ListBuffer[Workout]

    urls.foreach(url => {
      val feed = input.build(new XmlReader(new URL(url)))

      val entries = feed.getEntries().asInstanceOf[java.util.List[SyndEntryImpl]]

      entries.foreach(entry => {

        val _desc = description(entry.getDescription().getValue())
        val _title = entry.getTitle().split(" ").toList(0)
        val _published = DateTimeFormat.forPattern("yyMMdd").parseDateTime(entry.getTitle().split(" ").toList(1))
        val _uri = entry.getUri()
        
        list += Workout(_published,_title,_desc,_uri)
      })

    })

    list.toSeq
  }

  private def description(text: String): String = {
    if (text.startsWith("Rest Day")) {
      text.substring(0, 8)
    } else {
      val index = 
        if ((text.indexOf("Post") == -1)) text.indexOf("Compare") 
        else text.indexOf("Post")
      try {
        text.substring(0, index)
      } catch {
        case e: Exception => text
      }
    }
  }
}