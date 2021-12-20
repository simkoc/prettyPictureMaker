package de.halcony.ppm.basics

import de.halcony.ppm.colors.{Color, CustomColor}

trait Plot extends Plottable {

  protected val color: Color
  protected val coordinates: Seq[Coordinate]
  protected val name : Option[String]

  def getName: Option[String] = name

  def getCustomPlotConfigLines: String

  override def plot: String = {
    s"""\\addplot[
         |  color = $color,
         |  $getCustomPlotConfigLines
         |] coordinates {${coordinates.map(_.toString).mkString(" ")}};
         |""".stripMargin
  }

  override def getCustomColors: List[CustomColor] = color match {
    case x: CustomColor => List(x)
    case _              => List()
  }

}
