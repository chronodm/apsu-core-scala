package apsu.core.world

import apsu.core.Entity

/**
 * Superclass of all operations that can modify the state of a World
 */
sealed trait Update extends ((World) => Unit) {
  def entities: Seq[Entity]
  def components: Seq[_]
}

/**
 * Sets the specified component as the only component of that type
 * for the specified entity
 */
case class Set[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.set(e, c)
  }

  def entities() = Seq(e)
  def components() = Seq(c)
}

/**
 * Adds the specified component to the specified entity
 */
case class Add[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.add(e, c)
  }

  def entities() = Seq(e)
  def components() = Seq(c)
}

/**
 * Removes the specified component from the specified entity
 */
case class Remove[C1](e: Entity, c: C1) extends Update {
  override def apply(w: World): Unit = {
    w.remove(e, c)
  }

  def entities() = Seq(e)
  def components() = Seq(c)
}

/**
 * Removes all components from the specified entity
 */
case class RemoveAll(e: Entity) extends Update {
  override def apply(w: World): Unit = {
    w.removeAll(e)
  }

  def entities() = Seq(e)
  def components() = Nil
}
