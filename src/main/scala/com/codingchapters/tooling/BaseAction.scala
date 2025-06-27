package com.codingchapters.tooling

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import com.intellij.openapi.application.ApplicationManager

trait BaseAction extends AnAction {

  override final def actionPerformed(e: AnActionEvent): Unit = {
    ApplicationManager.getApplication.invokeLater(() => execute(e))
  }

  def execute(e: AnActionEvent): Unit
}
