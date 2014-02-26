package apsu.core

import com.fasterxml.uuid.{Generators, EthernetAddress}
import java.util.UUID
import org.apache.log4j.Logger

case class Entity(id: UUID)

object Entity {

  private val log = Logger.getLogger(classOf[Entity])

  private val uuidGen = Generators.timeBasedGenerator({
    Option(EthernetAddress.fromInterface()) match {
      case Some(addr) => addr
      case _ => EthernetAddress.constructMulticastAddress()
    }
  })

  def apply(): Entity = {
    Entity(uuidGen.generate())
  }
}
