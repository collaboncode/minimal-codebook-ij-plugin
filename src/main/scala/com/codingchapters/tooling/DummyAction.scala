package com.codingchapters.tooling

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import com.intellij.openapi.wm.ToolWindowManager

class DummyAction extends AnAction("Show Current Time", "Display current time in browser", null) with TimeHtmlGenerator {

  override def actionPerformed(e: AnActionEvent): Unit = {
    val project = e.getProject
    if (project != null) {
      val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("TimeWindow")
      val content = toolWindow.getContentManager.getContent(0)
      val browser = content.getUserData(TimeToolWindow.KEY)
      browser.loadHTML(generateTimeHtml())
    }
  }
}
