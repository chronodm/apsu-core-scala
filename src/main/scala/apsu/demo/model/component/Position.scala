package apsu.demo.model.component

case class Position(x: Double, y: Double) {
  def +(v: Velocity) = {
    Position(x + v.deltaX, y + v.deltaY)
  }
}

object Position {
  val Origin = Position(0, 0)
}