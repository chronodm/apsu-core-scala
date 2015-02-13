package apsu.core

import org.scalatest.mock.MockitoSugar
import org.scalatest.{fixture, Matchers}
import org.mockito.Mockito._

import scala.collection.mutable

/**
 * MapEntityManagerSpec
 *
 * @author david
 */
trait EntityManagerSpec[T <: EntityManager] extends fixture.FlatSpec with Matchers with MockitoSugar {

  // ------------------------------------------------------------
  // Fixture

  def createManager: T

  type FixtureParam = T

  def withFixture(test: OneArgTest) = withFixture(test.toNoArgTest(createManager))

  case class SomeComponent(id: Int)

  case class OtherComponent(id: Int)

  // ------------------------------------------------------------
  // newEntity()

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
    evaluating {mgr.newEntity("foo")} should produce[IllegalArgumentException]
    mgr.getNickname(e).get should be("foo")
  }

  // ------------------------------------------------------------
  // get() / set() / has() / remove()

  "set()/get()" should "set/get a component" in { mgr =>
    val e = mgr.newEntity()
    val c = SomeComponent(0)

    mgr.set(e, c)
    mgr.get[SomeComponent](e) should be(Some(c))
  }

  "set()" should "replace existing components" in { mgr =>
    val e = mgr.newEntity()
    val c0 = SomeComponent(0)
    val c1 = SomeComponent(1)

    mgr.set(e, c0)
    mgr.set(e, c1)
    mgr.get[SomeComponent](e) should be(Some(c1))
  }

  it should "support components of multiple types" in { mgr =>
    val e = mgr.newEntity()
    val c0 = SomeComponent(0)
    val c1 = OtherComponent(1)
    mgr.set(e, c0)
    mgr.set(e, c1)
    mgr.get[SomeComponent](e) should be(Some(c0))
    mgr.get[OtherComponent](e) should be(Some(c1))
  }

  it should "disallow setting an entity as a component" in { mgr =>
    val e = mgr.newEntity()
    val e1 = Entity()
    evaluating({
      mgr.set(e, e1)
    }) should produce[IllegalArgumentException]
    evaluating({
      mgr.set(e1, e)
    }) should produce[IllegalArgumentException]
  }

  "get()" should "return None for unset components" in { mgr =>
    val e = mgr.newEntity()
    mgr.get[SomeComponent](e) should be(None)
  }

  "has()" should "return true for set components" in { mgr =>
    val e = mgr.newEntity()
    val c = SomeComponent(0)

    mgr.set(e, c)
    mgr.has[SomeComponent](e) should be(true)
  }

  it should "return false for unset components" in { mgr =>
    val e = mgr.newEntity()
    mgr.has[SomeComponent](e) should be(false)
  }

  "remove()" should "return previous value for set component" in { mgr =>
    val e = mgr.newEntity()
    val c = SomeComponent(0)

    mgr.set(e, c)
    mgr.remove[SomeComponent](e) should be(Some(c))
  }

  it should "return None for unset components" in { mgr =>
    val e = mgr.newEntity()
    mgr.remove[SomeComponent](e) should be(None)
  }

  // ------------------------------------------------------------
  // delete()

  "delete()" should "delete all components" in { mgr =>
    val e = mgr.newEntity()
    val c0 = SomeComponent(0)
    val c1 = OtherComponent(1)

    mgr.set(e, c0)
    mgr.set(e, c1)

    mgr.delete(e)

    mgr.has[SomeComponent](e) should be(false)
    mgr.has[OtherComponent](e) should be(false)

    mgr.all[SomeComponent] should be(empty)
    mgr.all[OtherComponent] should be(empty)
  }

  it should "clear nicknames" in { mgr =>
    val nn = "nickname"
    val e = mgr.newEntity(nn)
    mgr.delete(e)
    mgr.getNickname(e) should be(None)
  }

  // ------------------------------------------------------------
  // all()

  "all()" should "return all and only entities with specified component type" in { mgr =>
    val e0 = mgr.newEntity()
    val e1 = mgr.newEntity()
    val e2 = mgr.newEntity()
    val sc0 = SomeComponent(0)
    val sc1 = SomeComponent(1)
    val oc2 = OtherComponent(2)

    mgr.set(e0, sc0)
    mgr.set(e1, sc1)
    mgr.set(e2, oc2)

    mgr.all[SomeComponent].toStream should contain only((e0, sc0), (e1, sc1))
    mgr.all[OtherComponent].toStream should contain only ((e2, oc2))
  }

  // ------------------------------------------------------------
  // forAll()

  "forAll()" should "return result for all and only entities with specified component type" in { mgr =>
    val e0 = mgr.newEntity()
    val e1 = mgr.newEntity()
    val e2 = mgr.newEntity()
    val sc0 = SomeComponent(0)
    val sc1 = SomeComponent(1)
    val oc2 = OtherComponent(2)

    mgr.set(e0, sc0)
    mgr.set(e1, sc1)
    mgr.set(e2, oc2)

    def f0(e: Entity, c: SomeComponent) = {
      s"$e $c"
    }

    def f1(e: Entity, c: OtherComponent) = {
      s"$e $c"
    }

    mgr.forAll[SomeComponent, String](f0).toStream should contain only(s"$e0 $sc0", s"$e1 $sc1")
    mgr.forAll[OtherComponent, String](f1).toStream should contain only s"$e2 $oc2"
  }

  "forAll()" should "execute on all and only entities with specified component type" in { mgr =>
    val e0 = mgr.newEntity()
    val e1 = mgr.newEntity()
    val e2 = mgr.newEntity()
    val sc0 = SomeComponent(0)
    val sc1 = SomeComponent(1)
    val oc2 = OtherComponent(2)

    mgr.set(e0, sc0)
    mgr.set(e1, sc1)
    mgr.set(e2, oc2)

    val f0: (Entity, SomeComponent) => Unit = mock[(Entity, SomeComponent) => Unit]
    val f1: (Entity, OtherComponent) => Unit = mock[(Entity, OtherComponent) => Unit]

    mgr.forAll[SomeComponent](f0)
    mgr.forAll[OtherComponent](f1)

    verify(f0).apply(e0, sc0)
    verify(f0).apply(e1, sc1)
    verifyNoMoreInteractions(f0)

    verify(f1).apply(e2, oc2)
    verifyNoMoreInteractions(f1)
  }

  // ------------------------------------------------------------
  // allComponents

  "allComponents" should "return all components for an entity" in { mgr =>
    val e = mgr.newEntity()
    val c0 = SomeComponent(0)
    val c1 = OtherComponent(0)

    mgr.set(e, c0)
    mgr.set(e, c1)

    mgr.allComponents(e).toStream should contain only(c0, c1)
  }

  it should "return an empty iterator for entities with no components" in { mgr =>
    val e = mgr.newEntity()
    mgr.allComponents(e).toStream shouldBe empty
  }

  // ------------------------------------------------------------
  // setNickname() / getNickname() / clearNickname()

  "setNickname()/getNickname()" should "set/get a nickname" in { mgr =>
    val e = mgr.newEntity()
    mgr.setNickname(e, "foo")
    mgr.getNickname(e) should be(Some("foo"))
  }

  "clearNickname" should "remove a nickname" in { mgr =>
    val e = mgr.newEntity("foo")
    mgr.clearNickname(e)
    mgr.getNickname(e) should be(None)
  }
}
