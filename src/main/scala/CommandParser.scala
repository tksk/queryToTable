
package org.tksk.queryToTable

import org.jsoup._

trait CommandParser {

  def condStr(symbol: String, operand: String): String => Boolean = symbol match {
    case null | "eq" => _ == operand
    case "ne" | "!"  => _ != operand
    case "lt"        => _ <  operand
    case "lte"       => _ <= operand
    case "gt"        => _ >  operand
    case "gte"       => _ >= operand
    case "like"      => _.matches(operand)
  }

  def condNum(symbol: String, operand: BigDecimal): BigDecimal => Boolean = symbol match {
    case null | "eq" => _ == operand
    case "ne" | "!"  => _ != operand
    case "lt"        => _ <  operand
    case "lte"       => _ <= operand
    case "gt"        => _ >  operand
    case "gte"       => _ >= operand
  }

  val WherePattern = """(\d+)(?::(.+))?""".r

  val parser = new scopt.OptionParser[Config]("qutie") {
    head("QueryTo<table>", "1.0")
    opt[String]('s', "select") required() action { (v, c) =>
      c.copy(fields = v.split(",").map(_.trim.toInt))
    } text("comma separated field indices(0-origin)") valueName("<indices>")
    opt[(String, String)]('w', "where") unbounded() action {
      case ((WherePattern(i, symbol), v), c) =>
      // -w:1:lt=hoge
      val cond = condStr(symbol, v)
      val func = (seq: Seq[String]) => cond(seq(i.toInt))
      c.copy(conds = c.conds :+ func)
    } keyValueName("<index>:<ope>", "<value>") text("String field filter")
    opt[(String, BigDecimal)]('e', "nwhere") unbounded() action {
      case ((WherePattern(i, symbol), v), c) =>
      // -e:1:lt=100
      val cond = condNum(symbol, v)
      val func = (seq: Seq[String]) => cond(BigDecimal(seq(i.toInt)))
      c.copy(conds = c.conds :+ func)
    } keyValueName("<index>:<ope>", "<value>") text("Number field filter")
    opt[Unit]('i', "indexed") action { (v, c) =>
      c.copy(indexed = true)
    } text("prepend an index column")
    opt[Unit]("head") action { (v, c) =>
      c.copy(heading = true)
    } text("show only 10 rows.")
    opt[String]("encoding") action { (v, c) =>
      c.copy(encoding = Some(v))
    } text("document encoding (file only)")
    opt[String]("table-id") abbr("ti") action { (v, c) =>
      c.copy(tableId = Some(v))
    } text("table ID")
    opt[String]("skip-empty") abbr("se") action { (v, c) =>
      val func = (seq: Seq[String]) => seq.length != 0
      c.copy(conds = func :: c.conds)
    } text("skip epmty line")
    arg[String]("<file> or <url>") action { (v, c) =>
      c.copy(args = c.args :+ v)
    } text("input file or URL")
    note("""
      |  <ope>: operators
      |      (null) | eq : equal
      |      ne     | !  : not equal
      |      lt   : less than
      |      lte  : less than equal
      |      gt   : greater than
      |      gte  : greater than equal
      |      like : regular expression (String only)
    """.stripMargin)
    help("help") text("prints this usage text")
  }
}
