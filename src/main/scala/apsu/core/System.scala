package apsu.core

import apsu.core.world.{Update, World}

trait System {
  def invoke(w: World): Seq[Update]
}
