package de.tubs.ias.ppm.tikzGeneral

import wvlet.log.LogSupport

import java.io.{BufferedWriter, File, FileWriter}
import scala.sys.process._
import java.lang.{ProcessBuilder => jProcessBuilder}

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
  private val PDF2SVG = "pdf2svg"

  final def compile(texFile: String): Unit = {
    assert(texFile.endsWith(".tex"))
    val folder = texFile.split("/").reverse.tail.reverse.mkString("/")
    val baseFile = texFile.split("/").last
    val pdf = s"${baseFile.substring(0, baseFile.length - 4)}.pdf"
    val svg = s"${baseFile.substring(0, baseFile.length - 4)}.svg"
    info(s"compiling tex file $baseFile")
    new jProcessBuilder(PDFLATEX, baseFile)
      .directory(new File(folder))
      .!!
    info(s"processing $pdf to $svg")
    new jProcessBuilder(PDF2SVG, pdf, svg)
      .directory(new File(folder))
      .!!
  }
}
