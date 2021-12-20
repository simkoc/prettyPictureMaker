package de.tubs.ias.ppm.basics

import de.tubs.ias.ppm.colors.{Color, CustomColor}

trait Plot extends Plottable {

  protected val color: Color
  protected val coordinates: Seq[Coordinate]

  def getCustomPlotConfigLines: String

  override def plot: String = {
    println("stuff: " + getCustomPlotConfigLines)
    s"""\\addplot[
         |  draw = $color,
         |  $getCustomPlotConfigLines
         |] coordinates {${coordinates.map(_.toString).mkString(" ")}};
         |""".stripMargin
  }

  override def getCustomColors: List[CustomColor] = color match {
    case x: CustomColor => List(x)
    case _              => List()
  }

}
