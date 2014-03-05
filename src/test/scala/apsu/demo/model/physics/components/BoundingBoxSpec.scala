package apsu.demo.model.physics.components

import org.scalatest.{FlatSpec, Matchers}

class BoundingBoxSpec extends FlatSpec with Matchers {

  "intersects" should "recognize equal boxes as intersecting" in {
    val bb1 = BoundingBox(-2, 2, 4, 4)
    bb1.intersects(bb1) should be(true)

    val bb2 = BoundingBox(-2, 2, 4, 4)
    bb1.intersects(bb2) should be(true)
    bb2.intersects(bb1) should be(true)
  }

  "intersects" should "detect intersecting boxes" in {
    val bb1 = BoundingBox(-2, 2, 4, 4)
    val bb2 = BoundingBox(1, -1, 2, 2)

    bb1.intersects(bb2) should be(true)
    bb2.intersects(bb1) should be(true)
  }

  "intersects" should "detect non-intersecting boxes" in {
    val bb1 = BoundingBox(-2, 2, 1, 1)
    val bb2 = BoundingBox(1, -1, 1, 1)

    bb1.intersects(bb2) should be(false)
    bb2.intersects(bb1) should be(false)
  }
}
