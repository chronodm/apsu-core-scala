package apsu.core

/**
 * MapEntityManagerSpec
 *
 * @author david
 */
class MapEntityManagerSpec extends EntityManagerSpec[MapEntityManager] {
  override def createManager: MapEntityManager = new MapEntityManager()
}
