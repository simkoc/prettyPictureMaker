package de.halcony.ppm.tikzGeneral

import de.halcony.ppm.colors.CustomColor
import wvlet.log.LogSupport

import java.io.{BufferedWriter, File, FileWriter}
import java.lang.{ProcessBuilder => jProcessBuilder}
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, ExecutionContext, Future, TimeoutException}
import scala.sys.process._

@deprecated
abstract class TikzSVG(outSvg: String,
                       customColors: Option[List[CustomColor]] = None) {

  private def getBoilerplateHead: String = {
    s"""\\documentclass[tikz,convert={outfile=$outSvg.svg}]{standalone}
        |\\usepackage{pgfplots}
        |\\usetikzlibrary{patterns}
        |${customColors
         .getOrElse(List())
         .map(_.getColorDefinition)
         .mkString("\n")}
        |\\begin{document}
        |\\begin{tikzpicture}
        |""".stripMargin
  }

  protected def createGraphTex(): String

  final def createPictureTex(outFile: String): Unit = {
    val bw = new BufferedWriter(new FileWriter(outFile))
    try {
      bw.write(getBoilerplateHead)
      bw.write(createGraphTex())
      bw.write(getBoilerplateTail)
    } finally {
      bw.flush()
      bw.close()
    }
  }

  private def getBoilerplateTail: String = {
    """\end{tikzpicture}
        |\end{document}
        |""".stripMargin
  }

}

object TikzSVG extends LogSupport {

  private val PDFLATEX = "pdflatex"

  final def compile(texFile: String, timeout: Int = 10000): Boolean = {
    assert(texFile.endsWith(".tex"))
    val folder = texFile.split("/").reverse.tail.reverse.mkString("/")
    val baseFile = texFile.split("/").last
    val future = Future {
      info(s"compiling tex file $baseFile")
      new jProcessBuilder(PDFLATEX, baseFile)
        .directory(new File(folder))
        .!!
    }(ExecutionContext.global)
    try {
      Await.result(future, Duration(timeout.toLong, MILLISECONDS))
      true
    } catch {
      case _: TimeoutException =>
        error(s"creation of $texFile timed out")
        false
    }
  }
}
