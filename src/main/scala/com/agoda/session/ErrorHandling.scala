package com.agoda.session
import scala.concurrent.duration._
import scala.concurrent.TimeoutException
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.util.Random

object ErrorHandling extends App {

  val task = {
    Task("PricePush!")
      .delayExecution(10.seconds)
      .timeout(3.seconds)
  }

  val recovered = task.onErrorHandleWith {
    case _: TimeoutException =>
      Task.now("Recovered!")
    case other =>
      Task.raiseError(other)
  }

  val recoveryTask = Task(Random.nextInt).flatMap {
    case even if even % 2 == 0 =>
      Task.now(even)
    case other =>
      Task.raiseError(new IllegalStateException(other.toString))
  }

  recoveryTask.onErrorRestart(maxRetries = 4)

  recoveryTask.onErrorRestartIf {
      case _: IllegalStateException => true
      case _ => false
    }

}
