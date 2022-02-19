package de.halcony.ppm.graph.visual.bar

import de.halcony.ppm.graph.DataTableElement
import de.halcony.ppm.style.Style
import de.halcony.ppm.utility.OptionExtensions.ExtendedOption

class NodesNearCoords() {

  private var style: Option[Style] = None
  private var dataColumn: Int = 0

  def setDataColumn(nth: Int): NodesNearCoords = {
    dataColumn = nth
    this
  }

  def setStyle(style: Style): NodesNearCoords = {
    this.style = Some(style)
    this
  }

  def getDataTableSpecs(examplecTableElement: DataTableElement): String = {
    s"meta=${examplecTableElement.getColumnName(dataColumn)},"
  }

  def getSpecs: String = {
    s"""point meta=explicit symbolic,
       |nodes near coords,
       |${style.processOrElse(
         value => s"nodes near coords style=${value.getStyle},",
         "")}
       |""".stripMargin
  }

}
