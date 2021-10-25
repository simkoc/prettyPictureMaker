package de.tubs.ias.ppm.bargraph

import BarOrientation._
import wvlet.log.LogSupport

/**
  *
  * @param axisOrientation whether the bars are supposed to be horizontal or vertical
  * @param delta the space between bars of multiple plots in cm
  * @param barWidth the width of the bar in pt
  * @param width the width of the picture in cm
  * @param height the height of the picture in cm
  * @param xmin the minimum value on the plotted x axis
  * @param xmax the maximum value on the plotted x axis
  * @param ymin the minimum value on the plotted y axis
  * @param ymax the maximum value on the plotted y axis
  */
case class Axis(axisOrientation: BarOrientation,
                delta: Double,
                barWidth: Int,
                width: Option[Int] = None,
                height: Option[Int] = None,
                ymin: Option[Int] = None,
                ymax: Option[Int] = None,
                xmin: Option[Int] = None,
                xmax: Option[Int] = None)
    extends LogSupport {

  def getLabel(coordinate: Coordinate): String = {
    axisOrientation match {
      case BarOrientation.vertical   => coordinate.x
      case BarOrientation.horizontal => coordinate.y
    }
  }

  private def createLegend(plots: Seq[Plot]): String = {
    try {
      plots.map(_.label.get).mkString("\\legend{", ",", "};\n")
    } catch {
      case _: Throwable => ""
    }
  }

  def createAxis(plots: Seq[Plot]): String = {
    val labelNumberic = try {
      plots.foreach(_.coordinates.foreach(elem => getLabel(elem).toDouble))
      true
    } catch {
      case _: Throwable =>
        info("detected non-numeric labels")
        false
    }
    val labels = plots.flatMap(_.coordinates.map(getLabel))
    val o = axisOrientation match {
      case BarOrientation.horizontal => "x"
      case BarOrientation.vertical   => "y"
    }
    val oo = axisOrientation match {
      case BarOrientation.horizontal => "y"
      case BarOrientation.vertical   => "x"
    }
    val beginAxis: String =
      s"""\\begin{axis}[
         ${if (width.nonEmpty) s"width=${width.get}cm," else ""}
         ${if (height.nonEmpty) s"height=${height.get}cm," else ""}
         ${o}bar = ${delta}cm,
         bar width = ${barWidth}pt,
         ${if (!labelNumberic) {
           s"symbolic $oo coords={ ${labels.mkString(",")} },"
         } else ""}
         ${if (xmin.nonEmpty) s"xmin = ${xmin.get}," else ""}
         ${if (xmax.nonEmpty) s"xmax = ${xmax.get}," else ""}
         ${if (ymin.nonEmpty) s"ymin = ${ymin.get}," else ""}
         ${if (ymax.nonEmpty) s"ymax = ${ymax.get}," else ""}
         ${oo}tick = data\n
         ]""".stripMargin
        .split("\n")
        .filter(_.replace(" ", "") != "")
        .mkString("\n")
    s"""$beginAxis
       |${plots.map(plot => plot.getPlotCommand).mkString("\n")}
       |${createLegend(plots)}
       |\\end{axis}
       |""".stripMargin
  }
}
