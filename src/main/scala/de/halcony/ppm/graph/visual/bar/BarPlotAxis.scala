package de.halcony.ppm.graph.visual.bar

import de.halcony.ppm.graph.Coordinate
import de.halcony.ppm.graph.generics.Axis
import de.halcony.ppm.graph.visual.bar.BarOrientation._
import wvlet.log.LogSupport

class BarPlotAxis() extends Axis with LogSupport {

  protected var axisOrientation: BarOrientation = BarOrientation.vertical
  protected var barWidth: Double = 0.5
  protected var barSeparation: Double = 0.5

  def setBarOrientation(orientation: BarOrientation): BarPlotAxis = {
    axisOrientation = orientation
    this
  }

  def setBarWidth(width: Double): BarPlotAxis = {
    barWidth = width
    this
  }

  def setBarSeparation(separation: Double): BarPlotAxis = {
    barSeparation = separation
    this
  }

  override protected def customAxisConfigurationLines: String = {
    val o = axisOrientation match {
      case BarOrientation.horizontal => "x"
      case BarOrientation.vertical   => "y"
    }
    val oo = axisOrientation match {
      case BarOrientation.horizontal => "y"
      case BarOrientation.vertical   => "x"
    }
    val labels = plots.flatMap(_.getCoordinates.map(getLabel)).toSet
    s"""${if (!areLabelNumeric) {
         s"symbolic $oo coords={ ${labels.mkString(",")} },"
       } else ""}
        ${o}bar = ${barSeparation}cm,
        bar width = ${barWidth}cm,
        ${oo}tick = data,
    """.stripMargin
  }

  private def getLabel(coordinate: Coordinate): String = {
    axisOrientation match {
      case BarOrientation.vertical   => coordinate.x
      case BarOrientation.horizontal => coordinate.y
    }
  }

  private def areLabelNumeric: Boolean = {
    try {
      plots.foreach(_.getCoordinates.foreach(elem => getLabel(elem).toDouble))
      true
    } catch {
      case _: Throwable =>
        info("detected non-numeric labels")
        false
    }
  }

}
