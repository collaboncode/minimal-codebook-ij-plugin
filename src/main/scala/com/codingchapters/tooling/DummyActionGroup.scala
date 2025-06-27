package com.codingchapters.tooling

import com.intellij.openapi.actionSystem.{AnAction, DefaultActionGroup}

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
