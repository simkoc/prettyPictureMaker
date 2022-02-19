package de.halcony.ppm.graph.cartesian.stackedarea

import de.halcony.ppm.graph.generics.Plot

class StackedAreaPlot() extends Plot {

  override def getCustomPlotConfigLines: String = s"fill=$color"

  override def plot: String = {
    /*s"""\\addplot[
       |  fill = $color,
       |  $getCustomPlotConfigLines
       |] coordinates {${entries.map(_.toString).mkString(" ")}}
       | \\closedcycle;
       |""".stripMargin*/
    val superPlot = super.plot
    superPlot.substring(0, superPlot.length - 1) ++ "\n\\closedcycle;"
  }

}
