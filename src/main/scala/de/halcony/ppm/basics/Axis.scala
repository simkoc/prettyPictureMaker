package de.halcony.ppm.basics
import de.halcony.ppm.colors.CustomColor
import wvlet.log.LogSupport

trait Axis extends Plottable with LogSupport {

  protected def getPlots: Seq[Plot]
  protected def getWidth: Option[Int]
  protected def getHeight: Option[Int]

  override def getCustomColors: List[CustomColor] =
    getPlots.flatMap(_.getCustomColors).toSet.toList

  override def plot: String = {
    val plot =
      s"""\\begin{axis}[
        $getBoilerplateCode
        $customAxisConfigurationLines
       ]
       ${getPlots.map(_.plot).mkString("\n")}
       \\end{axis}"""
    Plottable.purgeEmptyLines(plot)
  }

  protected def customAxisConfigurationLines: String

  private def getBoilerplateCode: String = {
    s"""${if (getWidth.nonEmpty) s"width=${getWidth.get}cm," else ""}
        ${if (getHeight.nonEmpty) s"height=${getHeight.get}cm," else ""}"""
  }

}
