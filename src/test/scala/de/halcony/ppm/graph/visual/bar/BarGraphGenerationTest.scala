package de.halcony.ppm.graph.visual.bar

import de.halcony.ppm.colors._
import de.halcony.ppm.graph.generics.AxisAlignment.{CENTER, LEFT}
import de.halcony.ppm.graph.{Coordinate, Graph}
import de.halcony.ppm.style.Anchor.{EAST, WEST}
import de.halcony.ppm.style.FontSizes.TINY
import de.halcony.ppm.style.Style
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BarGraphGenerationTest extends AnyWordSpec with Matchers {

  val testOutputDirectory = "resources/test/barGraph/"

  "vertical bar graph generation" should {
    "generate a bar graph with  lines of a single plot" in {
      new Graph().
        addAxis(new BarPlotAxis().setBarOrientation(BarOrientation.vertical).addPlot(
          new BarPlot().setColor(Red).addData(List(Coordinate("0", "1"), Coordinate("1", "2")))
        )
      ).compile(s"$testOutputDirectory/vertical1.tex") shouldBe true
    }
    "generate a bar with 2 lines and multiple plots" in {
      new Graph()
        .addAxis(new BarPlotAxis().setBarOrientation(BarOrientation.vertical).addPlots(
          List(
           new BarPlot().setColor(Red)
             .addData(List(Coordinate("0", "1"), Coordinate("1", "2"), Coordinate("2", "4"))),
           new BarPlot().setColor(Blue)
             .addData(List(Coordinate("0", "3"), Coordinate("1", "1"), Coordinate("2", "0.5")))
        )
      )).compile(s"$testOutputDirectory/vertical2.tex") shouldBe true
    }
    "generate a plot with labeled axis instead of numerical" in {
      new Graph().addAxis(
        new BarPlotAxis().setBarOrientation(BarOrientation.vertical).addPlots(
        List(
          new BarPlot().setColor(Red).addData(List(Coordinate("A", "1"), Coordinate("B", "2"), Coordinate("C", "4"))),
          new BarPlot().setColor(Blue).addData(List(Coordinate("A", "3"), Coordinate("B", "1"), Coordinate("C", "0.5")))
        )
      )).compile(s"$testOutputDirectory/vertical3.tex") shouldBe true
    }
    "generate a plot with labeled axis instead of numerical and legend" in {
      new Graph().addAxis(
        new BarPlotAxis().setBarOrientation(BarOrientation.vertical).addPlots(
        List(
          new BarPlot().setColor(Red).addData(List(Coordinate("A", "1"), Coordinate("B", "2"), Coordinate("C", "4"))).setName("weird-1"),
          new BarPlot().setColor(Blue).addData(List(Coordinate("A", "3"), Coordinate("B", "1"), Coordinate("C", "0.5"))).setName("weird-2")
        )
      )).compile(s"$testOutputDirectory/vertical4.tex") shouldBe true
    }
    "generate plot with custom color" in {
      val tdgreen = CustomColor("tdgreen",0,0.6,0)
      new Graph()
        .addAxis(
          new BarPlotAxis().setBarOrientation(BarOrientation.vertical).addPlot(
            new BarPlot().setColor(tdgreen).addData(List(Coordinate("0", "1"), Coordinate("1", "2")))
        )
      ).compile(s"$testOutputDirectory/vertical5.tex") shouldBe true
    }
    "generate a two sided bar graph without spaces" in {
      new Graph()
        .addAxis(
          new BarPlotAxis().setBarOrientation(BarOrientation.horizontal)
            .setBarShift(0)
            .setBarWidth(4)
            .addPlot(
              new BarPlot().setColor(Green).setLineColor(Black).addData(
                List(Coordinate("-58","A"),Coordinate("-45","B"),Coordinate("-43","C"))
              ).setNodesNearCoords(new NodesNearCoords()
                .setDataColumn(1)
                .setStyle(new Style().setAnchor(EAST).setFontSize(TINY)))
            ).addPlot(
              new BarPlot().setColor(Red).setLineColor(Black).addData(
                List(Coordinate("19","D"),Coordinate("35","E"),Coordinate("65","F"))
              ).setNodesNearCoords(new NodesNearCoords()
                .setDataColumn(1)
                .setStyle(new Style().setAnchor(WEST).setFontSize(TINY)))
            ).setXAxisAlignment(LEFT).setYAxisAlignment(CENTER).setNoYTickLabels().setYTicksSpacing(4)
            .enlargeLimits(0.2).disableYTicks().disableYArrowTip().disableXArrowTip()
        ).compile(s"$testOutputDirectory/keyness.tex") shouldBe true
    }
  }
}
