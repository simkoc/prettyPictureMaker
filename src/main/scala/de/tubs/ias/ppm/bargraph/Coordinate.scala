package de.tubs.ias.ppm.bargraph

case class Coordinate(x : String,y : String) {
  override def toString : String = s"($x,$y)"
}