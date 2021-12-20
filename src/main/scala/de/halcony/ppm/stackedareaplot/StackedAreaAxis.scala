package de.halcony.ppm.stackedareaplot

import de.halcony.ppm.basics.{Axis, Plot}
import de.halcony.ppm.legends.Legend

case class StackedAreaAxis(plots: Seq[StackedAreaPlot],
                           width: Option[Int] = None,
                           height: Option[Int] = None,
                           xticks: Option[Seq[String]] = None,
                           yticks: Option[Seq[String]] = None,
                           xmin: Option[Int] = Some(0),
                           xmax: Option[Int] = None,
                           ymin: Option[Int] = None,
                           ymax: Option[Int] = None,
                           legend : Option[Legend] = None)
    extends Axis {

  override def getLegend: Option[Legend] = legend

  override protected def getPlots: Seq[Plot] = plots

  override protected def getWidth: Option[Int] = width

  override protected def getHeight: Option[Int] = height

  override protected def customAxisConfigurationLines: String = {
    s"""area style,
        stack plots=y,
      """.stripMargin
  }

  override protected def getXMin: Option[Int] = xmin

  override protected def getXMax: Option[Int] = xmax

  override protected def getYMin: Option[Int] = ymin

  override protected def getYMax: Option[Int] = ymax
}
