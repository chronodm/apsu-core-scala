package apsu.demo.model.ships.systems

import apsu.core.System
import apsu.core.world.{Remove, Add, Update, World}
import apsu.demo.model.physics.components.Spin
import apsu.demo.controller.components.Activation
import apsu.demo.model.ships.components.RCSThruster
import apsu.demo.model.equipment.components.EquippedBy

class Turn extends System {

  override def invoke(w: World): Seq[Update] = {
    (for ((thrusterEntity, (thruster, equippedBy, activation)) <- w.find[RCSThruster, EquippedBy, Activation]) yield {
      Seq(
        Remove(thrusterEntity, activation),
        Add(equippedBy.owner, Spin(thruster.deltaA))
      )
    }).flatten
  }
}
