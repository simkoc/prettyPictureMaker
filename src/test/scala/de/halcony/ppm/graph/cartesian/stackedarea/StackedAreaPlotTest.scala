package de.halcony.ppm.graph.cartesian.stackedarea

import de.halcony.ppm.colors.{Green, Red}
import de.halcony.ppm.graph.legends.DefaultLegend
import de.halcony.ppm.graph.{Coordinate, Graph}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StackedAreaPlotTest extends AnyWordSpec with Matchers {

  val testOutputDirectory = "resources/test/stackedAreaPlot/"

  "creating a stacked area plot" should {

    "result in a basic stacked area plot with a single area" in {
      val outFile = s"$testOutputDirectory/simpleSingle.tex"
      val coordinates = List(Coordinate(0,1),Coordinate(1,2),Coordinate(2,1))
      new Graph[StackedAreaPlot,StackedAreaAxis]().
        setAxis(new StackedAreaAxis()
          .addPlot(new StackedAreaPlot().addCoordinates(coordinates).setColor(Green)))
        .compile(outFile,timeout = 3000) shouldBe true
    }

    "result in two stacked areas" in {
      val outFile = s"$testOutputDirectory/simpleTwo.tex"
      val coordinates = List(Coordinate(0,1),Coordinate(1,2),Coordinate(2,1))
      val plots = List(
        new StackedAreaPlot().addCoordinates(coordinates).setColor(Green),
        new StackedAreaPlot().addCoordinates(coordinates).setColor(Red)
      )
      new Graph[StackedAreaPlot,StackedAreaAxis]()
        .setAxis(new StackedAreaAxis().addPlots(plots))
        .compile(outFile,timeout = 3000) shouldBe true
    }

    "result in two stacked areas with a legend" in {
      val outFile = s"$testOutputDirectory/simpleTwoWithLegend.tex"
      val coordinates = List(Coordinate(0,1),Coordinate(1,2),Coordinate(2,1))
      val plots = List(
        new StackedAreaPlot().addCoordinates(coordinates).setColor(Green).setName("Green"),
        new StackedAreaPlot().addCoordinates(coordinates).setColor(Red).setName("Red")
      )
      new Graph[StackedAreaPlot,StackedAreaAxis]()
        .setAxis(new StackedAreaAxis().addPlots(plots).addLegend(new DefaultLegend()).setWidth(10))
        .compile(outFile,timeout = 3000) shouldBe true
    }

  }

}