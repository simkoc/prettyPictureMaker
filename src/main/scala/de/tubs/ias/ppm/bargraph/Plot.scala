package de.tubs.ias.ppm.bargraph

import de.tubs.ias.ppm.tikzGeneral.Color

case class Plot(lineColor: Color,
                lineWidth: Double,
                fillColor: Color,
                coordinates: Seq[Coordinate]) {

  def getPlotCommand: String = {
    s"""\\addplot[
       | draw = $lineColor,
       | line width = ${lineWidth}mm,
       | fill = $fillColor
       |] coordinates {${coordinates.map(_.toString).mkString(" ")}};
       |""".stripMargin
  }
}
