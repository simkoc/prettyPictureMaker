package de.tubs.ias.ppm.timepointsgraph

import wvlet.log.LogSupport

case class TimePointsAxis(width : Option[Int] = None,
                          height : Option[Int] = None,
                          xtick : Option[Seq[String]] = None,
                          ytick : Option[Seq[String]] = None) extends LogSupport {

  def createAxis(plots : Seq[TimePointPlot]) : String = {
    val labels : Seq[String] = plots.flatMap(_.coordinates.map(_.x)).sorted
    val actualxtick = xtick match {
      case Some(value) => value
      case None => plots.flatMap(_.coordinates.map(_.x)).toSet.toList.sorted
    }
    val actualytick = ytick match {
      case Some(value) => value
      case None => plots.flatMap(_.coordinates.map(_.y)).toSet.toList.sorted
    }
    val beginAxis : String =
      s"""\\begin{axis}[
         ${if (width.nonEmpty) s"width=${width.get}cm," else ""}
         ${if (height.nonEmpty) s"height=${height.get}cm," else ""}
         xtick={ ${actualxtick.mkString(",")} },
         ytick={ ${actualytick.mkString(",")} },
         ]""".stripMargin.split("\n").filter(_.replace(" ","") != "").mkString("\n")
    s"""$beginAxis
       |${plots.map(plot => plot.getPlotCommand).mkString("\n")}
       |\\end{axis}
       |""".stripMargin
  }

}
