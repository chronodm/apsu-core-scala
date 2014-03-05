package apsu.demo.model.rocks.components

import apsu.util.Enum

case class Rock(size: Size)

sealed trait Size extends Size.Value

object Size extends Enum[Size] {
  case object Small extends Size { override val name = "Small" }; Small
  case object Medium extends Size { override val name = "Medium" }; Medium
  case object Large extends Size { override val name = "Large" }; Large
}