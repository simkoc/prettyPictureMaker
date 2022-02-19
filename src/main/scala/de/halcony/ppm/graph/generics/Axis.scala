package de.halcony.ppm.graph.generics
import de.halcony.ppm.colors.CustomColor
import de.halcony.ppm.graph.legends.Legend
import de.halcony.ppm.utility.OptionExtensions.ExtendedOption
import wvlet.log.LogSupport

import scala.collection.mutable.ListBuffer

trait Axis extends Plottable with LogSupport {

  protected val plots: ListBuffer[Plot] = ListBuffer()
  private var width: Option[Int] = None
  private var height: Option[Int] = None
  private var xmin: Option[Int] = None
  private var xmax: Option[Int] = None
  private var ymin: Option[Int] = None
  private var ymax: Option[Int] = None
  private var legend: Option[Legend] = None
  private var xAxisAlignment: Option[AxisAlignment.Alignment] = None
  private var yAxisAlignment: Option[AxisAlignment.Alignment] = None
  private var xtickspt: Option[Int] = None
  private var ytickspt: Option[Int] = None
  private var yticklabels: Boolean = true
  private var xticklabels: Boolean = true
  private var enlargeLimits: Option[Double] = None
  private var drawXTicks: Boolean = true
  private var drawYTicks: Boolean = true
  private var noXArrowTip: String = ""
  private var noYArrowTip: String = ""

  def disableXArrowTip(): Axis = {
    noXArrowTip = "*"
    this
  }

  def disableYArrowTip(): Axis = {
    noYArrowTip = "*"
    this
  }

  def disableXTicks(): Axis = {
    drawXTicks = false
    this
  }

  def disableYTicks(): Axis = {
    drawYTicks = false
    this
  }

  def enlargeLimits(factor: Double): Axis = {
    enlargeLimits = Some(factor)
    this
  }

  def setNoYTickLabels(): Axis = {
    yticklabels = false
    this
  }

  def setNoXTickLabels(): Axis = {
    xticklabels = false
    this
  }

  def setXTicksSpacing(pt: Int): Axis = {
    xtickspt = Some(pt)
    this
  }

  def setYTicksSpacing(pt: Int): Axis = {
    ytickspt = Some(pt)
    this
  }

  def setYAxisAlignment(alignment: AxisAlignment.Alignment): Axis = {
    yAxisAlignment = Some(alignment)
    this
  }

  def setXAxisAlignment(alignment: AxisAlignment.Alignment): Axis = {
    xAxisAlignment = Some(alignment)
    this
  }

  def addPlot(plot: Plot): Axis = {
    plots.addOne(plot)
    this
  }

  def addPlots(plot: Seq[Plot]): Axis = {
    plots.addAll(plot)
    this
  }

  def setWidth(width: Int): Axis = {
    this.width = Some(width)
    this
  }

  def setHeight(height: Int): Axis = {
    this.height = Some(height)
    this
  }

  def setXMin(xmin: Int): Axis = {
    this.xmin = Some(xmin)
    this
  }

  def setXMax(xmax: Int): Axis = {
    this.xmax = Some(xmax)
    this
  }

  def setYMin(ymin: Int): Axis = {
    this.ymin = Some(ymin)
    this
  }

  def setYMax(ymax: Int): Axis = {
    this.ymax = Some(ymax)
    this
  }

  def addLegend(legend: Legend): Axis = {
    this.legend = Some(legend)
    this
  }

  override def getCustomColors: List[CustomColor] =
    plots.flatMap(_.getCustomColors).toSet.toList

  override def plot: String = {
    val plot =
      s"""\\begin{axis}[
        $customAxisConfigurationLines
        $getBoilerplateCode
       ]
       ${plots.map(_.plot).mkString("\n")}
       ${if (legend.nonEmpty) legend.get.plot(plots.toList) else ""}
       \\end{axis}"""
    Plottable.purgeEmptyLines(plot)
  }

  protected def customAxisConfigurationLines: String

  private def getBoilerplateCode: String = {
    val symbolicXCoords =
      if (plots.exists(_.getEntries.exists(_.isSymbolic(0)))) {
        Some(plots.flatMap(_.getEntries.map(_.getColumnValue(0))))
      } else {
        None
      }
    val symbolicYCoords =
      if (plots.exists(_.getEntries.exists(_.isSymbolic(1)))) {
        Some(plots.flatMap(_.getEntries.map(_.getColumnValue(1))))
      } else {
        None
      }
    s"""${if (width.nonEmpty) s"width=${width.get}cm," else ""}
        ${if (height.nonEmpty) s"height=${height.get}cm," else ""}
        ${symbolicXCoords.processOrElse(
      sym => s"symbolic x coords={${sym.mkString(",")}},",
      "")}
        ${symbolicYCoords.processOrElse(
      sym => s"symbolic y coords={${sym.mkString(",")}},",
      "")}
        ${yAxisAlignment.processOrElse(
      value => s"axis y line$noYArrowTip=$value,",
      "")}
        ${xAxisAlignment.processOrElse(
      value => s"axis x line$noXArrowTip=$value,",
      "")}
        ${ytickspt.processOrElse(value => s"y=${value}pt,", "")}
        ${xtickspt.processOrElse(value => s"x=${value}pt,", "")}
        ${enlargeLimits.processOrElse(
      value => s"enlargelimits={lower, $value},",
      "")}
        ${if (xmin.nonEmpty) s"xmin=${xmin.get}," else ""}
        ${if (xmax.nonEmpty) s"xmax=${xmax.get}," else ""}
        ${if (ymin.nonEmpty) s"ymin=${ymin.get}," else ""}
        ${if (ymax.nonEmpty) s"ymax=${ymax.get}," else ""}
        ${if (!drawXTicks) s"xtick style={draw=none}," else ""}
        ${if (!drawYTicks) s"ytick style={draw=none}," else ""}
        ${if (!xticklabels) "xticklabels=\\empty," else ""}
        ${if (!yticklabels) "yticklabels=\\empty," else ""}"""
  }

}
