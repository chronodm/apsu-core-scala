package apsu.demo.model.system

import apsu.core.System
import apsu.core.world.{Add, Update, World}
import apsu.demo.model.component.command.TurnCommand
import apsu.demo.model.component.Spin

class Turn extends System {
  override def invoke(w: World): Seq[Update] = {
    for ((e, t) <- w.find[TurnCommand]) yield {
      Add(e, Spin(t.radians))
    }
  }
}
