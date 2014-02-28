package apsu.demo.model.systems

import apsu.core.{Entity, Components, System}
import apsu.demo.model.components.{Velocity, Position}

class Movement extends System {

  override def update(c: Components) = {
    c.update(move(c) _)
  }

  def move(c: Components)(p0: Position, v: Velocity, e: Entity): (Components) => Unit = {
    setPosition(Position(p0.x + v.deltaX, p0.y + v.deltaY), e)
  }

  def setPosition(p1: Position, e: Entity)(c: Components): Unit = {
    c.set(p1, e)
  }
}

