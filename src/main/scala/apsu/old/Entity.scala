package apsu.old

import com.fasterxml.uuid.{Generators, EthernetAddress}
import java.util.UUID
import org.apache.log4j.Logger

/**
 * The fundamental building block of the entity system.
 * @param id The UUID of the entity. Two entities with the same UUID are considered identical.
 */
case class Entity(id: UUID)

/**
 * Factory class for constructing entities.
 */
object Entity {

  // -----------------------------------------------------------
  // Private fields

  private val log = Logger.getLogger(classOf[Entity])

  private val uuidGen = Generators.timeBasedGenerator({
    val enetAddr = Option(EthernetAddress.fromInterface()) match {
      case Some(addr) => addr
      case _ => EthernetAddress.constructMulticastAddress()
    }
    log.info(s"Initializing UUID generator with Ethernet address ${enetAddr.toString}")
    enetAddr
  })

  // ------------------------------------------------------------
  // Public methods

  /**
   * Constructs a new entity with a globally unique ID.
   * @return A new entity
   */
  def apply(): Entity = {
    Entity(uuidGen.generate())
  }
}
