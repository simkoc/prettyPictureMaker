package de.tubs.ias.ppm.tikzGeneral

trait Color {
  def toString: String
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
