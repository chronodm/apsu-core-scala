package apsu.core

/**
 * System
 *
 * @author david
 */
trait System {
  /**
   * Human-readable name, primarily for debugging
   * @return The human-readable name of this system
   */
  def nickname: String

  /**
   * Process one game tick
   * @param delta The time delta of the current tick,
   *              in microseconds
   */
  def processTick(delta: Long)
}
