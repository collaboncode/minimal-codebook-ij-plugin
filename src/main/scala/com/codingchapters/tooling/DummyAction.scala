package com.codingchapters.tooling

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager

class DummyAction extends BaseAction with TimeHtmlGenerator {

  override def execute(e: AnActionEvent): Unit = {
    val result = for {
      project <- Option(e.getProject).filter(!_.isDisposed)
      toolWindow <- Option(ToolWindowManager.getInstance(project).getToolWindow("TimeWindow"))
      contentManager = toolWindow.getContentManager
      content <- Option(contentManager.getContent(0)).filter(_ => contentManager.getContentCount > 0)
      browser <- Option(content.getUserData(TimeToolWindow.KEY))
    } yield browser.loadHTML(generateTimeHtml())
    
    // Result is Option[Unit], automatically handles all null cases
    result.getOrElse(()) // Do nothing if any step fails
  }
}
