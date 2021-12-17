package de.tubs.ias.ppm.timepointsgraph

import de.tubs.ias.ppm.tikzGeneral.{CustomColor, TikzSVG}

class TimePointGraph(outSvg : String, axis : TimePointsAxis, plots : Seq[TimePointPlot], customColors: Option[List[CustomColor]] = None) extends TikzSVG(outSvg,customColors) {

  override protected def createGraphTex(): String = axis.createAxis(plots)

}
