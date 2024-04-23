package com.codingchapters.isplugin

import com.intellij.icons.AllIcons
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.{DumbAware, Project}
import com.intellij.openapi.wm.{ToolWindow, ToolWindowFactory}

import java.awt.BorderLayout
import javax.swing.JPanel

class BookToolWindowFactory extends SToolWindowFactory with DumbAware {

  private val logger = Logger.getInstance(classOf[BookToolWindowFactory])

  override def init(toolWindow: ToolWindow): Unit = {
    toolWindow.setIcon(AllIcons.Nodes.Tag)
    toolWindow.setStripeTitle("Book")
  }

  override def createToolWindowContent(project: Project, toolWindow: ToolWindow): Unit = {

    logger.debug("creating tool window")

    val htmlToolWindowComponentsService = project.getService(classOf[BookWindowComponentsService])

    val toolbar = htmlToolWindowComponentsService.toolbar

    val toolbarPanel = {
      val panel = new JPanel()
      panel.setLayout(new BorderLayout())

      panel.add(toolbar.getComponent(), BorderLayout.NORTH)
      panel.add(htmlToolWindowComponentsService.browser.getComponent, BorderLayout.CENTER)

      panel
    }

    toolWindow.getComponent.add(toolbarPanel)
    toolWindow.hide()
  }
}
