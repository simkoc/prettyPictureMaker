package de.tubs.ias.ppm.timepointsgraph

import de.tubs.ias.ppm.tikzGeneral.Color

case class TimePointPlot(color : Color, connect : Boolean, coordinates : Seq[TimeCoordinate]) {

  def getPlotCommand : String = {
    s"""\\addplot[
       |  draw = $color,
       |] coordinates {${coordinates.map(_.toString).mkString(" ")}};
       |""".stripMargin
  }

}
