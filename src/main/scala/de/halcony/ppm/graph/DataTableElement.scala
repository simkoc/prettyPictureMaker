package de.halcony.ppm.graph

trait DataTableElement {

  private val columnNumbers: Map[String, Int] = {
    (0 until getColumnCount).map(pos => getColumnValue(pos) -> pos)
  }.toMap

  def getHeaderRow: String
  def getValueRow: String
  def getColumnName(nth: Int): String
  def getColumnNumber(name: String): Int = {
    columnNumbers.getOrElse(
      name,
      throw new RuntimeException(s"there is now column named $name"))
  }
  def getColumnValue(nth: Int): String
  def getColumnCount: Int
  def isSymbolic(nth: Int): Boolean = {
    try {
      getColumnValue(nth).toDouble
      false
    } catch {
      case _: Throwable => true
    }
  }

}

object DataTableElement {

  def areSymbolic(nth: Int, elements: Seq[DataTableElement]): Boolean = {
    elements.exists(_.isSymbolic(nth))
  }

}

case class Coordinate[X, Y](x: X, y: Y) extends DataTableElement {

  override def getColumnCount: Int = 2
  override def getHeaderRow: String = "x y"
  override def getValueRow: String = s"$x $y"
  override def getColumnName(nth: Int): String = {
    nth match {
      case 0 => "x"
      case 1 => "y"
      case _ =>
        throw new IndexOutOfBoundsException(
          "this data table element has only two columns")
    }
  }

  override def getColumnValue(nth: Int): String = {
    nth match {
      case 0 => x.toString
      case 1 => y.toString
      case _ =>
        throw new IndexOutOfBoundsException(
          "this data table element has only two columns")
    }
  }
}
