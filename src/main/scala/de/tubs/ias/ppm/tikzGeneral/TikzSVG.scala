package de.tubs.ias.ppm.tikzGeneral

import java.io.{BufferedWriter, File, FileWriter}
import scala.sys.process._
import java.lang.{ProcessBuilder => jProcessBuilder}

abstract class TikzSVG(outSvg : String) {

  private def getBoilerplateHead : String = {
      s"""\\documentclass[tikz,convert={outfile=$outSvg.svg}]{standalone}
        |\\usepackage{pgfplots}
        |\\usetikzlibrary{patterns}
        |\\begin{document}
        |\\begin{tikzpicture}
        |""".stripMargin
  }

  protected def createGraphTex() : String

  final def createPictureTex(outFile : String) : Unit = {
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

  private def getBoilerplateTail : String = {
      """\end{tikzpicture}
        |\end{document}
        |""".stripMargin
  }

}

object TikzSVG {

  private val PDFLATEX = "pdflatex"
  private val PDF2SVG = "pdf2svg"

  final def compile(texFile : String) : Unit = {
    assert(texFile.endsWith(".tex"))
    val folder = texFile.split("/").reverse.tail.reverse.mkString("/")
    val pdf = s"${texFile.substring(0,texFile.length-4)}.pdf"
    val svg = s"${texFile.substring(0,texFile.length-4)}.svg"
    new jProcessBuilder(PDFLATEX,texFile)
      .directory(new File(folder))
      .!
    new jProcessBuilder(PDF2SVG,pdf,svg)
      .directory(new File(folder))
      .!
  }
}
