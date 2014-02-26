package apsu.core

trait Components {

  def get[C](e: Entity): Option[C]

  def get[C1, C2](e: Entity): (Option[C1], Option[C2])

  def set[C](e: Entity, c: C)

  def remove[C](e: Entity, c: C)

}
