package apsu.core

import scala.reflect.runtime.{universe => ru}

object EntityManager {
  // ------------------------------------------------------------
  // Types

  /**
   * A function that updates the state of the [[EntityManager]]
   */
  // TODO this is a bit monolithic and stupid, and won't cut it once we have multi-entity updates
  type Update = (EntityManager) => Unit

  /**
   * A function that examines the state of two of an entity's
   * components, and produces a corresponding update
   *
   * @tparam C1 The type of the first component
   * @tparam C2 The type of the second component
   */
  // TODO do processes only ever look at one entity at a time?
  type Process2[C1, C2] = (C1, C2, Entity) => Update
}

trait EntityManager {
  import EntityManager._

  // ------------------------------------------------------------
  // Public methods

  def update[C1, C2](process: Process2[C1, C2])
      (implicit ct1: ru.TypeTag[C1], ct2: ru.TypeTag[C2])

  def set[C](e: Entity, c:C)
      (implicit ct: ru.TypeTag[C])
}

class EntityManagerImpl extends EntityManager {
  import EntityManager._

  // TODO there must be a better way to do type-safe heterogeneous containers in Scala, or at least hide the mess
  private var registry: Map[ru.TypeTag[_], Map[Entity, _]] = Map[ru.TypeTag[_], Map[Entity, _]]()

  override def update[C1, C2](process: Process2[C1, C2])
      (implicit ct1: ru.TypeTag[C1], ct2: ru.TypeTag[C2]): Unit = {

    // TODO not only is this slow and un-scala-like it'd be much more concise with flatmap etc.
    val o1 = registry.get(ct1)
    val o2 = registry.get(ct2)
    if (o1.isDefined && o2.isDefined) {
      val m1 = o1.get.asInstanceOf[Map[Entity, C1]]
      val m2 = o2.get.asInstanceOf[Map[Entity, C2]]
      for ((e: Entity, c1: C1) <- m1) {
        m2.get(e) match {
          case Some(c2) =>
            val update = process(c1, c2, e)
            update(this)
          case _ =>
        }
      }
    }
  }

  override def set[C](e: Entity, c: C)(implicit ct: ru.TypeTag[C]): Unit = {
    // TODO this is not thread-safe
    val newM = registry.get(ct) match {
      case Some(m) => m.asInstanceOf[Map[Entity, C]] + (e -> c)
      case _ => Map[Entity, C](e -> c)
    }
    registry += (ct -> newM)
  }

  def get[C](e: Entity)(implicit ct: ru.TypeTag[C]): Option[C] = {
    registry.get(ct) match {
      case Some(m) => m.asInstanceOf[Map[Entity, C]].get(e)
      case _ => None
    }
  }
}

/*
 * TODO: Rewrite entity/system/component relationship
 *
 * System should be a function, not a class.
 *
 * Submitting a system to... something should efficiently find all entities
 * with the necessary components for that system, and run it against them.
 *
 * The state of all entity-component relationships should be explicitly modeled
 * as a monad (whatever the hell that would mean).
 *
 * "Side" effects of systems should be expressed in terms of deltas, not final
 * states, and deltas should be queued up, merged, and applied at the end of
 * the tick, in some fantastically efficient manner.
 *
 * That stipulation might not actually make any sense if not every system is
 * on the same clock. If not every system is on the same clock, transactionality
 * needs rethinking.
 */
