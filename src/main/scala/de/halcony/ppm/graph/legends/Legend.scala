package de.halcony.ppm.graph.legends

import de.halcony.ppm.graph.generics.Plot

trait Legend[T <: Plot[T]] {

  def plot(plots : Seq[T]) : String = s"""\\legend{${plots.map(_.getName.getOrElse("NoName")).mkString(",")}}"""

  def getLayoutConfigLines : String
}
