package de.tubs.ias.ppm.bargraph

import de.tubs.ias.ppm.bargraph.BarOrientation.BarOrientation
import de.tubs.ias.ppm.bargraph.Sorting.{ASC, DESC, SortingDirection}
import de.tubs.ias.ppm.tikzGeneral.{Red, TikzSVG}
import wvlet.log.LogSupport

import scala.io.Source

class BarGraph(outSvg: String, axis: Axis, plots: Seq[Plot])
    extends TikzSVG(outSvg) {

  override protected def createGraphTex(): String = {
    axis.createAxis(plots)
  }

}

object BarGraph extends LogSupport {

  def fromCsv(csvs: Seq[String],
              out: String,
              orientation: BarOrientation,
              offset: Int = 0,
              steps: Int = 1,
              sorting: Option[SortingDirection] = None): BarGraph = {
    val plots = csvs.map { csv =>
      val csvSource = Source.fromFile(csv)
      info(s"reading in csv file $csv")
      try {
        var lineCounter = 0
        val coordinates: Seq[Coordinate] = csvSource
          .getLines()
          .map { line =>
            lineCounter += 1
            line.split(",").toList match {
              case x :: y :: Nil =>
                Coordinate(x, y)
              case x :: Nil =>
                Coordinate((lineCounter * steps + offset).toString, x)
              case _ =>
                throw new RuntimeException(
                  s"line $lineCounter has bad format : ${line.mkString(",")} (expected 2 values only)")
            }
          }
          .toSeq
        info(s"read in ${coordinates.length} coordinates")
        val sorted = sorting match {
          //todo: sorting should be done relative to the orientation of the graph!
          case Some(ASC) =>
            info("sorting values ascending")
            println(coordinates)
            val buff = coordinates.sortBy(_.x)
            println(buff)
            buff
          case Some(DESC) =>
            info("sorting values descending")
            coordinates.sortBy(_.x).reverse
          case None => coordinates
          case value =>
            throw new RuntimeException(
              s"this must never happen - the fuck is sorting $value")
        }
        Plot(Red, 0.04, Red, sorted)
      } finally {
        csvSource.close()
      }
    }
    val max: Option[Int] = orientation match {
      case de.tubs.ias.ppm.bargraph.BarOrientation.horizontal =>
        Coordinate.getMaxY(plots.flatMap(_.coordinates))
      case de.tubs.ias.ppm.bargraph.BarOrientation.vertical =>
        Coordinate.getMaxY(plots.flatMap(_.coordinates))
    }

    //plots.map(_.coordinates.map(_.getValue).max).max
    val axis = Axis(orientation, 0.05, 12, Some(offset), max)
    new BarGraph(out, axis, plots)
  }

}
