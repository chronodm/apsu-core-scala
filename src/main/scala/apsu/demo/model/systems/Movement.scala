package apsu.demo.model.systems

import apsu.core.{Entity, EntityManager}
import apsu.demo.model.components.{Velocity, Position}

class Movement {

  def update(c: EntityManager) = {
    c.update[Position, Velocity](move(c) _)
  }

  def move(c: EntityManager)(p0: Position, v: Velocity, e: Entity): (EntityManager) => Unit = {
    setPosition(Position(p0.x + v.deltaX, p0.y + v.deltaY), e)
  }

  def setPosition(p1: Position, e: Entity)(c: EntityManager): Unit = {
    c.set(e, p1)
  }
}

