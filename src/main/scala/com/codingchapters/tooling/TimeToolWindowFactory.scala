package com.codingchapters.tooling

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.{ActionManager, ActionPlaces, AnAction, DefaultActionGroup}
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.wm.{ToolWindow, ToolWindowFactory}
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.jcef.JBCefBrowser

import java.awt.BorderLayout
import javax.swing.JPanel

class TimeToolWindowFactory extends ToolWindowFactory with TimeHtmlGenerator {

  implicit class ToolbarActionExtensions(toolbarAction: AnAction) {
    def withIcon(tooltip: String, icon: javax.swing.Icon): AnAction = {
      toolbarAction.getTemplatePresentation.setIcon(icon)
      toolbarAction.getTemplatePresentation.setText(tooltip)
      toolbarAction
    }
  }

  override def init(toolWindow: ToolWindow): Unit = {
    toolWindow.setIcon(AllIcons.Nodes.Tag)
    toolWindow.setStripeTitle("Current Time")
  }

  override def createToolWindowContent(project: Project, toolWindow: ToolWindow): Unit = {
    val browser = new JBCefBrowser()

    val toolBarActionGroup = new DefaultActionGroup()

    val timeAction = new RefreshTimeAction().withIcon("Show Current Time", AllIcons.General.Information)
    toolBarActionGroup.addAction(timeAction)

    val toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLWINDOW_TITLE, toolBarActionGroup, true)
    
    // Create main panel and add toolbar and browser
    val mainPanel = new JPanel(new BorderLayout())
    mainPanel.add(toolbar.getComponent, BorderLayout.NORTH)
    mainPanel.add(browser.getComponent, BorderLayout.CENTER)
    
    // Set the target component for the toolbar to fix the warning
    toolbar.setTargetComponent(mainPanel)
    
    val content = ContentFactory.getInstance().createContent(mainPanel, "", false)
    
    // Store the browser instance in the content's user data for later access
    content.putUserData(TimeToolWindow.KEY, browser)
    
    toolWindow.getContentManager.addContent(content)
    
    // Initialize the time display
    browser.loadHTML(timeString())
  }
}

object TimeToolWindow {
  val KEY: Key[JBCefBrowser] = Key.create("TimeToolWindowBrowser")
}
