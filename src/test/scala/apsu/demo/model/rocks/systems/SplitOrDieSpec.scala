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

    /*
     TODO What that we want this to look like

     invoke should remove all for e1
     */
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

    /*
     TODO What that we want this to look like

     invoke should return a Map[Entity, Seq[Update]]

     invoke should set rock(medium) for two new entities
     invoke should set the position for (first new entity) to (position)
     invoke should set the position for (second new entity) to (position)
     invoke should set the position for (first new entity) to (first velocity)
     invoke should set the position for (second new entity) to (second velocity)
     */

  }

}
