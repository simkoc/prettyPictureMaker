package de.tubs.ias.ppm.dotplot

import de.tubs.ias.ppm.basics.{Axis, Plot}

case class DotPlotAxis(plots: Seq[DotPlot],
                       width: Option[Int] = None,
                       height: Option[Int] = None,
                       xtick: Option[Seq[String]] = None,
                       ytick: Option[Seq[String]] = None)
    extends Axis {

  override def customAxisConfigurationLines: String = {
    s"""${if (xtick.nonEmpty) { s"xtick=${xtick.mkString(",")}," } else ""}
        ${if (ytick.nonEmpty) { s"ytick=${ytick.mkString(",")}," } else ""}"""
  }

  override protected def getPlots: Seq[Plot] = plots.toList

  override protected def getWidth: Option[Int] = width

  override protected def getHeight: Option[Int] = height
}
