package apsu.core

import scala.reflect.runtime.{universe => ru}

import scala.collection.mutable

/**
 * A basic [[EntityManager]] implementation using [[mutable.OpenHashMap]]
 *
 * @author david
 */
class MapEntityManager extends EntityManager {

  // ------------------------------------------------------------
  // Fields

  private val et: ru.TypeTag[Entity] = ru.typeTag[Entity]

  private val components = new mutable.OpenHashMap[ru.TypeTag[_], mutable.OpenHashMap[Entity, _]]()

  private val nicknames = new mutable.OpenHashMap[Entity, String]
  private val nicknamesReverse = new mutable.OpenHashMap[String, Entity]

  // ------------------------------------------------------------
  // Private helpers

  private def mapFor[C1](implicit t: ru.TypeTag[C1]): Option[mutable.OpenHashMap[Entity, C1]] = {
    if (t == et) {
      throw new IllegalArgumentException(s"Entities cannot be components")
    }
    components.get(t).asInstanceOf[Option[mutable.OpenHashMap[Entity, C1]]]
  }

  // ------------------------------------------------------------
  // EntityManager

  override def get[C1](e: Entity)(implicit t: ru.TypeTag[C1]): Option[C1] = {
    mapFor[C1] match {
      case Some(m) => m.get(e)
      case _ => None
    }
  }

  override def set[C1](e: Entity, c: C1)(implicit t: ru.TypeTag[C1]): Unit = {
    val m: mutable.OpenHashMap[Entity, C1] = mapFor[C1] match {
      case Some(m1) => m1
      case _ =>
        val m2 = new mutable.OpenHashMap[Entity, C1]()
        components.put(t, m2)
        m2
    }
    m(e) = c
  }

  override def remove[C1](e: Entity)(implicit t: ru.TypeTag[C1]): Option[C1] = {
    mapFor[C1] match {
      case Some(m) => m.remove(e)
      case _ => None
    }
  }


  override def delete(e: Entity): Unit = {
    components.values.foreach(_.remove(e))
    clearNickname(e)
  }

  override def all[C1](implicit t: ru.TypeTag[C1]): Iterable[(Entity, C1)] = {
    mapFor[C1] match {
      case Some(m) => m
      case _ => Iterable.empty[(Entity, C1)]
    }
  }

  override def allComponents(e: Entity): Iterable[Any] = {
    components.values.flatMap((m) => m.get(e))
  }

  override def getNickname(e: Entity): Option[String] = {
    nicknames.get(e)
  }

  override def setNickname(e: Entity, nickname: String): Option[String] = {
    nicknamesReverse.get(nickname) match {
      case Some(other) => throw new IllegalArgumentException(s"Nickname '$nickname' already in use by entity ${other.id}")
      case _ =>
        val old = nicknames.get(e)
        nicknames(e) = nickname
        nicknamesReverse(nickname) = e
        old
    }
  }

  override def clearNickname(e: Entity): Option[String] = {
    nicknames.remove(e) match {
      case Some(nn) =>
        nicknamesReverse.remove(nn)
        Some(nn)
      case _ => None
    }
  }
}
