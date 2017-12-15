package com.agoda.session

import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Future

object TaskBuilder extends App {
  //the equivalent of Future.successful
  val task1: Task[String] = Task.now { println("task1: Price"); "Push!" }

  task1.runAsync.foreach(println)
  task1.runAsync.foreach(println)

  //Task.eval is the equivalent of Function0
  val task2: Task[String] = Task.eval { println("task2: Price"); "Push!" }

  task2.runAsync.foreach(println)
  task2.runAsync.foreach(println)

  //Task.evalOnce is the equivalent of a lazy val
  val task3: Task[String] = Task.evalOnce { println("task3: Price"); "Push!" }

  task3.runAsync.foreach(println)
  task3.runAsync.foreach(println)

  //Task.defer is about building a factory of tasks. behaviour like Task.eval
  val task4: Task[String] = Task.defer {
    Task.now { println("task4: Price"); "Push!" }
  }

  task4.runAsync.foreach(println)
  task4.runAsync.foreach(println)

  val task5 = Task.defer {
    val future = Future { println("task5: Price"); "Push!" }
    Task.fromFuture(future)
  }



  task5.runAsync.foreach(println)
  task5.runAsync.foreach(println)

  task5


  def convertTOtask(someFuture:Future[Int]) = {
    Task.deferFuture(someFuture.map(_.toString))
  }



//  *** MEMOIZE ON SUCCESS ***
  //
  // var effect = 0
//  val source = Task.eval {
//    effect += 1
//    if (effect < 3) throw new RuntimeException("dummy") else effect
//  }
//
//  val cached = source.memoizeOnSuccess
//
//  val f1 = cached.runAsync // yields RuntimeException
//  val f2 = cached.runAsync // yields RuntimeException
//  val f3 = cached.runAsync // yields 3
//  val f4 = cached.runAsync // yields 3
}
