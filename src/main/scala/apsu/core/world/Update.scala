package apsu.core.world

import apsu.core.Entity

sealed trait Update extends ((World) => Unit) {
}

case class Set[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.set(e, c)
  }
}

case class Add[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.add(e, c)
  }
}

case class Remove[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.remove(e, c)
  }
}

