
package org.tksk.queryToTable

import org.jsoup._
import scala.collection.JavaConverters._

object QueryToTable extends App with CommandParser {

  parser.parse(args, Config()) map { config =>
    val doc = {
      val target = config.args(0)
      if(target.matches("$https?:.*")) {
        Jsoup.connect(config.args(0)).get
      } else {
        val file = new java.io.File(config.args(0))
        val enc = config.encoding.getOrElse(null)
        Jsoup.parse(file, enc)
      }
    }

    val IdAsIndex = """^(\d+)$""".r
    val tableSelector = config.tableId match {
      case None => "table"
      case Some(IdAsIndex(index)) => s"table:eq(${index})"
      case Some(id) => s"table#${id}"
    }

    val table = doc.select(tableSelector)
    val tdsItor = for {
      tr <- table.select("tr").asScala
      tds = tr.select("td").asScala.map(_.text)
      if config.conds.forall(_(tds))
    } yield tds

    for {
      (tds, index) <- (if(!config.heading) tdsItor else tdsItor.take(10)).zipWithIndex
    } {
      val pre = if(config.indexed) s""""${index+1}",""" else ""
      val fields = config.fields.map(tds)
      println(pre + fields.mkString("\"", "\",\"", "\""))
    }
  } getOrElse {
    // arguments are bad, error message will have been displayed
  }
}

case class Config(foo: Int = 1, fields: Seq[Int] = Seq(),
  conds: List[Seq[String] => Boolean] = Nil,
  indexed: Boolean = false, encoding: Option[String] = None,
  tableId: Option[String] = None, args: Seq[String] = Seq(),
  heading: Boolean = false)
