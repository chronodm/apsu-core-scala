package apsu.core

import java.io.PrintStream

class Hello {
  def print(out: PrintStream) {
    out.println("hello")
  }
}

object Hello {
  def main(args: Array[String]) {
    val hello = new Hello()
    hello.print(System.out)
  }
}