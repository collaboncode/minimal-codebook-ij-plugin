package com.codingchapters.isplugin

import com.intellij.execution.configurations.{ConfigurationType, RunConfiguration}
import com.intellij.openapi.project.Project
import org.jetbrains.plugins.scala.testingSupport.test.AbstractTestRunConfigurationFactory
import org.jetbrains.plugins.scala.testingSupport.test.scalatest.ScalaTestConfigurationType

class CodingChaptersConfigurationFactory(override val typ: ConfigurationType)
  extends AbstractTestRunConfigurationFactory(ScalaTestConfigurationType.apply()) {

  override def id: String = "CodogenicsTest"

  override def createTemplateConfiguration(project: Project): RunConfiguration =
    new CodingChaptersRunConfiguration(project, this, name = "")
}