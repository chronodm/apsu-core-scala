package apsu.core.world

import scala.reflect.runtime.{universe => ru}
import apsu.core.Entity

trait World {

  // ---------------------------------------------
  // Entity/Component finders

  /**
   * Finds all entities with components of the specified type
   */
  def find[C1]()
      (implicit t1: ru.TypeTag[C1])
  :Seq[(Entity, C1)]

  /**
   * Finds all entities with components of both specified types
   */
  def find[C1, C2]()
      (implicit t1: ru.TypeTag[C1], t2: ru.TypeTag[C2])
  :Seq[(Entity, (C1, C2))]

  /**
   * Finds all entities with components of all specified types
   */
  def find[C1, C2, C3]()
      (implicit t1: ru.TypeTag[C1], t2: ru.TypeTag[C2], t3: ru.TypeTag[C3])
  :Seq[(Entity, (C1, C2, C3))]

  // ---------------------------------------------
  // Component accessors for entities

  /**
   * Gets the component of the specified type for the specified entity
   * @return the component, or None if the entity doesn't have one
   */
  def get[C1](e: Entity)
      (implicit t1: ru.TypeTag[C1])
  :Option[C1]

  /**
   * Gets the components of the specified type for the specified entity
   * @return the components, or None if the entity doesn't have both
   */
  def get[C1, C2](e: Entity)
      (implicit t1: ru.TypeTag[C1], t2: ru.TypeTag[C2])
  :(Option[C1], Option[C2])

  // ---------------------------------------------
  // Private updaters

  /**
   * Sets the specified component as the only component of that type
   * for the specified entity
   */
  private[world] def set[C1](e: Entity, c: C1)

  /**
   * Adds the specified component to the specified entity
   */
  private[world] def add[C1](e: Entity, c: C1)

  /**
   * Removes the specified component from the specified entity
   */
  private[world] def remove[C1](e: Entity, c: C1)
}


