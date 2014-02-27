package apsu.demo.model.systems

import apsu.core.{Entity, System, Components}
import apsu.demo.model.components.{AngularVelocity, Orientation}

class Rotation(components: Components) extends System {
  override def update(e: Entity): Unit = {
    components.get[Orientation, AngularVelocity](e) match {
      case (Some(o0), Some(v)) =>
        val o1 = Orientation(o0.alpha + v.deltaA)
        components.set(e, o1)
      case _ =>
    }
  }
}