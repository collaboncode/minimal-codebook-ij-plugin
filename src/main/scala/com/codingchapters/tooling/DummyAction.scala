package com.codingchapters.tooling

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent, DefaultActionGroup}
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.wm.ToolWindowManager
import org.jetbrains.plugins.scala.testingSupport.test.testdata.ClassTestData

import scala.util.{Failure, Success, Try}

class DummyAction extends AnAction("Show Current Time", "Display current time in browser", null) {

  private def strToInt(str : String) : Either[String, Int] = {
    Try(str.toInt) match {
      case Failure(exception) => Left(s"$str is not a number")
      case Success(value) => Right(value)
    }
  }

  private def someLogicThatUsedScalaPluginClasses() : Unit = {
    val classTestData : ClassTestData = ClassTestData(null, "")
  }

  override def actionPerformed(e: AnActionEvent): Unit = {
    val project = e.getProject
    if (project != null) {
      val toolWindowManager = ToolWindowManager.getInstance(project)
      val toolWindow = toolWindowManager.getToolWindow("TimeWindow")
      
      if (toolWindow != null) {
        // Show the tool window
        toolWindow.show()
        
        // Get the content and update the time
        val contentManager = toolWindow.getContentManager
        if (contentManager.getContentCount > 0) {
          val content = contentManager.getContent(0)
          if (content != null) {
            // Get the TimeToolWindow instance from user data
            val timeToolWindow = content.getUserData(TimeToolWindow.KEY)
            if (timeToolWindow != null) {
              timeToolWindow.updateTime()
            } else {
              Messages.showInfoMessage("TimeToolWindow instance not found in content.", "Error")
            }
          }
        }
      } else {
        Messages.showInfoMessage("Time Window not found. Please restart IntelliJ IDEA.", "Error")
      }
    }

    val e1: Either[String, Int] = strToInt("2")
    val e2: Either[String, Int] = strToInt("two")

    someLogicThatUsedScalaPluginClasses()

    Messages.showInfoMessage("Time updated in browser window", "Current Time Display")
  }
}

class DummyActionGroup extends DefaultActionGroup("Time Tools", true) {
  
  private def actionWithIcon(actionId: String, tooltip: String, icon: javax.swing.Icon): AnAction = {
    val action = com.intellij.openapi.actionSystem.ActionManager.getInstance().getAction(actionId)
    if (action != null) {
      action.getTemplatePresentation.setIcon(icon)
      action.getTemplatePresentation.setText(tooltip)
      action
    } else {
      // If action not found, create a new DummyAction instance
      val newAction = new DummyAction()
      newAction.getTemplatePresentation.setIcon(icon)
      newAction.getTemplatePresentation.setText(tooltip)
      newAction
    }
  }

  // Initialize the toolbar with actions
  locally {
    import com.intellij.icons.AllIcons
    
    val timeAction = actionWithIcon("DummyActionId", "Show Current Time", AllIcons.General.Information)
    addAction(timeAction)
  }
}
