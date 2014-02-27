package apsu.util

/**
 * A utility class for approximating Java-style enums with pattern matching.
 * Usage:
 * <pre>
 * sealed trait Fruit extends Fruit.Value
 *
 * object Fruit extends Enum[Fruit] {
 *   case object Apple extends Fruit { override val name = "apple" }; Apple
 *   case object Banana extends Fruit { override val name = "banana" }; Banana
 *   case object Cherry extends Fruit { override val name = "cherry" }; Cherry
 * }
 * </pre>
 * @tparam A The class of the value
 */
trait Enum[A <: NamedEnumValue] {

  // ------------------------------------------------------
  // Inner classes

  trait Value extends NamedEnumValue { self: A => _values :+= this }

  // ------------------------------------------------------
  // Fields

  private var _values = List.empty[A]

  // ------------------------------------------------------
  // Methods

  def values = _values
  def named(name: String): Option[A] = values find { _.name == name }
}

trait NamedEnumValue { def name: String }
