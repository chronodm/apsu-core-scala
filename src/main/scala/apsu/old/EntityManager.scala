package apsu.old

import scala.reflect.runtime.{universe => ru}

object EntityManager {
  // ------------------------------------------------------------
  // Types

  /**
   * A function that updates the state of the [[EntityManager]]
   */
  // TODO this is a bit monolithic and stupid, and won't cut it once we have multi-entity updates
  // TODO also it doesn't do anything to support (let alone enforce) atomicity
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
