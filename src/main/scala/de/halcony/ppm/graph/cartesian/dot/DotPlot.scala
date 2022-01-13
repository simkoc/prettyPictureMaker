package de.halcony.ppm.graph.cartesian.dot

import de.halcony.ppm.graph.generics.Plot

class DotPlot() extends Plot {

  protected var connect: Boolean = false

  def setConnected(connect: Boolean): DotPlot = {
    this.connect = connect
    this
  }

  override def getCustomPlotConfigLines: String = {
    s"""${if (!connect) { "only marks" } else ""}"""
  }

}
