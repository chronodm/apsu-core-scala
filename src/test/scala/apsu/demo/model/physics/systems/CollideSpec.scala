package apsu.demo.model.physics.systems

import org.mockito.Mockito._
import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.mock.MockitoSugar
import apsu.core.Entity
import apsu.demo.model.physics.components.{Collision, BoundingBox}
import apsu.core.world.{Update, Add, World}
import scala.reflect.runtime.{universe => ru}

class CollideSpec extends FlatSpec with Matchers with MockitoSugar {

  "invoke" should "create collisions for colliding entities" in {
    implicit val bbt = ru.typeTag[BoundingBox]

    val bb1 = BoundingBox(-2, 2, 4, 4)
    val bb2 = BoundingBox(-3, 3, 2, 2)
    val bb3 = BoundingBox(1, -1, 2, 2)

    bb1.intersects(bb2) should be(true) // just to be sure
    bb1.intersects(bb3) should be(true) // just to be sure
    bb2.intersects(bb3) should be(false) // just to be sure

    val e1 = Entity()
    val e2 = Entity()
    val e3 = Entity()

    val world = mock[World]
    when(world.find[BoundingBox]()).thenReturn(Seq(
      (e1, bb1),
      (e2, bb2),
      (e3, bb3)
    ))

    val collide = new Collide()
    val updates = collide.invoke(world)
    updates should have size (4)
    val collisions: Map[Collision, Seq[Entity]] = updates.groupBy(u => u.components.head).map({
      case (c: Collision, s: Seq[Update]) => (c, s.flatMap({
        case a: Add[_] => a.entities
      }))
    })
    collisions should have size(2)
    // TODO more assertions
  }

  "invoke" should "create collisions for all colliding entities" in {
    implicit val bbt = ru.typeTag[BoundingBox]

    val bb1 = BoundingBox(-2, 2, 4, 4)
    val bb2 = BoundingBox(-3, 3, 2, 2)
    val bb3 = BoundingBox(1, -1, 2, 2)

    bb1.intersects(bb2) should be(true) // just to be sure
    bb1.intersects(bb3) should be(true) // just to be sure
    bb2.intersects(bb3) should be(false) // just to be sure

    val e1 = Entity()
    val e2 = Entity()
    val e3 = Entity()

    val world = mock[World]
    when(world.find[BoundingBox]()).thenReturn(Seq(
      (e1, bb1),
      (e2, bb2),
      (e3, bb3)
    ))

    val collide = new Collide()
    val updates = collide.invoke(world)

    /*
     TODO What we want this to look like:

     invoke should add a collision for e1 and e2
     invoke should add a collision for e1 and e3
     invoke should not add a collision for e2 and e3
     */


    updates should have size 4

    var results = Map[Entity, Set[Collision]]()
    for (u <- updates) {
      u match {
        case Add(e, c) if c.isInstanceOf[Collision] => {
          results += (e -> (results.getOrElse(e, Set[Collision]()) ++ Set(c.asInstanceOf[Collision])))
        }
      }
    }

    val r1 = results(e1)
    val r2 = results(e2)
    val r3 = results(e3)

    r1 should have size 2
    r2 should have size 1
    r3 should have size 1

    r1.contains(r2.head) should be(true)
    r1.contains(r3.head) should be(true)

    r2.intersect(r3) shouldBe empty
  }
}
