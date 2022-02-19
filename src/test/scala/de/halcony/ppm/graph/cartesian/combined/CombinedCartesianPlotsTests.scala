package de.halcony.ppm.graph.cartesian.combined

import de.halcony.ppm.colors.Green
import de.halcony.ppm.graph.cartesian.dot.{DotPlot, DotPlotAxis}
import de.halcony.ppm.graph.cartesian.stackedarea.{StackedAreaAxis, StackedAreaPlot}
import de.halcony.ppm.graph.{Coordinate, Graph}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CombinedCartesianPlotsTests extends AnyWordSpec with Matchers {

  val testOutputDirectory = "resources/test/combinedPlots/"

  "creating a combined area/dot plot" should {

    "result in a basic combined graph" in {
      val outFile = s"$testOutputDirectory/simple.tex"
      val coordinatesA = List(Coordinate(0,1),Coordinate(1,2),Coordinate(2,1))
      val coordinatesB = List(Coordinate(0,0.5),Coordinate(1,1.5),Coordinate(1.5,1.2),Coordinate(2,0.5))
      new Graph().
        addAxis(new StackedAreaAxis()
          .addPlot(new StackedAreaPlot().addData(coordinatesA).setColor(Green)))
        .addAxis(new DotPlotAxis()
          .addPlot(new DotPlot().setConnected(true).addData(coordinatesB)))
        .compile(outFile,timeout = 3000) shouldBe true
    }

  }

}
