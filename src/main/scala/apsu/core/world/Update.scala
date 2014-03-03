package apsu.core.world

import apsu.core.Entity

sealed trait Update extends ((World) => Unit) {
}

case class Set[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.set(e, c)
  }
}
