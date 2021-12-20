package de.halcony.ppm.colors

trait Color {
  def toString: String
}

case class CustomColor(name: String, r: Double, g: Double, b: Double)
    extends Color {
  def getColorDefinition: String = s"\\definecolor{$name}{rgb}{$r,$g,$b}"

  override def toString: String = name
}

object White extends Color {
  override def toString: String = "white"
}

object Red extends Color {
  override def toString = "red"
}

object Blue extends Color {
  override def toString: String = "blue"
}

object Yellow extends Color {
  override def toString: String = "yellow"
}

object Green extends Color {
  override def toString: String = "green"
}

object Cyan extends Color {
  override def toString: String = "cyan"
}

object Magenta extends Color {
  override def toString: String = "magenta"
}

object Black extends Color {
  override def toString: String = "black"
}

object Brown extends Color {
  override def toString: String = "brown"
}

object Lime extends Color {
  override def toString: String = "lime"
}

object Olive extends Color {
  override def toString: String = "olive"
}

object Orange extends Color {
  override def toString: String = "orange"
}

object Pink extends Color {
  override def toString: String = "pink"
}

object Purple extends Color {
  override def toString: String = "purple"
}

object Teal extends Color {
  override def toString: String = "teal"
}

object Violet extends Color {
  override def toString: String = "violet"
}
