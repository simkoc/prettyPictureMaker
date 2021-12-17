package de.tubs.ias.ppm.timepointsgraph

case class TimeCoordinate(x : String, y : String) {

  override def toString: String = s"($x,$y)"

}
