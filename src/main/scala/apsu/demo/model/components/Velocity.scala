package apsu.demo.model.components

import apsu.demo.util.units.{Angle, Distance}

case class Velocity(theta: Angle, r: Distance) {
  lazy val deltaX = (r.v * theta.cos).asInstanceOf[Float]
  lazy val deltaY = (r.v * theta.sin).asInstanceOf[Float]
}