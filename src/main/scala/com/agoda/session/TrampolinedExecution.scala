package com.agoda.session

import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Future

object TrampolinedExecution extends App{

//  val futureTask = Future{
//    val t1 = Future{
//      Thread.sleep(1000)
//      print("Future: Executing Task1 Parallely")
//    }
//
//    val t2 = Future{
//      Thread.sleep(1000)
//      print("Future: Executing Task2 Parallely")
//    }
//
//  }


  val mulithreadingTask = Task.eval{
    val t1 = Task.eval{
      Thread.sleep(1000)
      print("Executing Task1 Parallely")
    }

    val t2 = Task.eval{
      Thread.sleep(1000)
      print("Executing Task2 Parallely")
    }
    t1.runAsync
    t2.runAsync
  }
  mulithreadingTask.runAsync

  Thread.sleep(3000)


  def print(msg: String): Unit = {
    val now = java.time.format.DateTimeFormatter.ISO_INSTANT
      .format(java.time.Instant.now)
      .substring(11, 23)
    val thread = Thread.currentThread.getName()
    println(s"$now [$thread] $msg")
  }

}
