package de.halcony.ppm.graph.generics
import de.halcony.ppm.colors.CustomColor
import de.halcony.ppm.graph.generics.Side._
import de.halcony.ppm.graph.legends.Legend
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
  private var side: Side = LEFT

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

  def setSide(side: Side): Axis = {
    this.side = side
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
        axis y line*=${if (side == LEFT) "left" else "right"},
        ${if (height.nonEmpty) s"height=${height.get}cm," else ""}
        ${if (xmin.nonEmpty) s"xmin=${xmin.get}," else ""}
        ${if (xmax.nonEmpty) s"xmax=${xmax.get}," else ""}
        ${if (ymin.nonEmpty) s"ymin=${ymin.get}," else ""}
        ${if (ymax.nonEmpty) s"ymax=${ymax.get}," else ""}"""
  }

}
