package com.agoda.session

import monix.eval.{Coeval, Task}
import monix.execution.exceptions.DummyException

object CoEval extends App {

  /*
   *  - `Coeval`'s execution is always immediate / synchronous, whereas
   *    `Task` can describe asynchronous computations
   *  - `Coeval` is not cancelable, obviously, since execution is
   *    immediate and there's nothing to cancel
   */
  val coeval = Coeval.eval {
    println("Price!")
    "Push!"
  }

  // Eager
  val fa = Coeval.now(println("**********"))
  val fe = Coeval.raiseError(new DummyException("dummy"))

  val func = Coeval.eval(10 * 2)
  func.runAttempt match {
    case Coeval.Now(value) =>
      println("Success: " + value)
    case Coeval.Error(e) =>
      e.printStackTrace()
  }

  func.runTry

  //Task, but works only for immediate, synchronous evaluation
//  println(coeval.value)
//  println(coeval.value)

  Thread.sleep(100)

}
