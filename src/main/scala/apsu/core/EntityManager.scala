package apsu.core

import scala.reflect.runtime.{universe => ru}

/**
 * EntityManager
 *
 * @author david
 */
trait EntityManager {

  // ------------------------------------------------------------
  // Methods on single entities

  /**
   * Gets the component of the specified type for the
   * specified entity.
   *
   * @tparam C1 The component type
   * @param e The entity
   * @return An [[Option]] containing the component,
   *         if present, or [[None]] if not
   */
  def get[C1](e: Entity)(implicit t: ru.TypeTag[C1]): Option[C1]

  /**
   * Sets the component of the specified type for the
   * specified entity.
   * @param e The entity to set the component for.
   * @param c The component to set.
   * @tparam C1 The component type
   */
  def set[C1](e: Entity, c: C1)(implicit t: ru.TypeTag[C1])

  /**
   * Removes the component of the specified type from
   * the specified entity.
   *
   * @tparam C1 The component type
   * @param e The entity
   * @return An [Option] containing the component, or [None]
   *         if there was no such component
   */
  def remove[C1](e: Entity)(implicit t: ru.TypeTag[C1]): Option[C1]

  /**
   * Reports whether the specified entity has a component
   * of the specified type.
   *
   * @tparam C1 The component type
   * @param e The entity
   * @return True if the entity has the component, false
   * otherwise
   */
  @inline final def has[C1](e: Entity)(implicit t: ru.TypeTag[C1]): Boolean = get[C1](e).isDefined

  // ------------------------------------------------------------
  // Cross-entity methods

  /**
   * Gets all entity-component pairs for the specified component
   * type.
   *
   * @tparam C1 The component type
   * @return All entity-component pairs for the specified component
   *         type, or an empty [Iterable] if no entities with the
   *         specified component type exist.
   */
  def all[C1](implicit t: ru.TypeTag[C1]): Iterable[(Entity, C1)]

  // ------------------------------------------------------------
  // Convenience methods

  /**
   * Gets all components for the specified entity. Mostly
   * useful only for debugging.
   * @param e The entity
   * @return All components for the specified entity, or
   *         an empty [Iterable] if the entity has no
   *         components
   */
  def allComponents(e: Entity): Iterable[Any]

  /**
   * Gets the nickname of the specified entity, if any
   * @param e The entity
   * @return An [Option] containing the nickname for
   *         the entity, if present, or [None] if the entity
   *         did not have a nickname
   */
  def getNickname(e: Entity): Option[String]

  /**
   * Sets a nickname for the specified entity.
   * @param e The entity
   * @param nickname The nickname of the entity
   * @return An [Option] containing the old nickname for
   *         the entity, if present, or [None] if the entity
   *         did not have a nickname
   */
  def setNickname(e: Entity, nickname: String): Option[String]

  /**
   * Creates a new entity
   * @return The new entity
   */
  @inline final def newEntity(): Entity = Entity()

  /**
   * Creates a new entity with the specified nickname
   * @param nickname The nickname of the entity
   */
  @inline final def newEntity(nickname: String): Entity = {
    val e = newEntity()
    setNickname(e, nickname)
    e
  }
}
