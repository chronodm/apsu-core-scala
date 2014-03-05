package apsu.demo.model.rocks.systems

import apsu.core.{Entity, System}
import apsu.core.world.{RemoveAll, Update, World, Set}
import apsu.demo.model.rocks.components.{Size, Rock}
import apsu.demo.model.shared.Damage
import apsu.demo.model.rocks.components.Size.{Large, Medium, Small}
import apsu.demo.model.physics.components.{Velocity, Position}
import Math.{PI => pi}

class SplitOrDie extends System {
  override def invoke(w: World): Seq[Update] = {
    (for ((rockEntity, (damage, rock, position, velocity)) <- w.find[Damage, Rock, Position, Velocity]) yield {

      val v0 = Velocity.fromPolar(velocity.theta - (pi / 2), velocity.r * 2)
      val v1 = Velocity.fromPolar(velocity.theta + (pi / 2), velocity.r * 2)

      (rock.size match {
        case Small => Nil
        case Medium =>
          createRock(Size.Small, position, v0) ++
            createRock(Size.Small, position, v1)
        case Large =>
          createRock(Size.Medium, position, v0) ++
            createRock(Size.Medium, position, v1)
      }) ++ Seq(
        RemoveAll(rockEntity)
      )
    }).flatten
  }

  private def createRock(size: Size, position: Position, velocity: Velocity) = {
    val rockEntity = Entity()
    Seq(
      Set(rockEntity, Rock(size)),
      Set(rockEntity, position),
      Set(rockEntity, velocity)
    )
  }
}
