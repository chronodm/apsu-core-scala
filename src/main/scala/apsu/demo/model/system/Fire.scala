package apsu.demo.model.system

import apsu.core.{Entity, System}
import apsu.core.world.{Add, Remove, Update, World}
import apsu.demo.model.component.command.FireCommand
import apsu.demo.model.component.{Velocity, Bullet, Orientation, Position}

class Fire extends System {
  override def invoke(w: World): Seq[Update] = {
    (for ((e0, (weapon, fireCmd)) <- w.find[Weapon, FireCommand]) yield {
      val e1 = Entity()
      val bulletComponent = Bullet()
      val position = w.get[Position](e0).getOrElse(Position.Origin)
      val orientation = w.get[Orientation](e0).getOrElse(Orientation.Zero)
      Seq(
        Remove(e0, fireCmd),
        Add(e1, bulletComponent),
        Add(e1, position),
        Add(e1, orientation),
        Add(e1, weapon.bulletVelocity)
      )
    }).flatten
  }
}

case class Weapon(bulletVelocity: Velocity)