package com.scala.ticker

import scala.actors.Actor._
import java.net.URL
import scala.xml.XML
import scala.actors.Actor
import java.util.Timer
import java.util.TimerTask

object StockQuoteProcessor extends Actor {
  def act() {
    loop {
      react {
        case ticker: String => getStockQuote(ticker)
      }
    }
  }

  def getStockQuote(ticker: String): Unit = {
    val url = new URL("http://www.google.com/ig/api?stock=" + ticker)
    val body = url.openStream()
    val xml = XML.load(body)
    val threadId = Thread.currentThread().getId
    val threadName = Thread.currentThread().getName()
    for (node <- xml \\ "last") {
      node.attribute("data") match {
        case None => None
        case Some(lastPrice) => println("Thread [" + threadId + "-" + threadName + "] " + ticker + " = " + lastPrice)
      }
    }
  }
}