package de.halcony.ppm.stackedareaplot

import de.halcony.ppm.basics.{Coordinate, Graph}
import de.halcony.ppm.colors.{Green, Red}
import de.halcony.ppm.legends.DefaultLegend
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StackedAreaPlotTest extends AnyWordSpec with Matchers {

  val testOutputDirectory = "resources/test/stackedAreaPlot/"

  "creating a stacked area plot" should {

    "result in a basic stacked area plot with a single area" in {
      val outFile = s"$testOutputDirectory/simpleSingle.tex"
      val coordinates = List(Coordinate(0,1),Coordinate(1,2),Coordinate(2,1))
      Graph(StackedAreaAxis(List(StackedAreaPlot(coordinates,Green)))).compile(outFile,timeout = 3000) shouldBe true
    }

    "result in two stacked areas" in {
      val outFile = s"$testOutputDirectory/simpleTwo.tex"
      val coordinates = List(Coordinate(0,1),Coordinate(1,2),Coordinate(2,1))
      val plots = List(
        StackedAreaPlot(coordinates,Green),
        StackedAreaPlot(coordinates,Red)
      )
      Graph(StackedAreaAxis(plots)).compile(outFile,timeout = 3000) shouldBe true
    }

    "result in two stacked areas with a legend" in {
      val outFile = s"$testOutputDirectory/simpleTwoWithLegend.tex"
      val coordinates = List(Coordinate(0,1),Coordinate(1,2),Coordinate(2,1))
      val plots = List(
        StackedAreaPlot(coordinates,Green,name = Some("Green")),
        StackedAreaPlot(coordinates,Red, name = Some("Red"))
      )
      Graph(StackedAreaAxis(plots, legend = Some(DefaultLegend),width = Some(10))).compile(outFile,timeout = 3000) shouldBe true
    }

  }

}
