package de.halcony.ppm.basics

case class Coordinate(x: String, y: String) {
  override def toString: String = s"($x,$y)"
}

object Coordinate {

  def apply(x: Int, y: Int): Coordinate = {
    Coordinate(x.toString, y.toString)
  }

  def apply(x: Double, y: Double): Coordinate = {
    Coordinate(x.toString, y.toString)
  }

  def getMaxX(seq: Seq[Coordinate]): Option[Int] = {
    try {
      Some(seq.map(_.x.toInt).max)
    } catch {
      case _: Throwable => None
    }
  }

  def getMaxY(seq: Seq[Coordinate]): Option[Int] = {
    try {
      Some(seq.map(_.y.toInt).max)
    } catch {
      case _: Throwable => None
    }
  }

}
