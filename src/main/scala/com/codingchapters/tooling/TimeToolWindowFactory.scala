package com.codingchapters.tooling

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.{ToolWindow, ToolWindowFactory}
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.openapi.util.Key
import com.intellij.openapi.actionSystem.{ActionManager, ActionPlaces}
import javax.swing.JPanel
import java.awt.BorderLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeToolWindowFactory extends ToolWindowFactory {
  
  override def createToolWindowContent(project: Project, toolWindow: ToolWindow): Unit = {
    val browser = new JBCefBrowser()
    val timeToolWindow = new TimeToolWindow(browser)
    
    // Create the toolbar
    val toolBarActionGroup = new com.codingchapters.tooling.DummyActionGroup()
    val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLWINDOW_TITLE, toolBarActionGroup, true)
    
    // Create main panel and add toolbar and browser
    val mainPanel = new JPanel(new BorderLayout())
    mainPanel.add(toolbar.getComponent, BorderLayout.NORTH)
    mainPanel.add(browser.getComponent, BorderLayout.CENTER)
    
    val content = ContentFactory.getInstance().createContent(mainPanel, "", false)
    
    // Store the browser instance in the content's user data for later access
    content.putUserData(TimeToolWindow.KEY, browser)
    
    toolWindow.getContentManager.addContent(content)
    
    // Initialize the time display
    timeToolWindow.updateTime()
  }
}

object TimeToolWindow {
  val KEY: Key[JBCefBrowser] = Key.create("TimeToolWindowBrowser")
}

class TimeToolWindow(browser: JBCefBrowser) {
  
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
  
}
