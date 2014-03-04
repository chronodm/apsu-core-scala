package apsu.demo.model.ships.systems

import apsu.core.System
import apsu.core.world.{Add, Remove, Update, World}
import apsu.demo.model.ships.components.MainThruster
import apsu.demo.model.equipment.components.EquippedBy
import apsu.demo.controller.components.Activation
import apsu.demo.model.physics.components.{Orientation, Velocity, Spin}

class Accelerate extends System {
  override def invoke(w: World): Seq[Update] = {
    (for ((thrusterEntity, (thruster, equippedBy, activation)) <- w.find[MainThruster, EquippedBy, Activation]) yield {
      val theta = w.get[Orientation](equippedBy.owner) match {
        case Some(o) => o.alpha
        case _ => 0
      }
      Seq(
        Remove(thrusterEntity, activation),
        Add(equippedBy.owner, Velocity.fromPolar(theta, thruster.deltaV))
      )
    }).flatten
  }
}
