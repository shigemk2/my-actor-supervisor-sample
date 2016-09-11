package com.example

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
// import akka.actor.AllForOneStrategy // ある子アクターで例外が発生すると、他のすべての子アクターにも同じ例外処理を適用
// ↑ひとつが壊れるとすべてダメになるケースで使用します。
import akka.actor.{Actor, OneForOneStrategy, Props}  // それぞれの子アクターが個別に例外処理される
import akka.event.Logging

import scala.concurrent.duration._
import scala.language.postfixOps

class MyActor extends Actor {
  val log = Logging(context.system, this)

  val child = context.actorOf(Props[MyActor2], name = "myChild")

  // ここで例外処理を設定。
  // ウィンドウサイズ 60 秒で 10 回を越えて Restart が発生すると、例外処理を行わずに Stop します。
  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Resume// ゼロ除算など。握り潰して何もなかったことにする (Resume)
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate // MyActor で発生した例外として扱う
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
