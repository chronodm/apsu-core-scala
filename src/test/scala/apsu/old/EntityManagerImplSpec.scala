package apsu.old

import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.mockito.Mockito._
import org.mockito.Matchers._
import apsu.core.Entity

class EntityManagerImplSpec extends FlatSpec with Matchers with MockitoSugar {

  case class Comp1(id: Int)
  case class Comp2(id: Int)

  "set()/get()" should "store & retrieve components for entities" in {
    val entityMgr = new EntityManagerImpl

    val e = Entity()
    entityMgr.get[Comp1](e) should be(None)
    entityMgr.get[Comp2](e) should be(None)

    entityMgr.set[Comp1](e, Comp1(1))
    entityMgr.set[Comp2](e, Comp2(2))

    entityMgr.get[Comp1](e) should be (Some(Comp1(1)))
    entityMgr.get[Comp2](e) should be (Some(Comp2(2)))
  }

  "update[C1,C2]" should "call process() on all entities with both components" in {
    val entityMgr = new EntityManagerImpl

    val e1 = Entity()
    val e2 = Entity()
    val e3 = Entity()
    val e4 = Entity()

    // TODO more concise syntax for this

    entityMgr.set[Comp1](e1, Comp1(1))
    entityMgr.set[Comp2](e1, Comp2(1))

    entityMgr.set[Comp1](e2, Comp1(2))

    entityMgr.set[Comp1](e3, Comp1(3))
    entityMgr.set[Comp2](e3, Comp2(3))

    entityMgr.set[Comp2](e4, Comp2(4))

    val process = mock[EntityManager.Process2[Comp1, Comp2]]
    when(process.apply(
      any(classOf[Comp1]), any(classOf[Comp2]), any(classOf[Entity]))
    ).thenReturn((em: EntityManager) => {})

    entityMgr.update(process)

    verify(process).apply(Comp1(1), Comp2(1), e1)
    verify(process).apply(Comp1(3), Comp2(3), e3)

    verifyNoMoreInteractions(process)
  }

}
