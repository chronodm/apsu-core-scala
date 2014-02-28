package apsu.core

trait System {
  def update(entityMgr: EntityManager)
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

