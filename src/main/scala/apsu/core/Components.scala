package apsu.core

import scala.reflect.runtime.{universe => ru}

trait Components {

  //  def update[C](system: (C, Entity) => Unit)(implicit ct: ru.TypeTag[C])

  def update[C1, C2](system: (C1, C2, Entity) => ((Components) => Unit))(
    implicit ct1: ru.TypeTag[C1], ct2: ru.TypeTag[C2])

  def set[C](c: C, e: Entity)(implicit ct: ru.TypeTag[C])

  //  def set[C1, C2](c1: C1, c2: C2, e: Entity)
}

class ComponentsImpl extends Components {

  // TODO there must be a better way to do type-safe heterogeneous containers in Scala, or at least hide the mess
  private var registry: Map[ru.TypeTag[_], Map[Entity, _]] = Map[ru.TypeTag[_], Map[Entity, _]]()

  override def update[C1, C2](system: (C1, C2, Entity) => (Components) => Unit)(
    implicit ct1: ru.TypeTag[C1], ct2: ru.TypeTag[C2]): Unit = {

    // TODO not only is this slow and un-scala-like it'd be much more concise with flatmap etc.
    val o1 = registry.get(ct1)
    val o2 = registry.get(ct2)
    if (o1.isDefined && o2.isDefined) {
      val m1 = o1.get.asInstanceOf[Map[Entity, C1]]
      val m2 = o2.get.asInstanceOf[Map[Entity, C2]]
      for ((e: Entity, c1: C1) <- m1) {
        m2.get(e) match {
          case Some(c2) =>
            val update = system(c1, c2, e)
            update(this)
          case _ =>
        }
      }
    }
  }

  override def set[C](c: C, e: Entity)(implicit ct: ru.TypeTag[C]): Unit = {
    // TODO this is not thread-safe
    val newM = registry.get(ct) match {
      case Some(m) => m.asInstanceOf[Map[Entity, C]] + (e -> c)
      case _ => Map[Entity, C](e -> c)
    }
    registry += (ct -> newM)
  }

  def get[C](e: Entity)(implicit ct: ru.TypeTag[C]): Option[C] = {
    registry.get(ct) match {
      case Some(m) => m.asInstanceOf[Map[Entity, C]].get(e)
      case _ => None
    }
  }
}

