package apsu.demo.model.physics.systems

import apsu.core.System
import apsu.core.world.{Add, Update, World}
import apsu.demo.model.physics.components.Spin
import apsu.demo.controller.components.TurnCommand

class Turn extends System {
  override def invoke(w: World): Seq[Update] = {
    for ((e, t) <- w.find[TurnCommand]) yield {
      Add(e, Spin(t.radians))
    }
  }
}
