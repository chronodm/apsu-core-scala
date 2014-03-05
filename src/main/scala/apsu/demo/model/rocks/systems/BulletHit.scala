package apsu.demo.model.rocks.systems

import apsu.core.{Entity, System}
import apsu.core.world.{Add, Remove, Set, Update, World}
import apsu.demo.model.physics.components.Collision
import apsu.demo.model.rocks.components.Rock
import apsu.demo.model.ships.components.Bullet
import apsu.demo.model.shared.Damage

class BulletHit extends System {
  override def invoke(w: World): Seq[Update] = {
    // TODO something more efficient
    val rockCollisions = for ((e, (r, c)) <- w.find[Rock, Collision]()) yield (c, e)
    val bulletCollisions = (for ((e, (b, c)) <- w.find[Bullet, Collision]()) yield (c, (e, b))).toMap

    (for ((collision, rockEntity) <- rockCollisions) yield {
      bulletCollisions.get(collision) match {
        case Some((bulletEntity, bullet)) => Seq(
          Remove(rockEntity, collision),
          Set(rockEntity, Damage()),

          Remove(bulletEntity, collision),
          Remove(bulletEntity, bullet)
        )
        case _ => Nil
      }
    }).flatten
  }
}
