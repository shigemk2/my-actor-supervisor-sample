package com.example

import akka.actor.Actor
import akka.event.Logging

class MyActor2 extends Actor {
  val log = Logging(context.system, this)

  override def preStart(): Unit = {
    log.info("preStart")
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info("preRestart")
  }

  override def postRestart(reason: Throwable): Unit = {
    log.info("postRestart")
  }

  override def postStop(): Unit = {
    log.info("postStop")
  }

  def receive = {
    case "let's throw ArithmeticException" => {
      throw new ArithmeticException
    }
    case "let's throw NullPointerException" => {
      throw new NullPointerException
    }
    case "let's throw IllegalArgumentExceptoin" => {
      throw new IllegalArgumentException
    }
    case "let's throw Exception" => {
      throw new Exception
    }
    case _ => {
    }
  }

}
