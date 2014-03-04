package apsu.demo.model.component

case class Orientation(alpha: Double) {
  def +(w: Spin) = {
    Orientation(alpha + w.deltaA)
  }
}