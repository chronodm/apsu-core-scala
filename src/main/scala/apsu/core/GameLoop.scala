package apsu.core

import apsu.core.world.{Update, World}

trait GameLoop extends System {

  def systems: Seq[System]

  override def invoke(w: World): Seq[Update] = {
    for {
      system <- systems
      update <- system.invoke(w)
    } {
      update(w)
    }
    Nil
  }
}

