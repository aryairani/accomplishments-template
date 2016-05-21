import java.text.NumberFormat
import java.time.LocalDate

import scalaz.syntax.std.boolean._
import scalaz.syntax.std.option._
import scalaz.std.option._
import scalaz.std.string._
import scalaz.syntax.monoid._
import scalaz.syntax.equal._
import scalaz.NonEmptyList

/**
 * Created by arya on 5/31/14.
 */
package object accomplishments {


  val headers = List(
    "Project",
    "Project Title",
    "Subtask Title",
    "Contract No",
    "Sponsor",
    "PD of Project",
    "Contract Value includes Fee",
    "Funded Amount includes Fee",
    "PD of Subtask",
    "Total Hours Worked",
    "Budget of Subtask",
    "Contract Start Date",
    "Contract End Date",
    "Employee First Month Worked on Project",
    "Employee Last Month Worked on Project"
  )

  def template(d: Template) = {
    import d._
    val ppd = who === projectPD
    val spd = who === subtaskPD
    templateS(s"$projTitle: $subTitle", projNo, sponsor,
      pi = s"$subtaskPD; $projectPD",
      role = if (ppd) Some("Project Director") else if (spd) Some("Subtask Lead") else None,
      budgetaryAuthority = Some(ppd || spd),
      hoursCharged = Some(hoursWorked),
      subtaskBudget, contractValue,
      personsSupervised = None,
      contractStart, contractEnd, youStart, youEnd,
      contribution = None
    )
  }

  def templateS(title: String, projNo: String, sponsor: String, pi: String, role: Option[String],
                budgetaryAuthority: Option[Boolean], hoursCharged: Option[BigDecimal],
                taskBudget: BigDecimal, projectBudget: BigDecimal,
                personsSupervised: Option[NonEmptyList[String]],
                contractStart: LocalDate, contractEnd: LocalDate,
                youStart: LocalDate, youEnd: LocalDate,
                contribution: Option[String]
                ) = {
    val empty = "_______"
    val currency = NumberFormat.getCurrencyInstance
    val number = NumberFormat.getNumberInstance
    import number.{format => n}, currency.{format => c}
    val showHours: BigDecimal => String = h => s"(${n(h)} hours)"

    s"""
    |Title: $title
    |Project Number: $projNo
    |Sponsor: $sponsor
    |P.I.: $pi
    |Your Role: ${role.getOrElse(empty)}
    |Did You have Budgetary Authority? ${budgetaryAuthority.map(_ ? "yes" | "no").getOrElse(empty)}
    |Amount Funded for Task on which Candidate was Responsible: [${c(taskBudget)}]? ${hoursCharged.map(showHours).getOrElse(empty)}
    |Amount Funded for Entire Project: ${c(projectBudget)}
    |Persons Supervised for Portion of Work Led by You: ${personsSupervised.getOrElse(NonEmptyList(empty, empty)).list.mkString(", ")}
    |Contract Period: $contractStart – $contractEnd
    |Your Performance Period: $youStart – $youEnd
    |
    |${contribution.getOrElse("Describe your contribution:")}
  """.stripMargin
  }
}