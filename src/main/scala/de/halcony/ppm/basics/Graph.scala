package de.halcony.ppm.basics

import wvlet.log.LogSupport

import java.io.{File, FileWriter}
import java.lang.{ProcessBuilder => jProcessBuilder}
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, ExecutionContext, Future, TimeoutException}
import scala.sys.process._

case class Graph(axis: Axis) extends LogSupport {

  def plot: String = {
    val bw = new StringBuilder()
    bw.append(getBoilerplateHead)
    bw.append(axis.plot + "\n")
    bw.append(getBoilerplateTail)
    bw.toString()
  }

  def writeToFile(texFile: String): Unit = {
    val fw = new FileWriter(new File(texFile))
    try {
      fw.write(plot)
    } finally {
      fw.flush()
      fw.close()
    }
  }

  def compile(texFile: String,
              timeout: Int = 10000,
              PDFLATEX: String = "pdflatex"): Boolean = {
    assert(texFile.endsWith(".tex"))
    writeToFile(texFile)
    val folder = texFile.split("/").reverse.tail.reverse.mkString("/")
    val baseFile = texFile.split("/").last
    val pdf = s"${baseFile.substring(0, baseFile.length - 4)}.pdf"
    val svg = s"${baseFile.substring(0, baseFile.length - 4)}.svg"
    val future = Future {
      info(s"compiling tex file $baseFile")
      new jProcessBuilder(PDFLATEX, baseFile)
        .directory(new File(folder))
        .!!
    }(ExecutionContext.global)
    try {
      Await.result(future, Duration(timeout, MILLISECONDS))
      true
    } catch {
      case _: TimeoutException =>
        error(s"creation of $texFile timed out")
        false
    }
  }

  private def getBoilerplateHead: String = {
    val customColors = axis.getCustomColors
    s"""\\documentclass[tikz]{standalone}
       |\\usepackage{pgfplots}
       |\\usetikzlibrary{patterns}
       |${customColors
         .map(_.getColorDefinition)
         .mkString("\n")}
       |\\begin{document}
       |\\begin{tikzpicture}
       |""".stripMargin
  }

  private def getBoilerplateTail: String = {
    """\end{tikzpicture}
      |\end{document}
      |""".stripMargin
  }

}
