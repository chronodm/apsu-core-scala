package apsu.core.world

import scala.reflect.runtime.{universe => ru}
import apsu.core.Entity

trait World {

  // ---------------------------------------------
  // Entity/Component finders

  def find[C1]()
      (implicit t1: ru.TypeTag[C1])
  :Seq[(Entity, C1)]

  def find[C1, C2]()
      (implicit t1: ru.TypeTag[C1], t2: ru.TypeTag[C2])
  :Seq[(Entity, (C1, C2))]

  def find[C1, C2, C3]()
      (implicit t1: ru.TypeTag[C1], t2: ru.TypeTag[C2], t3: ru.TypeTag[C3])
  :Seq[(Entity, (C1, C2, C3))]

  // ---------------------------------------------
  // Component accessors for entities

  def get[C1](e: Entity)
      (implicit t1: ru.TypeTag[C1])
  :Option[C1]

  def get[C1, C2](e: Entity)
      (implicit t1: ru.TypeTag[C1], t2: ru.TypeTag[C2])
  :Option[(C1, C2)]

  // ---------------------------------------------
  // Private updaters

  private[world] def set[C1](e: Entity, c: C1)

  private[world] def add[C1](e: Entity, c: C1)

  private[world] def remove[C1](e: Entity, c: C1)
}


