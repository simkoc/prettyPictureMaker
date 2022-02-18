package de.halcony.ppm.graph.visual.bar

import de.halcony.ppm.graph.generics.FontSizes.FontSize
import de.halcony.ppm.graph.generics.NodesAlignment
import de.halcony.ppm.utility.OptionExtensions.ExtendedOption

class NodesNearCoords() {

  private var alignment: Option[NodesAlignment.Alignment] = None
  private var fontSize: Option[FontSize] = None

  def setAlignment(alignment: NodesAlignment.Alignment): NodesNearCoords = {
    this.alignment = Some(alignment)
    this
  }

  def setFontSize(size: FontSize): NodesNearCoords = {
    fontSize = Some(size)
    this
  }

  def getSpecs: String = {
    s"""nodes near coords,
       |${alignment.processOrElse(value => s"nodes near coords align={$value},",
                                  "")}
       |${fontSize.processOrElse(
         value => s"nodes near coords style={font=$value},",
         "")}
       |""".stripMargin
  }

}
