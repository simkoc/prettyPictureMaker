package de.halcony.ppm.dotplot

import de.halcony.ppm.basics.{Coordinate, Plot}
import de.halcony.ppm.colors.{Black, Color}

case class DotPlot(override val coordinates: Seq[Coordinate],
                   override val color: Color = Black,
                   connect: Boolean = false)
    extends Plot {

  override def getCustomPlotConfigLines: String = {
    s"""${if (!connect) { "only marks" } else ""}"""
  }

}
