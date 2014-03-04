package apsu.core.world

import apsu.core.Entity

sealed trait Update extends ((World) => Unit) {
}

/**
 * Sets the specified component as the only component of that type
 * for the specified entity
 */
case class Set[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.set(e, c)
  }
}

/**
 * Adds the specified component to the specified entity
 */
case class Add[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.add(e, c)
  }
}

/**
 * Removes the specified component from the specified entity
 */
case class Remove[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.remove(e, c)
  }
}

