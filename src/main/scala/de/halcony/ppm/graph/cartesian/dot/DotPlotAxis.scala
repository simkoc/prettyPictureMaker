package de.halcony.ppm.graph.cartesian.dot

import de.halcony.ppm.graph.generics.Axis

class DotPlotAxis() extends Axis[DotPlot,DotPlotAxis] {

  private var xticks : Option[Seq[String]] = None
  private var yticks : Option[Seq[String]] = None

  def setXTicks(xticks : Seq[String]) : DotPlotAxis = {
    this.xticks = Some(xticks)
    this
  }

  def setYTicks(yticks : Seq[String]) : DotPlotAxis = {
    this.yticks = Some(yticks)
    this
  }

  override def customAxisConfigurationLines: String = {
    s"""${if (xticks.nonEmpty) { s"xtick=${xticks.mkString(",")}," } else ""}
        ${if (yticks.nonEmpty) { s"ytick=${yticks.mkString(",")}," } else ""}"""
  }

}
