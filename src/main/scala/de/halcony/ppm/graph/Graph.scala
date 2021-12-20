package de.halcony.ppm.graph

import de.halcony.ppm.graph.generics.{Axis, Plot}
import wvlet.log.LogSupport
import scala.sys.process._
import java.lang.{ProcessBuilder => jProcessBuilder}
import java.io.{File, FileWriter}
import scala.concurrent.duration.{Duration, MILLISECONDS}
import scala.concurrent.{Await, ExecutionContext, Future, TimeoutException}

class Graph[X <: Plot[X], T <: Axis[X,T]]() extends LogSupport {

  protected var axis : Option[T] = None

  def setAxis(axis : T) : Graph[X,T] = {
    this.axis = Some(axis)
    this.asInstanceOf[Graph[X,T]]
  }

  def plot: String = {
    val bw = new StringBuilder()
    bw.append(getBoilerplateHead)
    bw.append(axis.get.plot + "\n")
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

  private def getBoilerplateHead: String = {
    val customColors = axis.get.getCustomColors
    s"""\\documentclass[tikz]{standalone}
       |\\usepackage{pgfplots}
       |\\usetikzlibrary{patterns}
       |${
      customColors
        .map(_.getColorDefinition)
        .mkString("\n")
    }
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
