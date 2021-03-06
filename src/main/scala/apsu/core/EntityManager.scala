package apsu.core

import scala.reflect.runtime.{universe => ru}

/**
 * EntityManager
 *
 * @author david
 */
trait EntityManager {

  // ------------------------------------------------------------
  // Entity creation helpers

  /**
   * Creates a new entity
   * @return The new entity
   */
  @inline final def newEntity(): Entity = Entity()

  /**
   * Creates a new entity with the specified nickname
   * @param nickname The nickname of the entity
   * @throws IllegalArgumentException if the nickname already
   *         exists for some other entity
   */
  @inline final def newEntity(nickname: String): Entity = {
    val e = newEntity()
    setNickname(e, nickname)
    e
  }

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
   * @param c The component to set. Cannot itself be an entity.
   * @tparam C1 The component type
   * @throws IllegalArgumentException if <code>c</code> is an entity.
   */
  def set[C1](e: Entity, c: C1)(implicit t: ru.TypeTag[C1]): Unit

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

  /**
   * Removes the specified entity, removing all its components
   * and freeing up its nickname.
   * @param e The entity to remove.
   */
  def delete(e: Entity): Unit

  // ------------------------------------------------------------
  // Cross-entity methods

  /**
   * Gets all entity-component pairs for the specified component
   * type.
   *
   * @tparam C1 The component type
   * @return All entity-component pairs for the specified component
   *         type, or an empty Iterable if no entities with the
   *         specified component type exist.
   */
  def all[C1](implicit t: ru.TypeTag[C1]): Iterable[(Entity, C1)]

  /**
   * Executes the specified function on each entity-component pair
   * for the specified component type, and returns the result.
   * @param f The function to execute
   * @tparam C1 The component type
   * @tparam T The return type of the function
   * @return An Iterable containing the result of each function invocation,
   *         or an empty Iterable if no entities with the
   *         specified component type exist.
   */
  def forAll[C1, T](f: (Entity, C1) => T)(implicit t: ru.TypeTag[C1]): Iterable[T] = {
    all[C1].map { case (e, c) =>
      f(e, c)
    }
  }

  /**
   * Executes the specified function on each entity-component pair
   * for the specified component type, and discards the result.
   * @param f The function to execute
   * @tparam C1 The component type
   */
  def forAll[C1](f: (Entity, C1) => Unit)(implicit t: ru.TypeTag[C1]): Unit = {
    forAll[C1, Unit](f)
  }

  def first[C1](implicit t: ru.TypeTag[C1]): Option[(Entity, C1)] = {
    all[C1].headOption
  }

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
   * @throws IllegalArgumentException if the nickname already
   *         exists for some other entity
   */
  def setNickname(e: Entity, nickname: String): Option[String]

  /**
   * Clears any nickname set for the specified entity.
   * @param e The entity
   * @return The old nickname, if any
   */
  def clearNickname(e: Entity): Option[String]

}
