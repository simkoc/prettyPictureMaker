package de.halcony.ppm.dotplot

import de.halcony.ppm.basics.{Axis, Plot}
import de.halcony.ppm.legends.Legend

case class DotPlotAxis(plots: Seq[DotPlot],
                       width: Option[Int] = None,
                       height: Option[Int] = None,
                       xtick: Option[Seq[String]] = None,
                       ytick: Option[Seq[String]] = None,
                       xmin: Option[Int] = None,
                       xmax: Option[Int] = None,
                       ymin: Option[Int] = None,
                       ymax: Option[Int] = None,
                       legend : Option[Legend] = None)
    extends Axis {

  override def getLegend: Option[Legend] = legend

  override def customAxisConfigurationLines: String = {
    s"""${if (xtick.nonEmpty) { s"xtick=${xtick.mkString(",")}," } else ""}
        ${if (ytick.nonEmpty) { s"ytick=${ytick.mkString(",")}," } else ""}"""
  }

  override protected def getPlots: Seq[Plot] = plots.toList

  override protected def getWidth: Option[Int] = width

  override protected def getHeight: Option[Int] = height

  override protected def getXMin: Option[Int] = xmin

  override protected def getXMax: Option[Int] = xmax

  override protected def getYMin: Option[Int] = ymin

  override protected def getYMax: Option[Int] = ymax
}
