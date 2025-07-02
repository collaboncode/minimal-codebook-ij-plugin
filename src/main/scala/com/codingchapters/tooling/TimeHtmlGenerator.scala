package com.codingchapters.tooling

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

trait TimeHtmlGenerator {
  
  def timeString(): String = {
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formattedTime = currentTime.format(formatter)
    
    s"""
      <!DOCTYPE html>
      <html>
      <head>
        <title>Current Time</title>
        <style>
          body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
          }
          .time-container {
            text-align: center;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
          }
          .time {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
          }
          .label {
            font-size: 16px;
            color: #666;
          }
        </style>
      </head>
      <body>
        <div class="time-container">
          <div class="label">Current Time</div>
          <div class="time">$formattedTime</div>
        </div>
      </body>
      </html>
    """
  }
}
