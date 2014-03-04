package apsu.demo.model.physics.components

final class Collision {
  // not a case class because we want referential equality, not value equality
}

object Collision {
  def apply(): Collision = new Collision()
}