package de.halcony.ppm.utility

object OptionExtensions {

  implicit class ExtendedOption[T](optional: Option[T]) {

    def processOrElse[Z](processor: T => Z, default: Z): Z = {
      optional match {
        case Some(value) => processor(value)
        case None        => default
      }
    }

  }
}
