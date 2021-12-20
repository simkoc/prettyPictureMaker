package de.halcony.ppm.basics

import de.halcony.ppm.colors.CustomColor

trait Plottable {

  def plot: String

  def getCustomColors: List[CustomColor]

}

object Plottable {

  def purgeEmptyLines(string: String): String = {
    string.stripMargin
      .split("\n")
      .filter(_.replace(" ", "") != "")
      .mkString("\n")
  }

}
