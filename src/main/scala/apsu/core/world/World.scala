package apsu.core.world

import scala.reflect.runtime.{universe => ru}
import apsu.core.Entity

trait World {

  // ---------------------------------------------
  // Abstracts

  def find[C1]()
      (implicit t1: ru.TypeTag[C1])
  :Seq[(Entity, C1)]

  def find[C1, C2]()
      (implicit t1: ru.TypeTag[C1], t2: ru.TypeTag[C2])
  :Seq[(Entity, (C1, C2))]

  // ---------------------------------------------
  // Private updaters

  private[world] def set[C1](e: Entity, c: C1)

}


