package com.agoda.session

import monix.reactive.Observable
import monix.execution.Scheduler.Implicits.global
object Temp extends App{

  val a = Observable
    .fromIterable(List.empty).mapAsync(10){x => x}.toListL.runOnComplete(x =>{
    println("******** reached here")
  })
Thread.sleep(1000)
}
