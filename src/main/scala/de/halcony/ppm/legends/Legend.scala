package de.halcony.ppm.legends

import de.halcony.ppm.basics.Plot

trait Legend {

  def plot(plots : Seq[Plot]) : String = s"""\\legend{${plots.map(_.getName.getOrElse("NoName")).mkString(",")}}"""

  def getLayoutConfigLines : String
}
