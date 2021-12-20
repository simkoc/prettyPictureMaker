package de.halcony.ppm.basics
import de.halcony.ppm.colors.CustomColor
import de.halcony.ppm.legends.Legend
import wvlet.log.LogSupport

//todo: change this to this configuration to make shared expansion easier
trait Axis extends Plottable with LogSupport {

  protected def getPlots: Seq[Plot]
  protected def getWidth: Option[Int]
  protected def getHeight: Option[Int]
  protected def getXMin: Option[Int]
  protected def getXMax: Option[Int]
  protected def getYMin: Option[Int]
  protected def getYMax: Option[Int]
  protected def getLegend : Option[Legend]

  override def getCustomColors: List[CustomColor] =
    getPlots.flatMap(_.getCustomColors).toSet.toList

  override def plot: String = {
    val plot =
      s"""\\begin{axis}[
        $getBoilerplateCode
        $customAxisConfigurationLines
       ]
       ${getPlots.map(_.plot).mkString("\n")}
       ${if (getLegend.nonEmpty) getLegend.get.plot(getPlots) else ""}
       \\end{axis}"""
    Plottable.purgeEmptyLines(plot)
  }

  protected def customAxisConfigurationLines: String

  private def getBoilerplateCode: String = {
    s"""${if (getWidth.nonEmpty) s"width=${getWidth.get}cm," else ""}
        ${if (getHeight.nonEmpty) s"height=${getHeight.get}cm," else ""}
        ${if (getXMin.nonEmpty) s"xmin=${getXMin.get}," else ""}
        ${if (getXMax.nonEmpty) s"xmax=${getXMax.get}," else ""}
        ${if (getYMin.nonEmpty) s"ymin=${getYMin.get}," else ""}
        ${if (getYMax.nonEmpty) s"ymax=${getYMax.get}," else ""}"""
  }

}
