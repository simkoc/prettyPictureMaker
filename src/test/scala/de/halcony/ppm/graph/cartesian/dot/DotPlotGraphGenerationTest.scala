package de.halcony.ppm.graph.cartesian.dot

import de.halcony.ppm.graph.{Coordinate, Graph}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DotPlotGraphGenerationTest extends AnyWordSpec with Matchers {

  val testOutputDirectory = "resources/test/timeDotGraph/"

  "dot time graph generation" should {
    "generate a time dot plot for a single plot" in {
      val outFile = s"$testOutputDirectory/simple.tex"
      val coordinates = List(Coordinate(0,1),Coordinate(1,2),Coordinate(2,1))
      new Graph[DotPlot,DotPlotAxis]()
        .setAxis(new DotPlotAxis()
          .addPlot(new DotPlot().addCoordinates(coordinates)))
        .compile(outFile,timeout = 3000) shouldBe true
    }
  }

}
