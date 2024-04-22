package com.codingchapters.isplugin

import com.intellij.ide.util.projectWizard.AbstractModuleBuilder
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.platform.ProjectTemplate

import javax.swing.Icon

class BooksProjectTemplate extends ProjectTemplate{
  override def getName: String = "Books"

  override def getDescription: String = "Select Chapter from Book"

  override def getIcon: Icon = null

  override def createModuleBuilder(): AbstractModuleBuilder = new SelectChapterBuilder

  override def validateSettings(): ValidationInfo = null
}
