package com.codingchapters.isplugin

import com.intellij.icons.AllIcons
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.platform.{ProjectTemplate, ProjectTemplatesFactory}

import javax.swing.Icon

class CodingChaptersProjectTemplatesFactory extends ProjectTemplatesFactory{
  override def getGroups: Array[String] = Array("Coding Chapters")

  override def getGroupIcon(group: String): Icon = AllIcons.Nodes.Tag ;

  override def createTemplates(s: String, wizardContext: WizardContext): Array[ProjectTemplate] = {
    if (wizardContext.isCreatingNewProject) {
      Array( new BooksProjectTemplate )
    } else {
      Array.empty
    }
  }
}
