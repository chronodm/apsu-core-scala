package apsu.demo.model.rocks.components

case class Rock()

object Size extends Enumeration {
  type Size = Value
  val Small, Medium, Large = Value
}