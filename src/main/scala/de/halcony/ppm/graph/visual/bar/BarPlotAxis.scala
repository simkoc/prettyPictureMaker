package de.halcony.ppm.graph.visual.bar

import de.halcony.ppm.graph.generics.{Axis, Plot}
import de.halcony.ppm.graph.visual.bar.BarOrientation._
import de.halcony.ppm.utility.OptionExtensions.ExtendedOption
import wvlet.log.LogSupport

class BarPlotAxis() extends Axis with LogSupport {

  protected var axisOrientation: BarOrientation = BarOrientation.vertical
  protected var barWidth: Int = 5
  protected var barShiftPt: Option[Int] = None

  override def addPlot(barPlot: Plot): Axis = {
    assert(barPlot.isInstanceOf[BarPlot], "you can only add BarPlots")
    super.addPlot(barPlot)
    this
  }
  override def addPlots(barPlots: Seq[Plot]): Axis = {
    assert(barPlots.forall(_.isInstanceOf[BarPlot]),
           "you can only add BarPlots")
    super.addPlots(barPlots)
    this
  }
  def addPlots(barPlots: Seq[BarPlot]): BarPlotAxis = {
    super.addPlots(barPlots)
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
    s"""${o}bar,
        ${barShiftPt.processOrElse(value => s"bar shift=${value}pt,", "")}
        bar width=${barWidth}pt,
        ${oo}tick=data,
    """.stripMargin
  }

}
