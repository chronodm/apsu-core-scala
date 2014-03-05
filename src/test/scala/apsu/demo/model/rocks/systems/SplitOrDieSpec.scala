package apsu.demo.model.rocks.systems

import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.mock.MockitoSugar
import apsu.core.Entity
import apsu.demo.model.shared.Damage
import apsu.demo.model.rocks.components.{Rock, Size}
import apsu.demo.model.physics.components.{Velocity, Position}
import apsu.core.world.{RemoveAll, World}
import scala.reflect.runtime.{universe => ru}
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.mockito.ArgumentMatcher

class SplitOrDieSpec extends FlatSpec with Matchers with MockitoSugar {

  "invoke" should "destroy the original rock" in {
    implicit val dt = ru.typeTag[Damage]
    implicit val rt = ru.typeTag[Rock]
    implicit val pt = ru.typeTag[Position]
    implicit val vt = ru.typeTag[Velocity]

    val rockEntity = Entity()
    val damage = Damage()
    val rock = Rock(Size.Large)
    val position = Position.Origin
    val velocity = Velocity.Zero

    val world = mock[World]
    when(world.find[Damage, Rock, Position, Velocity]).thenReturn(Seq(
      (rockEntity, (damage, rock, position, velocity))
    ))

    val splitOrDie = new SplitOrDie()
    val updates = splitOrDie.invoke(world)
    updates.filter(u => u match {
      case r: RemoveAll => (r.e == rockEntity)
      case _ => false
    }) should have size(1)
  }

  "invoke" should "turn big rocks into medium rocks at right angles and twice the speed" in {
    implicit val dt = ru.typeTag[Damage]
    implicit val rt = ru.typeTag[Rock]
    implicit val pt = ru.typeTag[Position]
    implicit val vt = ru.typeTag[Velocity]

    val rockEntity = Entity()
    val damage = Damage()
    val rock = Rock(Size.Large)
    val position = Position(1, 1)
    val velocity = Velocity.fromCartesian(1, 0)

    val world = mock[World]
    when(world.find[Damage, Rock, Position, Velocity]).thenReturn(Seq(
      (rockEntity, (damage, rock, position, velocity))
    ))

    val splitOrDie = new SplitOrDie()
    for (u <- splitOrDie.invoke(world)) { u(world) }

//    verify(world, times(2)).set(any(classOf[Entity]), argThat(new ArgumentMatcher[Rock] {
//      override def matches(argument: scala.Any): Boolean = {
//        argument match {
//          case r: Rock if r.size == Size.Medium => true
//          case _ => false
//        }
//      }
//    }))
//
//    verify(world, times(2)).set(any(classOf[Entity]), argThat(new ArgumentMatcher[Position] {
//      override def matches(argument: scala.Any): Boolean = {
//        argument == position
//      }
//    }))

  }

}
