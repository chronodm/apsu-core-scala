package apsu.demo.model.combat.systems

import apsu.core.{Entity, System}
import apsu.core.world.{Update, World}
import apsu.core.world.Remove
import apsu.core.world.Add
import apsu.demo.model.physics.components.{Orientation, Position}
import apsu.demo.controller.components.FireCommand
import apsu.demo.model.combat.components.{Weapon, Bullet}

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

