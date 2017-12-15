package com.agoda.session

import monix.eval.Task
import monix.execution.ExecutionModel.BatchedExecution
import monix.execution.internal.Platform

import scala.concurrent.Future
import monix.execution.Scheduler.Implicits.global
//import scala.concurrent.ExecutionContext.Implicits.global
object TaskVsFuture extends App{

  implicit val executionModel = BatchedExecution(10)

  print("Main Thread")

  val future1 = Future{
    print("Future1: Price Push A")
    Thread.sleep(100)
    "Future1: Price Push B"
  }

  future1

//
//  val future2 = Future{
//    print("Future2: Price Push A")
//    Thread.sleep(100)
//    print("Future2: Price Push B")
//  }


  val task1 = Task.evalOnce{
    print("Task1: Price Push A")
    Thread.sleep(100)

    "Task2: Price Push A"
  }
//
//  val task2 = Task {
//    print("Forked Task1: Price Push A")
//    Thread.sleep(100)
//    print("Forked Task2: Price Push A")
//  }
////
 val a = task1.runAsync
  task1.runAsync
//  task2.runAsync

  a.cancel()

  def print(msg: String): Unit = {
    val now = java.time.format.DateTimeFormatter.ISO_INSTANT
      .format(java.time.Instant.now)
      .substring(11, 23)
    val thread = Thread.currentThread.getName()
    println(s"$now [$thread] $msg")
  }

  Thread.sleep(1000)
}
