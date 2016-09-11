package com.example

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, OneForOneStrategy, Props}
import akka.event.Logging

import scala.concurrent.duration._
import scala.language.postfixOps

class MyActor extends Actor {
  val log = Logging(context.system, this)

  val child = context.actorOf(Props[MyActor2], name = "myChild")

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  def receive = {
    case s: String => {
      log.info(s)
      child ! s
    }
    case _ => {
    }
  }
}
