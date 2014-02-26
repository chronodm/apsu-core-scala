package demo.model.systems

import apsu.core.{Components, Entity}
import apsu.demo.model.components.{Position, Velocity}
import apsu.demo.model.systems.Movement
import apsu.demo.util.units.{Angle, Distance}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.same

class MovementSpec extends FlatSpec with Matchers with MockitoSugar {

  "update" should "update entity position based on velocity" in {
    val entity = Entity()
    val p0 = Position(Distance(0), Distance(0))
    val v = Velocity(Angle(1.10714872f), Distance(2.23606798f))

    val components = mock[Components]
    when(components.get[Position, Velocity](entity)).thenReturn((Some(p0), Some(v)))

    val movement = new Movement(components)
    movement.update(entity)

    val captor = ArgumentCaptor.forClass(classOf[Position])
    verify(components).set(same(entity), captor.capture())

    val value = captor.getValue

    value should not be null

    value.x.v should be (1.0f +- 0.000001f)
    value.y.v should be (2.0f +- 0.000001f)
  }
}
