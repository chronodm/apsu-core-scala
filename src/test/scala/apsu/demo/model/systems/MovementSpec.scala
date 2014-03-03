package apsu.demo.model.systems

import apsu.old.{EntityManagerImpl, EntityManager, Entity}
import apsu.demo.model.components.{Position, Velocity}
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.same
import org.mockito.Mockito._
import org.mockito.Matchers.any
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import scala.reflect.runtime.{universe => ru}

class MovementSpec extends FlatSpec with Matchers with MockitoSugar {
  import EntityManager._

  "update" should "update entity position based on velocity" in {
    val entity = Entity()
    val p0 = Position(0,0)
    val v = Velocity(1.10714872, 2.23606798)

    val entityMgr = mock[EntityManager]

    val movement = new Movement()
    movement.update(entityMgr)

    // verify()s are a little ugly because Mockito complains if we
    // use matchers for the explicit parameters but not the implicits

    val systemCaptor = ArgumentCaptor.forClass(classOf[Process2[Position, Velocity]])
    verify(entityMgr).update[Position, Velocity](systemCaptor.capture())(any(classOf[ru.TypeTag[Position]]), any(classOf[ru.TypeTag[Velocity]]))

    val system = systemCaptor.getValue
    val update = system(p0, v, entity)
    update(entityMgr)

    val positionCaptor = ArgumentCaptor.forClass(classOf[Position])
    verify(entityMgr).set(same(entity), positionCaptor.capture())(any(classOf[ru.TypeTag[Position]]))

    val result = positionCaptor.getValue

    result should not be null

    result.x should be (1.0 +- 0.000001)
    result.y should be (2.0 +- 0.000001)
  }
}
