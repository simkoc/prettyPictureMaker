package de.tubs.ias.ppm.bargraph

import de.tubs.ias.ppm.tikzGeneral.Color

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
