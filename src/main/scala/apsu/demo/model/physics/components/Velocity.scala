package apsu.demo.model.physics.components

case class Velocity private(deltaX: Double, deltaY: Double)

object Velocity {
  def fromPolar(theta: Double, r: Double) = {
    val deltaX = r * Math.cos(theta)
    val deltaY = r * Math.sin(theta)
    Velocity(deltaX, deltaY)
  }

  def fromCartesian(x: Double, y: Double) = {
    Velocity(x, y)
  }
}