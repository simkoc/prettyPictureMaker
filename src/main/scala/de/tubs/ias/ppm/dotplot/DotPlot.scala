package de.tubs.ias.ppm.dotplot

import de.tubs.ias.ppm.basics.{Coordinate, Plot}
import de.tubs.ias.ppm.colors.{Black, Color}

case class DotPlot(override val coordinates: Seq[Coordinate],
                   override val color: Color = Black,
                   connect: Boolean = false)
    extends Plot {

  override def getCustomPlotConfigLines: String = {
    s"""${if (!connect) { "only marks" } else ""}"""
  }

}
