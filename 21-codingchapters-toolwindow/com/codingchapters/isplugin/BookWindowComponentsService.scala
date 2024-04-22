package com.codingchapters.isplugin

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.{ActionManager, ActionPlaces, AnAction, DefaultActionGroup}
import com.intellij.openapi.compiler.{CompilationStatusListener, CompileContext, CompilerTopics}
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ex.ToolWindowManagerListener
import com.intellij.ui.jcef._
import com.jetbrains.cef.JCefAppConfig

import javax.swing.{Icon, JCheckBox, JLabel}

class BookWindowComponentsService(val project: Project) {

  project.getMessageBus.connect.subscribe(ToolWindowManagerListener.TOPIC, BookToolWindowManagerListener(project))

  private def actionWithIcon(actionId: String, tooltip: String, icon: Icon): AnAction = {
    val action = ActionManager.getInstance().getAction(actionId)
    action.getTemplatePresentation.setIcon(icon)
    action.getTemplatePresentation.setText(tooltip)
    action
  }

  private val toolBarActionGroup = {
    val defaultActionGroup = new DefaultActionGroup()

    val verifyAction = actionWithIcon("VerifyActionId", "Verify Exercise", AllIcons.Actions.Execute)
    defaultActionGroup.addAction(verifyAction)
    defaultActionGroup
  }


  val browser: JCEFHtmlPanel = {
    val jBCefApp = JBCefApp.getInstance()
    val client: JBCefClient = jBCefApp.createClient()
    val b: JCEFHtmlPanel = new JCEFHtmlPanel(client, null)
    Disposer.register(project, b)
    b
  }

  val toolbar = {
    val t = ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLWINDOW_TITLE, toolBarActionGroup, true)
    t.setTargetComponent(browser.getComponent)
    t
  }
}
