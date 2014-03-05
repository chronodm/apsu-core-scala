package apsu.demo.model.rocks.systems

import apsu.core.System
import apsu.core.world.{Remove, Update, World}
import apsu.demo.model.rocks.components.Rock
import apsu.demo.model.shared.Damage

class SplitOrDie extends System {
  override def invoke(w: World): Seq[Update] = {
    (for ((rockEntity, (rock, damage)) <- w.find[Rock, Damage]) yield {
      Seq(
        Remove(rockEntity, damage)
      )
    }).flatten
  }
}
