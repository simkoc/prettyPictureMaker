package de.halcony.ppm.graph.legends

import de.halcony.ppm.graph.generics.Plot

trait Legend {

  def plot(plots: Seq[Plot]): String =
    s"""\\legend{${plots.map(_.getName.getOrElse("NoName")).mkString(",")}}"""

  def getLayoutConfigLines: String
}
