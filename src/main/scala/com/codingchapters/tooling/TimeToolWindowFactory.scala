package com.codingchapters.tooling

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.{ToolWindow, ToolWindowFactory}
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.openapi.util.Key
import javax.swing.JPanel
import java.awt.BorderLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeToolWindowFactory extends ToolWindowFactory {
  
  override def createToolWindowContent(project: Project, toolWindow: ToolWindow): Unit = {
    val timeToolWindow = new TimeToolWindow()
    val content = ContentFactory.getInstance().createContent(timeToolWindow.getContent, "", false)
    
    // Store the TimeToolWindow instance in the content's user data for later access
    content.putUserData(TimeToolWindow.KEY, timeToolWindow)
    
    toolWindow.getContentManager.addContent(content)
  }
}

object TimeToolWindow {
  val KEY: Key[TimeToolWindow] = Key.create("TimeToolWindow")
}

class TimeToolWindow {
  private val browser = new JBCefBrowser()
  private val panel = new JPanel(new BorderLayout())
  
  init()
  
  private def init(): Unit = {
    panel.add(browser.getComponent, BorderLayout.CENTER)
    updateTime()
  }
  
  def updateTime(): Unit = {
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formattedTime = currentTime.format(formatter)
    
    val html = s"""
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
    
    browser.loadHTML(html)
  }
  
  def getContent: JPanel = panel
  
  def getBrowser: JBCefBrowser = browser
}
