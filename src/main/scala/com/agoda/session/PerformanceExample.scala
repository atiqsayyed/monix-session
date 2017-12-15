package com.agoda.session

import monix.eval.Task

import scala.concurrent.Future
import monix.execution.Scheduler.Implicits.global

object PerformanceExample extends App {
  val startTime = System.currentTimeMillis()
//  val futureResult = Future {
//    (0 to 1000000).foldLeft(0)(_ + _)
//  }
//
//  futureResult.onComplete(x => {
//    val endTime = System.currentTimeMillis()
//    println(s"Time Taken ${endTime - startTime}")
//  })

  val taskResult = Task.eval {
    (0 to 1000000).foldLeft(0)(_ + _)
  }

  taskResult.runOnComplete(x =>{
    val endTime = System.currentTimeMillis()
    println(s"Time Taken ${endTime - startTime}")
  })

}
