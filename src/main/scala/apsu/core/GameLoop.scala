package apsu.core

import apsu.core.world.{Update, World}

trait GameLoop extends System {

  // TODO implement system preconditions/postconditions & sorting, cf. http://t-machine.org/index.php/2009/10/26/entity-systems-are-the-future-of-mmos-part-5/comment-page-3/#comment-97097
  def systems: Seq[System]

  override def invoke(w: World): Seq[Update] = {
    for {
      system <- systems
      update <- system.invoke(w)
    } {
      update(w)
    }
    Nil
  }
}

