package de.halcony.ppm.graph.generics

import de.halcony.ppm.colors.{Black, Color, CustomColor}
import de.halcony.ppm.graph.Coordinate

import scala.collection.mutable.ListBuffer

trait Plot[T <: Plot[T]] extends Plottable {

  protected var color: Color = Black
  protected var coordinates: ListBuffer[Coordinate] = ListBuffer()
  protected var name: Option[String] = None

  def setName(name: String): T = {
    this.name = Some(name)
    this.asInstanceOf[T]
  }

  def getName: Option[String] = name

  def addCoordinate(coordinate: Coordinate): T = {
    this.coordinates.addOne(coordinate)
    this.asInstanceOf[T]
  }

  def addCoordinates(coordinates: Seq[Coordinate]): T = {
    this.coordinates.addAll(coordinates)
    this.asInstanceOf[T]
  }

  def setColor(color: Color): T = {
    this.color = color
    this.asInstanceOf[T]
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
