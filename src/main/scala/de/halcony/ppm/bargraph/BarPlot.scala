package de.halcony.ppm.bargraph

import de.halcony.ppm.basics.Coordinate
import de.halcony.ppm.colors.Color

case class BarPlot(lineColor: Color,
                   lineWidth: Double,
                   fillColor: Color,
                   coordinates: Seq[Coordinate],
                   label: Option[String] = None) {

  def getPlotCommand: String = {
    s"""\\addplot[
       | draw = $lineColor,
       | line width = ${lineWidth}mm,
       | fill = $fillColor
       |] coordinates {${coordinates.map(_.toString).mkString(" ")}};
       |""".stripMargin
  }
}
