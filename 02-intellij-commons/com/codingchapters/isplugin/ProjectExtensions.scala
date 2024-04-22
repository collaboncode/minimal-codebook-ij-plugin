package com.codingchapters.isplugin

import com.intellij.openapi.module.{ModuleManager, Module => IModule}
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.{DumbService, Project}
import org.jetbrains.plugins.scala.project.ModuleExt

import scala.util.Try


trait ProjectExtensions {

  final def findBuildModule(project: Project): Try[IModule] = {
    val tried = Try {
      val modules: Array[IModule] = ModuleManager.getInstance(project).getSortedModules
      val optionalModule = modules.find(!_.isBuildModule)
      optionalModule.get
    }
    tried
  }

  def isIndexingInProgress(project: Project): Boolean = {
    val iiip = DumbService.isDumb(project)
    iiip
  }

  def isIndexingDone(project: Project): Boolean = !isIndexingInProgress(project)

  def isBackgroundTaskInProgress(): Boolean = {
    val progressManager = ProgressManager.getInstance()
    val backgroundTasksInProgress = progressManager.hasProgressIndicator()
    backgroundTasksInProgress
  }
}