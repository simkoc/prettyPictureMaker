package de.halcony.ppm.graph.visual.bar

import de.halcony.ppm.graph.Coordinate
import de.halcony.ppm.graph.generics.Axis
import de.halcony.ppm.graph.visual.bar.BarOrientation._
import de.halcony.ppm.utility.OptionExtensions.ExtendedOption
import wvlet.log.LogSupport

class BarPlotAxis() extends Axis with LogSupport {

  protected var axisOrientation: BarOrientation = BarOrientation.vertical
  protected var barWidth: Int = 5
  protected var barShiftPt: Option[Int] = None
  protected var nodesNearCoords: Option[NodesNearCoords] = None

  def setNodesNearCoords(specs: NodesNearCoords): BarPlotAxis = {
    nodesNearCoords = Some(specs)
    this
  }

  def setBarOrientation(orientation: BarOrientation): BarPlotAxis = {
    axisOrientation = orientation
    this
  }

  def setBarWidth(pt: Int): BarPlotAxis = {
    barWidth = pt
    this
  }

  def setBarShift(pt: Int): BarPlotAxis = {
    barShiftPt = Some(pt)
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
    val labels = plots.flatMap(_.getCoordinates.map(getLabel))
    s"""${o}bar,
        ${if (!areLabelNumeric) {
         s"symbolic $oo coords={${labels.mkString(",")}},"
       } else ""}
       ${nodesNearCoords.processOrElse(value => s"${value.getSpecs},", "")}
       ${barShiftPt.processOrElse(value => s"bar shift=${value}pt,", "")}
       bar width=${barWidth}pt,
       ${oo}tick=data,
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
