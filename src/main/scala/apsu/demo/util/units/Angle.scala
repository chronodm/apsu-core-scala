package apsu.demo.util.units

case class Angle(rads: Float) {
  lazy val cos = Math.cos(rads)
  lazy val sin = Math.sin(rads)
}
