package accomplishments

import scalaz._, Scalaz._

object CsvMain extends App {
  val header :: rows = args.toList.lift(0) match {
    case Some(file) => io.Source.fromFile(file, "UTF-16LE").getLines().toList
    case None => {
      println("Paste CSV data:")
      io.Source.fromInputStream(System.in, "UTF-16LE").getLines().toList
    }
  }
  rows.foreach { (Template.fromCSV _) >>> template >>> println }
}
