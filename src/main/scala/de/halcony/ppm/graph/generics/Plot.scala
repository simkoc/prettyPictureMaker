package de.halcony.ppm.graph.generics

import de.halcony.ppm.colors.{Black, Color, CustomColor}
import de.halcony.ppm.graph.Coordinate

import scala.collection.mutable.ListBuffer

trait Plot extends Plottable {

  protected var color: Color = Black
  protected var coordinates: ListBuffer[Coordinate] = ListBuffer()
  protected var name: Option[String] = None

  def setName(name: String): Plot = {
    this.name = Some(name)
    this
  }

  def getName: Option[String] = name

  def addCoordinate(coordinate: Coordinate): Plot = {
    this.coordinates.addOne(coordinate)
    this
  }

  def addCoordinates(coordinates: Seq[Coordinate]): Plot = {
    this.coordinates.addAll(coordinates)
    this
  }

  def setColor(color: Color): Plot = {
    this.color = color
    this
  }

  def getCoordinates: Seq[Coordinate] = coordinates.toList

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
