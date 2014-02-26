package apsu.demo.model.systems

import apsu.core.{Entity, Components, System}
import apsu.demo.model.components.{Velocity, Position}
import apsu.demo.util.units.Distance

class Movement(components: Components) extends System {

  override def update(e: Entity): Unit = {
    components.get[Position, Velocity](e) match {
      case (Some(p0), Some(v)) =>
        val newX = new Distance(p0.x.v + v.deltaX)
        val newY = new Distance(p0.y.v + v.deltaY)
        val p1 = Position(newX, newY)
        components.set(e, p1)
      case _ =>
    }
  }

}
