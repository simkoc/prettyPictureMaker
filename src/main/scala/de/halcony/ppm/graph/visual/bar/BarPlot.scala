package de.halcony.ppm.graph.visual.bar

import de.halcony.ppm.colors.{Black, Color, CustomColor}
import de.halcony.ppm.graph.generics.Plot

class BarPlot() extends Plot[BarPlot] {

  protected var lineColor : Color = Black
  protected var fillColor : Color = Black
  protected var lineWidth : Double = 0.01

  override def setColor(color: Color): BarPlot = {
    lineColor = color
    fillColor = color
    this
  }

  override def getCustomColors: List[CustomColor] =
    List(lineColor,fillColor).filter(_.isInstanceOf[CustomColor])
      .map(_.asInstanceOf[CustomColor])

  def setLineColor(color : Color) : BarPlot = {
    lineColor = color
    this
  }

  def setFillColor(color : Color) : BarPlot = {
    fillColor = color
    this
  }

  def setLineWidth(width : Double) : BarPlot = {
    lineWidth = width
    this
  }

  override def getCustomPlotConfigLines: String = {
    s"""draw = $lineColor,
       |line width = ${lineWidth}cm,
       |fill = $fillColor,""".stripMargin
  }
}
