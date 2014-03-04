package apsu.demo.model.ships.systems

import apsu.core.{Entity, System}
import apsu.core.world.{Update, World}
import apsu.core.world.Remove
import apsu.core.world.Add
import apsu.demo.model.physics.components.{Orientation, Position}
import apsu.demo.controller.components.Activation
import apsu.demo.model.ships.components.{Weapon, Bullet}
import apsu.demo.model.equipment.components.EquippedBy

class Fire extends System {
  override def invoke(w: World): Seq[Update] = {
    (for ((weaponEntity, (weapon, activation)) <- w.find[Weapon, Activation]) yield {

      // TODO if the weapon's not equipped, this creates a bullet in the middle of nowhere -- is that what we want?
      val (position, orientation) = w.get[EquippedBy](weaponEntity) match {
        case Some(equippedBy) =>
          w.get[Position, Orientation](equippedBy.owner)
        case _ => (None, None)
      }

      val bulletEntity = Entity()
      Seq(
        Remove(weaponEntity, activation),

        // TODO templatize me
        Add(bulletEntity, Bullet()),
        Add(bulletEntity, position.getOrElse(Position.Origin)),
        Add(bulletEntity, orientation.getOrElse(Orientation.Zero)),
        Add(bulletEntity, weapon.bulletVelocity)
      )
    }).flatten
  }
}

