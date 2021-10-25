package de.tubs.ias.ppm.bargraph

import de.tubs.ias.ppm.tikzGeneral.{Blue, CustomColor, Red, TikzSVG}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BarGraphGenerationTest extends AnyWordSpec with Matchers {

  val testOutputDirectory = "resources/test/barGraph/"

  "vertical bar graph generation" should {
    "generate a bar graph with  lines of a single plot" in {
      val bar = new BarGraph(
        s"$testOutputDirectory/vertical1.svg",
        Axis(BarOrientation.vertical, 1, 5, width = Some(10)),
        List(
          Plot(Red, 1, Red, List(Coordinate("0", "1"), Coordinate("1", "2")))
        )
      )
      bar.createPictureTex(s"$testOutputDirectory/vertical1.tex")
      TikzSVG.compile(s"$testOutputDirectory/vertical1.tex")
    }
    "generate a bar with 2 lines and multiple plots" in {
      val bar = new BarGraph(
        s"$testOutputDirectory/vertical2.svg",
        Axis(BarOrientation.vertical, 0.5, 5, width = Some(10)),
        List(
          Plot(Red, 1, Red, List(Coordinate("0", "1"), Coordinate("1", "2"), Coordinate("2", "4"))),
          Plot(Blue, 1, Blue, List(Coordinate("0", "3"), Coordinate("1", "1"), Coordinate("2", "0.5")))
        )
      )
      bar.createPictureTex(s"$testOutputDirectory/vertical2.tex")
      TikzSVG.compile(s"$testOutputDirectory/vertical2.tex")
    }
    "generate a plot with labeled axis instead of numerical" in {
      val bar = new BarGraph(
        s"$testOutputDirectory/vertical3.svg",
        Axis(BarOrientation.vertical, 0.5, 5, width = Some(10)),
        List(
          Plot(Red, 1, Red, List(Coordinate("A", "1"), Coordinate("B", "2"), Coordinate("C", "4"))),
          Plot(Blue, 1, Blue, List(Coordinate("A", "3"), Coordinate("B", "1"), Coordinate("C", "0.5")))
        )
      )
      bar.createPictureTex(s"$testOutputDirectory/vertical3.tex")
      TikzSVG.compile(s"$testOutputDirectory/vertical3.tex")
    }
    "generate a plot with labeled axis instead of numerical and legend" in {
      val bar = new BarGraph(
        s"$testOutputDirectory/vertical4.svg",
        Axis(BarOrientation.vertical, 0.5, 5, width = Some(10)),
        List(
          Plot(Red, 1, Red, List(Coordinate("A", "1"), Coordinate("B", "2"), Coordinate("C", "4")), Some("weird-1")),
          Plot(Blue, 1, Blue, List(Coordinate("A", "3"), Coordinate("B", "1"), Coordinate("C", "0.5")), Some("weird-2"))
        )
      )
      bar.createPictureTex(s"$testOutputDirectory/vertical4.tex")
      TikzSVG.compile(s"$testOutputDirectory/vertical4.tex")
    }
    "generate plot with custom color" in {
      val tdgreen = CustomColor("tdgreen",0,0.6,0)
      val bar = new BarGraph(
        s"$testOutputDirectory/vertical5.svg",
        Axis(BarOrientation.vertical, 1, 5, width = Some(10)),
        List(
          Plot(tdgreen, 1, tdgreen, List(Coordinate("0", "1"), Coordinate("1", "2")))
        ),
        Some(List(tdgreen))
      )
      bar.createPictureTex(s"$testOutputDirectory/vertical5.tex")
      TikzSVG.compile(s"$testOutputDirectory/vertical5.tex")
    }
  }


}
