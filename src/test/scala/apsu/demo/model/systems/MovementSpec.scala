package apsu.demo.model.systems

import apsu.core.{ComponentsImpl, Components, Entity}
import apsu.demo.model.components.{Position, Velocity}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.same

class MovementSpec extends FlatSpec with Matchers with MockitoSugar {

  "update" should "update entity position based on velocity" in {
    val entity = Entity()
    val p0 = Position(0,0)
    val v = Velocity(1.10714872, 2.23606798)

    val components = new ComponentsImpl
    components.set(p0, entity)
    components.set(v, entity)

    val movement = new Movement()
    movement.update(components)

    val result = components.get[Position](entity).get

    result should not be null

    result.x should be (1.0 +- 0.000001)
    result.y should be (2.0 +- 0.000001)
  }
}
