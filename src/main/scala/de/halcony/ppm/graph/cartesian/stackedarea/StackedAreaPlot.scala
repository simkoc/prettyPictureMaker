package de.halcony.ppm.graph.cartesian.stackedarea

import de.halcony.ppm.graph.generics.Plot

class StackedAreaPlot()  extends Plot[StackedAreaPlot] {


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
