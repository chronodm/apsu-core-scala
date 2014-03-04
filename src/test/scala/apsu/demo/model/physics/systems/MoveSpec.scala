package apsu.demo.model.physics.systems

import org.mockito.Mockito._
import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.mock.MockitoSugar
import apsu.core.Entity
import apsu.demo.model.physics.components.{Velocity, Position}
import apsu.core.world.{Set, World}
import scala.reflect.runtime.{universe => ru}

class MoveSpec extends FlatSpec with Matchers with MockitoSugar {
  "invoke" should "set new position for all entities with position and velocity" in {
    implicit val pt = ru.typeTag[Position]
    implicit val vt = ru.typeTag[Velocity]

    val a = Entity()
    val b = Entity()

    val world = mock[World]
    when(world.find[Position, Velocity]()).thenReturn(Seq(
      (a, (Position(1, 2), Velocity.fromCartesian(3, 4))),
      (b, (Position(-2, -4), Velocity.fromCartesian(6, 8)))
    ))

    val move = new Move()
    val updates = move.invoke(world)

    updates should have size 2

    updates should contain allOf(
      Set[Position](a, Position(4, 6)),
      Set[Position](b, Position(4, 4))
      )
  }
}
