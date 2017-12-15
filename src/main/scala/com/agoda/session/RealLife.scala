package com.agoda.session

import monix.eval.Task
import monix.reactive.Observable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.io.StdIn
import scala.util.Failure
import scala.util.control.NonFatal

object Realife extends App {
  val start = System.currentTimeMillis()
  val range = (1 to 100).map(x => {
    Task.deferFuture {
      Future {
        Thread.sleep(1000)
        println(
          s"[${Thread.currentThread().getName}] $x ${System.currentTimeMillis() - start}")
        x
      }
    }
  })

  val promise = Promise[List[Int]]
  val tasks = Observable
    .fromIterable(range)
    .mapAsync(16) { x =>
      x
    }
  val runningTask = tasks
    .doOnSubscriptionCancel(() =>
      promise.complete(Failure(new Exception("canceled"))))
    .toListL
    .runOnComplete { result =>
      promise.complete(result)
    }

  //  runningTask.cancel()

  promise.future
    .map { result =>
      println(
        s"[${Thread.currentThread().getName}] result: ${result.mkString(",")}")
    }
    .recover {
      case NonFatal(t) =>
        println(s"[${Thread.currentThread().getName}] ${t.getMessage}")
    }

  StdIn.readLine()
}
