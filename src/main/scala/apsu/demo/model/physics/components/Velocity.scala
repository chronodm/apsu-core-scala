package apsu.demo.model.physics.components

import Math.sqrt
import Math.pow
import Math.acos

case class Velocity private(deltaX: Double, deltaY: Double) {
  // TODO test this
  def theta = {
    acos(deltaX / r)
  }

  def r = {
    sqrt(pow(deltaX, 2) + pow(deltaY, 2))
  }
}

object Velocity {
  // TODO test this
  def fromPolar(theta: Double, r: Double) = {
    val deltaX = r * Math.cos(theta)
    val deltaY = r * Math.sin(theta)
    Velocity(deltaX, deltaY)
  }

  def fromCartesian(x: Double, y: Double) = {
    Velocity(x, y)
  }
}