package apsu.core

import org.scalatest.{fixture, Matchers}

import scala.collection.mutable

/**
 * MapEntityManagerSpec
 *
 * @author david
 */
trait EntityManagerSpec[T <: EntityManager] extends fixture.FlatSpec with Matchers {

  def createManager: T

  type FixtureParam = T

  def withFixture(test: OneArgTest) = withFixture(test.toNoArgTest(createManager))

  "newEntity()" should "construct an entity" in { mgr =>
    val e = mgr.newEntity()
    e should not be null
  }

  it should "construct a new entity each time" in { mgr =>
    val s = new mutable.HashSet[Entity]
    for (i <- 0 until 10) {
      val e = mgr.newEntity()
      s should not contain e
      s.add(e)
    }
  }

  "newEntity(nickname)" should "construct an entity and set its nickname" in { mgr =>
    val e = mgr.newEntity("foo")
    e should not be null
    mgr.getNickname(e).get should be("foo")
  }

  it should "not allow the same nickname for multiple entities" in { mgr =>
    val e = mgr.newEntity("foo")
    evaluating { mgr.newEntity("foo") } should produce[IllegalArgumentException]
    mgr.getNickname(e).get should be("foo")
  }
}
