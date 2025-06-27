package com.codingchapters.tooling

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import com.intellij.openapi.application.ApplicationManager

trait BaseAction extends AnAction {

  override final def actionPerformed(e: AnActionEvent): Unit = {
    val application = ApplicationManager.getApplication
    if (application.isDispatchThread) {
      // Already on EDT, execute directly
      execute(e)
    } else {
      // Not on EDT, invoke later
      application.invokeLater(() => execute(e))
    }
  }

  def execute(e: AnActionEvent): Unit
}
