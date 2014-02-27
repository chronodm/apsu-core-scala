package apsu.demo.model.systems

import apsu.core.{Components, Entity}
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

    val components = mock[Components]
    when(components.get[Position, Velocity](entity)).thenReturn((Some(p0), Some(v)))

    val movement = new Movement(components)
    movement.update(entity)

    val captor = ArgumentCaptor.forClass(classOf[Position])
    verify(components).set(same(entity), captor.capture())

    val result = captor.getValue

    result should not be null

    result.x should be (1.0 +- 0.000001)
    result.y should be (2.0 +- 0.000001)
  }
}
