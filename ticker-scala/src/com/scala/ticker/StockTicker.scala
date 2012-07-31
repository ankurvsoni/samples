package com.scala.ticker

import java.net.URL
import scala.io.Source
import scala.xml.XML
import java.util.Timer
import java.util.TimerTask

/**
 * A stock ticker in scala.
 */
object StockTicker {

  def main(args: Array[String]): Unit = {
    println("===Stock Ticker Application===")
    StockQuoteProcessor.start
    var tickers = readLine("prompt> ")
    while (true) {
    	println("====Fetching quotes with actors====")
    	tickers.split(",").foreach(s => StockQuoteProcessor ! s)
    	Thread sleep 5000
    }
  }
}