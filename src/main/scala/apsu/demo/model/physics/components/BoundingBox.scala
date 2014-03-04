package apsu.demo.model.physics.components

import math.abs

case class BoundingBox(x: Double, y: Double, w: Double, h: Double) {
  def intersects(other: BoundingBox): Boolean = {
    (abs(this.x - other.x) * 2 < (this.w + other.w)) &&
      (abs(this.y - other.y) * 2 < (this.h + other.h))
  }
}
