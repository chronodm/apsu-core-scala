package apsu.demo.model.component

final class Collision {
  // not a case class because we want referential equality, not value equality
}

object Collision {
  def apply(): Collision = new Collision()
}