package com.example

import akka.actor.{ActorSystem, Props}

object Hello {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("mySystem")

    val props = Props[MyActor]
    val actor = system.actorOf(props, name = "myActor")
    Thread.sleep(1000)

    actor ! "let's throw ArithmeticException"
    Thread.sleep(1000)
    actor ! "let's throw NullPointerException"
    Thread.sleep(1000)
    actor ! "let's throw IllegalArgumentException"
    Thread.sleep(1000)
    actor ! "let's throw Exception"
    Thread.sleep(1000)

    system.terminate
  }
}
