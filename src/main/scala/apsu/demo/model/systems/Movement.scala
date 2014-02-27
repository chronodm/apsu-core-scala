package apsu.demo.model.systems

import apsu.core.{Entity, Components, System}
import apsu.demo.model.components.{Velocity, Position}

class Movement(components: Components) extends System {
  override def update(e: Entity): Unit = {
    components.get[Position, Velocity](e) match {
      case (Some(p0), Some(v)) =>
        val p1 = Position(p0.x + v.deltaX, p0.y + v.deltaY)
        components.set(e, p1)
      case _ =>
    }
  }
}

