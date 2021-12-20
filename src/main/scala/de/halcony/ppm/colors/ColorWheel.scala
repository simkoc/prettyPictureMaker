package de.halcony.ppm.colors

import scala.collection.mutable.{Map => MMap}

object NoMoreColorException extends Throwable {
  override def getMessage: String =
    "there are no more available colors in the color wheel"
}

class ColorWheel(amount: Int = 18) {

  private val used: MMap[Color, Boolean] = MMap(
    Red -> false,
    Blue -> false,
    Yellow -> false,
    Green -> false,
    Cyan -> false,
    Magenta -> false,
    Black -> false,
    Brown -> false,
    Lime -> false,
    Olive -> false,
    Orange -> false,
    Pink -> false,
    Purple -> false,
    Teal -> false,
    Violet -> false,
  )

  def getNextColor: Color = {
    val hit: Option[(Color, Boolean)] = used.find(!_._2)
    hit match {
      case Some((color, _)) =>
        used(color) = true
        color
      case None => throw NoMoreColorException
    }
  }

}
