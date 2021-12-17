package de.tubs.ias.ppm.timepointsgraph

import de.tubs.ias.ppm.tikzGeneral.{Black, TikzSVG}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TimeGraphGenerationTest extends AnyWordSpec with Matchers {

  val testOutputDirectory = "resources/test/timeDotGraph/"

  "dot time graph generation" should {
    "generate a time dot plot for a single plot" in {
      val dot = new TimePointGraph(
        s"$testOutputDirectory/simple",
        TimePointsAxis(),
        List(
          TimePointPlot(Black,List(TimeCoordinate("0","1"),TimeCoordinate("1","2"),TimeCoordinate("2","1")))
        )
      )
      dot.createPictureTex(s"$testOutputDirectory/simple.tex")
      TikzSVG.compile(s"$testOutputDirectory/simple.tex") shouldBe true
    }
  }

}
