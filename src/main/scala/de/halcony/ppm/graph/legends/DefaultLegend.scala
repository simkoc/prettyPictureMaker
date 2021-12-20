package de.halcony.ppm.graph.legends

import de.halcony.ppm.graph.generics.Plot

class DefaultLegend[T <: Plot[T]]() extends Legend[T] {

  override def getLayoutConfigLines: String = ""

}

