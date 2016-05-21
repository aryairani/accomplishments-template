package accomplishments

import java.time.LocalDate
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}

/**
 * Created by arya on 5/31/14.
 */
case class Template(who: String, //0
                    projNo: String, //1
                    projTitle: String, //2
                    subTitle: String, //3
                    contractNo: String, //4
                    sponsor: String, //5
                    projectPD: String, //6
                    contractValue: BigDecimal, //7
                    fundedAmount: BigDecimal, //8
                    subtaskPD: String, //9
                    hoursWorked: BigDecimal, //10
                    subtaskBudget: BigDecimal, //11
                    contractStart: LocalDate, //12
                    contractEnd: LocalDate, //13
                    youStart: LocalDate, //14
                    youEnd: LocalDate) //15
//{
//  def hoursRate(rate: BigDecimal) = this.copy(subtaskBudget = (rate * hoursWorked).formatted("approximately %.2f"))
//}

object Template {
//  def fromHtml(n: xml.Node) = {
//    val t = n \ "td" map (_.child(0).text.replaceAll("&nbsp;",""))
//    Template(t(0), t(1), t(2), t(3), t(4), t(5), BigDecimal(t(6)), BigDecimal(t(7)), t(8), BigDecimal(t(9)), BigDecimal(t(10)), LocalDate.parse(t(11), DateTimeFormatter.ISO_LOCAL_DATE), t(12), t(13), t(14))
//  }

  val dtf = new DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .append(DateTimeFormatter.ISO_LOCAL_DATE)
    .appendLiteral(' ')
    .append(DateTimeFormatter.ISO_LOCAL_TIME)
    .toFormatter()

  def fromCSV(string: String): Template = {
    val t = string.split('\t')
    Template(
      who = t(0),
      projNo = t(1),
      projTitle = t(2),
      subTitle = t(3),
      contractNo = t(4),
      sponsor = t(5),
      projectPD = t(6),
      contractValue = BigDecimal(t(7)),
      fundedAmount = BigDecimal(t(8)),
      subtaskPD = t(9),
      hoursWorked = BigDecimal(t(10)),
      subtaskBudget = BigDecimal(t(11)),
      contractStart = LocalDate.parse(t(12), dtf),
      contractEnd = LocalDate.parse(t(13), dtf),
      youStart = LocalDate.parse(t(14), dtf),
      youEnd = LocalDate.parse(t(15), dtf)
    )
  }
}