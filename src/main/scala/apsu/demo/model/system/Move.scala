package apsu.demo.model.system

import apsu.core.System
import apsu.demo.model.component.{Orientation, Spin, Position, Velocity}
import apsu.core.world.{Update, World, Set}

class Move extends System {
  override def invoke(w: World): Seq[Update] = {
    for ((e, (p, v)) <- w.find[Position, Velocity]()) yield {
      Set[Position](e, p + v)
    }
  }
}

