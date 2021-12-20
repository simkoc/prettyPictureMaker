package de.halcony.ppm.graph.generics
import de.halcony.ppm.colors.CustomColor
import de.halcony.ppm.graph.legends.Legend
import wvlet.log.LogSupport

import scala.collection.mutable.ListBuffer

//todo: change this to this configuration to make shared expansion easier
trait Axis[X <: Plot[X], T <: Axis[X, T]] extends Plottable with LogSupport {

  protected val plots: ListBuffer[X] = ListBuffer()
  private var width: Option[Int] = None
  private var height: Option[Int] = None
  private var xmin: Option[Int] = None
  private var xmax: Option[Int] = None
  private var ymin: Option[Int] = None
  private var ymax: Option[Int] = None
  private var legend: Option[Legend[X]] = None

  def addPlot(plot: X): T = {
    plots.addOne(plot)
    this.asInstanceOf[T]
  }

  def addPlots(plot: Seq[X]): T = {
    plots.addAll(plot)
    this.asInstanceOf[T]
  }

  def setWidth(width: Int): T = {
    this.width = Some(width)
    this.asInstanceOf[T]
  }

  def setHeight(height: Int): T = {
    this.height = Some(height)
    this.asInstanceOf[T]
  }

  def setXMin(xmin: Int): T = {
    this.xmin = Some(xmin)
    this.asInstanceOf[T]
  }

  def setXMax(xmax: Int): T = {
    this.xmax = Some(xmax)
    this.asInstanceOf[T]
  }

  def setYMin(ymin: Int): T = {
    this.ymin = Some(ymin)
    this.asInstanceOf[T]
  }

  def setYMax(ymax: Int): T = {
    this.ymax = Some(ymax)
    this.asInstanceOf[T]
  }

  def addLegend(legend: Legend[X]): T = {
    this.legend = Some(legend)
    this.asInstanceOf[T]
  }

  override def getCustomColors: List[CustomColor] =
    plots.flatMap(_.getCustomColors).toSet.toList

  override def plot: String = {
    val plot =
      s"""\\begin{axis}[
        $getBoilerplateCode
        $customAxisConfigurationLines
       ]
       ${plots.map(_.plot).mkString("\n")}
       ${if (legend.nonEmpty) legend.get.plot(plots.toList) else ""}
       \\end{axis}"""
    Plottable.purgeEmptyLines(plot)
  }

  protected def customAxisConfigurationLines: String

  private def getBoilerplateCode: String = {
    s"""${if (width.nonEmpty) s"width=${width.get}cm," else ""}
        ${if (height.nonEmpty) s"height=${height.get}cm," else ""}
        ${if (xmin.nonEmpty) s"xmin=${xmin.get}," else ""}
        ${if (xmax.nonEmpty) s"xmax=${xmax.get}," else ""}
        ${if (ymin.nonEmpty) s"ymin=${ymin.get}," else ""}
        ${if (ymax.nonEmpty) s"ymax=${ymax.get}," else ""}"""
  }

}
