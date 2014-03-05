package apsu.util

import org.junit.runner.RunWith

import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.junit.JUnitRunner
import apsu.util.Fruit._

sealed trait Fruit extends Fruit.Value

object Fruit extends Enum[Fruit] {
  case object Apple extends Fruit {override val name = "apple"}; Apple
  case object Banana extends Fruit {override val name = "banana"}; Banana
  case object Cherry extends Fruit {override val name = "cherry"}; Cherry
}

@RunWith(classOf[JUnitRunner])
class EnumSpec extends FlatSpec with Matchers {

  classOf[Enum[_]].getName should
    "provide values in the order declared in the companion object" in {
    Fruit.values should be(List(Apple, Banana, Cherry))
  }

  it should "provide the ability to retrieve values by name" in {
    for (f <- Fruit.values) Fruit.named(f.name) should be(Some(f))
  }

  it should "return None when asked for a non-existent name" in {
    Fruit.named("broccoli") should be(None)
  }

  it should "support pattern matching" in {
    def doMatch(f: Fruit) = {
      f match {
        case Apple => Some(1)
        case Banana => Some(2)
        case Cherry => Some(3)
        case _ => None
      }
    }

    doMatch(Apple) should be(Some(1))
    doMatch(Banana) should be(Some(2))
    doMatch(Cherry) should be(Some(3))
    doMatch(null) should be(None)
  }
}
