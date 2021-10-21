package de.tubs.ias.ppm.bargraph

import BarOrientation._
import wvlet.log.LogSupport


case class Axis(axisOrientation : BarOrientation,
                delta : Double,
                width : Int,
                min : Int,
                max : Int) extends LogSupport {

  def getLabel(coordinate: Coordinate): String = {
    axisOrientation match {
      case BarOrientation.vertical => coordinate.x
      case BarOrientation.horizontal => coordinate.y
    }
  }

  def createAxis(plots : Seq[Plot]) : String = {
    val labelNumberic = try {
      plots.foreach(_.coordinates.foreach(getLabel))
      true
    } catch {
      case _ : Throwable =>
        info("detected non-numeric labels")
        false
    }
    val labels = plots.flatMap(_.coordinates.map(getLabel))
    val o = axisOrientation match {
      case BarOrientation.horizontal => "x"
      case BarOrientation.vertical => "y"
    }
    val oo = axisOrientation match {
      case BarOrientation.horizontal => "y"
      case BarOrientation.vertical => "x"
    }
    val beginAxis : String =
      s"""\\begin{axis}[
         |   ${o}bar = ${delta}cm,
         |   bar width = ${width}pt,
         |   ${if (!labelNumberic) { s"symbolic $oo coords={ ${labels.mkString(",")} }," } else "" }
         |   ${o}min = $min,
         |   ${o}max = $max,
         |   ${oo}tick = data
         | ]
         |""".stripMargin
    s"""$beginAxis
       |${plots.map(plot => plot.getPlotCommand).mkString("\n")}
       |\\end{axis}
       |""".stripMargin
  }
}