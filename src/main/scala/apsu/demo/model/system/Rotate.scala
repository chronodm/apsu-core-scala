package apsu.demo.model.system

import apsu.core.System
import apsu.core.world.{Set, Update, World}
import apsu.demo.model.component.{Spin, Orientation}

class Rotate extends System {
  override def invoke(w: World): Seq[Update] = {
    for ((e, (o, w)) <- w.find[Orientation, Spin]()) yield {
      Set[Orientation](e, o + w)
    }
  }
}