package de.halcony.ppm.graph.generics

import de.halcony.ppm.colors.{Black, Color, CustomColor}
import de.halcony.ppm.graph.DataTableElement
import de.halcony.ppm.graph.visual.bar.NodesNearCoords
import de.halcony.ppm.utility.OptionExtensions.ExtendedOption

import scala.collection.mutable.ListBuffer

trait Plot extends Plottable {

  protected var color: Color = Black
  protected var entries: ListBuffer[DataTableElement] = ListBuffer()
  protected var name: Option[String] = None
  protected var nodesNearCoords: Option[NodesNearCoords] = None

  def setNodesNearCoords(specs: NodesNearCoords): Plot = {
    nodesNearCoords = Some(specs)
    this
  }

  def setName(name: String): Plot = {
    this.name = Some(name)
    this
  }

  def getName: Option[String] = name

  def addData(entry: DataTableElement): Plot = {
    assert(entries.isEmpty || entries.head.getHeaderRow == entry.getHeaderRow,
           "the header row must match the already entered entries")
    this.entries.addOne(entry)
    this
  }

  def addData(entries: Seq[DataTableElement]): Plot = {
    entries.foreach(entry =>
      assert(entries.isEmpty || entries.head.getHeaderRow == entry.getHeaderRow,
             "the header row must match the already entered entries"))
    this.entries.addAll(entries)
    this
  }

  def setColor(color: Color): Plot = {
    this.color = color
    this
  }

  protected def generateTableSpecs: String = {
    s"${nodesNearCoords.processOrElse(_.getDataTableSpecs(entries.head), "")}"
  }

  def getEntries: Seq[DataTableElement] = entries.toList

  protected def getCustomPlotConfigLines: String

  override def plot: String = {
    s"""\\addplot[
         |  color = $color,
         |  $getCustomPlotConfigLines
         |  ${nodesNearCoords.processOrElse(value => s"${value.getSpecs},", "")}
         |] table[$generateTableSpecs] {
         | ${entries.head.getHeaderRow}
         | ${entries.map(_.getValueRow).mkString("\n")}
         |};""".stripMargin
  }

  override def getCustomColors: List[CustomColor] = color match {
    case x: CustomColor => List(x)
    case _              => List()
  }

}
