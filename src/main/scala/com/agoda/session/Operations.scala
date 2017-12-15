package com.agoda.session

import monix.eval.Task

import scala.annotation.tailrec
import monix.execution.Scheduler.Implicits.global

object Operations extends App {
  @tailrec
  def factorial(accumulator: Int, number: Int): Int = {
    if (number == 1) accumulator
    else factorial(number * accumulator, number - 1)
  }

  val startTime = System.currentTimeMillis()

  def factorialTask(accumulator: Int, number: Int): Task[Int] = {
    if (number == 1) Task.now(accumulator)
    else Task.defer(factorialTask(number * accumulator, number - 1))
  }

  //flatMap, which is the monadic bind operation that forces ordering (e.g. execute this, then that, then that)

  def factorialTaskFlatMap(accumulator: Int, number: Int): Task[Int] = {
    Task.eval(number == 1).flatMap {
      case true => Task.now(accumulator)
      case false => factorialTask(number * accumulator, number - 1)
    }
  }

  //  map and FlatMap on Task are stack and Heap safe

  factorialTask(1, 10).runAsync.foreach(println)

  Thread.sleep(1000)


  // ******** Task parallel effect

//  val task1: Task[String] = Task.eval(???)
//  val task2: Task[String] = Task.eval(???)
//  val task3: Task[String] = Task.eval(???)
//
//  // Ordered operations based on flatMap, sequential execution
//  val aggregate = for {
//    t1 <- task1
//    t2 <- task2
//    t3 <- task3
//  } yield {
//    "Gotcha!"
//  }
// //Potentially executed in parallel
//val aggregate2 =
//Task.zip3(task1, task2, task3).map {
//  case (t1, t2, t3) => "Gotcha!"
//}


}
