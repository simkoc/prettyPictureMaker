package de.halcony.ppm.graph.cartesian.stackedarea

import de.halcony.ppm.graph.generics.Axis

class StackedAreaAxis() extends Axis {

  private var xticks: Option[Seq[String]] = None
  private var yticks: Option[Seq[String]] = None

  def setXTicks(xticks: Seq[String]): StackedAreaAxis = {
    this.xticks = Some(xticks)
    this
  }

  def setYTicks(yticks: Seq[String]): StackedAreaAxis = {
    this.yticks = Some(yticks)
    this
  }

  override protected def customAxisConfigurationLines: String = {
    s"""${if (xticks.nonEmpty) { s"xtick=${xticks.mkString(",")}," } else ""}
        ${if (yticks.nonEmpty) { s"ytick=${yticks.mkString(",")}," } else ""}
        area style,
        stack plots=y,
      """.stripMargin
  }

}
