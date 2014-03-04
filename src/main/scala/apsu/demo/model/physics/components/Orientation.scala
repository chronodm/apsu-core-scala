package apsu.demo.model.physics.components

case class Orientation(alpha: Double) {
  def +(w: Spin) = {
    Orientation(alpha + w.deltaA)
  }
}

object Orientation {
  val Zero = Orientation(0)
}