package de.tubs.ias.ppm

import de.halcony.argparse.{Parser, ParsingException, ParsingResult}
import de.tubs.ias.ppm.bargraph.{Axis, BarGraph, BarOrientation, Coordinate, Plot, Sorting}
import de.tubs.ias.ppm.tikzGeneral.{Red, TikzSVG, Yellow}
import wvlet.log.LogSupport

object PrettyPictureMaker extends LogSupport {

  private val parser : Parser = Parser("PrettyPictureMaker", "using raw input creates nice outputs")
    .addSubparser(Parser("dummy","use this to run the current flavor of test")
      .addPositional("out","the file to create the test in/for")
      .addDefault[ParsingResult => Unit]("func",dummyMain))
    .addSubparser(Parser("barPlot","create bar plots")
      .addPositional("csv","input file containing the raw data (ledeco analysis trackerStatistics)")
      .addPositional("orientation","the orientation of the bar {horizontal,vertical}")
      .addPositional("out", "the path to the file that shall be created (no ending, a .tex and .svg will be created)")
      .addOptional("sort","s","sort",None,s"how (if) to sort the values {asc,desc}")
      .addDefault[ParsingResult => Unit]("func",barPlotMain))


  def main(pargs : Array[String]) : Unit = {
    try {
      val pargv: ParsingResult = parser.parse(pargs)
      pargv.get[ParsingResult => Unit]("func").apply(pargv)
    } catch {
      case _: ParsingException =>
    }
  }

  private def splitOut(out : String) : (String,String) = {
    val outSplit = out.split("/")
    (outSplit.reverse.tail.reverse.mkString("/"), outSplit.last)
  }

  private def dummyMain(pargv : ParsingResult) : Unit = {
    val (outPath, outFile) = splitOut(pargv.get[String]("out"))
    val graph = new BarGraph(
      s"$outFile.svg",
      Axis(BarOrientation.horizontal, 0.05, 12, Some(0), Some(4)),
      List(Plot(Red, 0.4, Yellow, List(
        Coordinate("1", "0"), Coordinate("2", "1"), Coordinate("3", "2"), Coordinate("0", "3")))))
    val texFile = s"$outPath/$outFile.tex"
    info(s"writing tex into $texFile")
    graph.createPictureTex(texFile)
    info(s"compiling filing $texFile")
    TikzSVG.compile(texFile)
  }

  private def barPlotMain(pargv : ParsingResult) : Unit = {
    val orientation = pargv.get[String]("orientation").toLowerCase match {
      case "horizontal" => BarOrientation.horizontal
      case "vertical" => BarOrientation.vertical
    }
    val sorting = pargv.get[Option[String]]("sort") match {
      case Some("ASC") => Some(Sorting.ASC)
      case Some("DESC") => Some(Sorting.DESC)
      case None => None
      case value => throw new RuntimeException(s"the sorting value $value is not supported")
    }
    val (folder,file) = splitOut(pargv.get[String]("out"))
    val plot = BarGraph.fromCsv(
      pargv.get[String]("csv").split(",").toSeq,
      file,
      orientation,
      sorting = sorting
    )
    val texFile = s"$folder/$file.tex"
    plot.createPictureTex(texFile)
    TikzSVG.compile(texFile)
  }
}
