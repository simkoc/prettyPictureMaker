package de.halcony.ppm.stackedareaplot

import de.halcony.ppm.basics.{Coordinate, Plot}
import de.halcony.ppm.colors.Color

case class StackedAreaPlot(override val coordinates: Seq[Coordinate], override val color : Color) extends Plot {

  override def getCustomPlotConfigLines: String = ""

  override def plot: String = {
    s"""\\addplot[
       |  fill = $color,
       |  $getCustomPlotConfigLines
       |] coordinates {${coordinates.map(_.toString).mkString(" ")}}
       | \\closedcycle;
       |""".stripMargin
  }

}
