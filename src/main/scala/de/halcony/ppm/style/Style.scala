package de.halcony.ppm.style

import FontSizes.FontSize
import de.halcony.ppm.style.Anchor.Anchor
import de.halcony.ppm.utility.OptionExtensions.ExtendedOption

class Style {

  private var fontSize: Option[FontSize] = None
  private var anchor: Option[Anchor] = None

  def setFontSize(size: FontSize): Style = {
    this.fontSize = Some(size)
    this
  }

  def setAnchor(anchor: Anchor): Style = {
    this.anchor = Some(anchor)
    this
  }

  def getStyle: String = {
    val fontSizeStr = fontSize.processOrElse(value => s"font=$value,", "")
    val anchorStr = anchor.processOrElse(value => s"anchor=$value", "")
    s"{$fontSizeStr$anchorStr}"
  }

}
