package de.halcony.ppm.graph.generics

object NodesAlignment extends Enumeration {
  type Alignment = Value
  val HORIZONTAL: NodesAlignment.Value = Value("horizontal")
  val VERTICAL: NodesAlignment.Value = Value("vertical")
}

object AxisAlignment extends Enumeration {

  type Alignment = Value
  val CENTER: AxisAlignment.Value = Value("center")
  val LEFT: AxisAlignment.Value = Value("left")
  val RIGHT: AxisAlignment.Value = Value("right")

}
