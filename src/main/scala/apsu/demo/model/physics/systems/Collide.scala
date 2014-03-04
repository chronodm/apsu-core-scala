package apsu.demo.model.physics.systems

import apsu.core.{Entity, System}
import apsu.core.world.{Add, Update, World}
import apsu.demo.model.physics.components.{Collision, BoundingBox}

class Collide extends System {

  // TODO something cleaner & more efficient
  override def invoke(w: World): Seq[Update] = {
    val allBoxes: Seq[(Entity, BoundingBox)] = w.find[BoundingBox]()
    var updates = List[Update]()
    for {
      (e0, b0) <- allBoxes
      (e1, b1) <- allBoxes
    } {
      if ((e0 != e1) && b0.intersects(b1)) {
        val c = Collision()
        updates ::= Add[Collision](e0, c)
        updates ::= Add[Collision](e1, c)
      }
    }
    updates
  }
}
