package apsu.demo.model.component

case class Velocity(theta: Double, r: Double) {
  lazy val deltaX = r * Math.cos(theta)
  lazy val deltaY = r * Math.sin(theta)
}