package apsu.demo.model.physics.components

import Math.{PI => pi}

case class Orientation(alpha: Double) {

  import Orientation.twoPi

  def +(w: Spin) = {
    // TODO unit-test this
    Orientation({
      val alpha1 = (alpha + w.deltaA) % twoPi
      if (alpha1 >= 0) alpha1 else alpha1 + twoPi
    })
  }
}

object Orientation {
  private val twoPi = pi * 2
  val Zero = Orientation(0)
}