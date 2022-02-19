package de.halcony.ppm.style

import de.halcony.ppm.style

object Anchor extends Enumeration {
  type Anchor = Value
  val NORTH: style.Anchor.Value = Value("north")
  val EAST: style.Anchor.Value = Value("east")
  val SOUTH: style.Anchor.Value = Value("south")
  val WEST: style.Anchor.Value = Value("west")
}
