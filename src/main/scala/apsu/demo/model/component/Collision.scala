package apsu.demo.model.component

import apsu.core.Entity

// TODO how do we ensure entity garbage collection for these
// TODO maybe components should only know about other components?
case class Collision(e1: Entity, e2: Entity)
