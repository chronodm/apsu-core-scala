package apsu.core

import java.io.PrintStream
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock._

@RunWith(classOf[JUnitRunner])
class HelloTest extends FlatSpec with Matchers with MockitoSugar {
  classOf[Hello].getSimpleName should "print hello" in {
    val out = mock[PrintStream]
    val hello = new Hello()
    hello.print(out)
    verify(out).println("hello")
  }
}