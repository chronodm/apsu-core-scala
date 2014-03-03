package apsu.old

import scala.reflect.runtime.{universe => ru}
import scala.Some
import apsu.core.Entity

class EntityManagerImpl extends EntityManager {
  import EntityManager._

  // TODO there must be a better way to do type-safe heterogeneous containers in Scala, or at least hide the mess
  private var registry: Map[ru.TypeTag[_], Map[Entity, _]] = Map[ru.TypeTag[_], Map[Entity, _]]()

  override def update[C1, C2](process: Process2[C1, C2])
      (implicit ct1: ru.TypeTag[C1], ct2: ru.TypeTag[C2]): Unit = {

    // TODO not only is this slow and un-scala-like it'd be much more concise with flatmap etc.
    val o1 = registry.get(ct1)
    val o2 = registry.get(ct2)
    if (o1.isDefined && o2.isDefined) {
      val m1 = o1.get.asInstanceOf[Map[Entity, C1]]
      val m2 = o2.get.asInstanceOf[Map[Entity, C2]]
      for ((e: Entity, c1: C1) <- m1) {
        m2.get(e) match {
          case Some(c2) =>
            val update = process(c1, c2, e)
            // TODO queue this instead of calling immediately?
            // TODO either way, test it
            update(this)
          case _ =>
        }
      }
    }
  }

  // TODO 'set' is not very functionish
  override def set[C](e: Entity, c: C)(implicit ct: ru.TypeTag[C]): Unit = {
    // TODO this is not thread-safe
    val newM = registry.get(ct) match {
      case Some(m) => m.asInstanceOf[Map[Entity, C]] + (e -> c)
      case _ => Map[Entity, C](e -> c)
    }
    registry += (ct -> newM)
  }

  // TODO 'get' is not very functionish
  def get[C](e: Entity)(implicit ct: ru.TypeTag[C]): Option[C] = {
    registry.get(ct) match {
      case Some(m) => m.asInstanceOf[Map[Entity, C]].get(e)
      case _ => None
    }
  }
}