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
   * @param deltaMicros The time delta of the current tick,
   *              in microseconds
   */
  def processTick(deltaMicros: Long)
}

object System {
  /**
   * Utility value for converting deltas (in microseconds)
   * to seconds, using multiplication rather than division
   * for efficiency
   */
  final val secondsPerMicro = 1e-6f
}
