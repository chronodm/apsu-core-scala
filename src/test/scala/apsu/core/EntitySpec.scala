package apsu.core

import org.scalatest.{FlatSpec, Matchers}
import java.util.UUID
import java.util
import java.lang.reflect.Modifier

class EntitySpec extends FlatSpec with Matchers {

  classOf[Entity].getSimpleName should "generate entities with unique UUIDs" in {
    val count: Int = 100
    val uuids: Set[UUID] = (for (i <- 0 until count) yield Entity().id).toSet
    uuids.size should be(count)
  }

  it should "be final" in {
    Modifier.isFinal(classOf[Entity].getModifiers) should be(true)
  }

  it should "take < 5us to create an Entity" in {
    val runs: Int = 11
    val count: Int = 1000

    val times = new Array[Long](runs)
    for (i <- 0 until runs) {
      val start = java.lang.System.nanoTime()
      for (j <- 0 until count) {
        Entity()
      }
      times(i) = java.lang.System.nanoTime() - start
    }
    util.Arrays.sort(times)
    val median = times(runs / 2) / count.asInstanceOf[Double]
    median should be < 5000.0
  }
}
