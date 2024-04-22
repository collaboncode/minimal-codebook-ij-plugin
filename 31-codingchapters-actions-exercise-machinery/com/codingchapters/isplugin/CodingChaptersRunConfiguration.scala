package com.codingchapters.isplugin

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties
import com.intellij.openapi.project.Project
import org.jetbrains.plugins.scala.testingSupport.test.CustomTestRunnerBasedStateProvider.TestFrameworkRunnerInfo
import org.jetbrains.plugins.scala.testingSupport.test.scalatest.ScalaTestRunConfiguration
import org.jetbrains.plugins.scala.testingSupport.test.{RunStateProvider, ScalaTestFrameworkConsoleProperties}

class CodingChaptersRunConfiguration(project: Project,
                                     configurationFactory: ConfigurationFactory,
                                     name: String) extends ScalaTestRunConfiguration(project, configurationFactory, name){

  override def createTestConsoleProperties(executor: Executor): SMTRunnerConsoleProperties = {
    new ScalaTestFrameworkConsoleProperties(this, testFramework.getName(), executor) ;
  }

  override def runStateProvider: RunStateProvider = {

    val runnerInfo = TestFrameworkRunnerInfo(classOf[org.jetbrains.plugins.scala.testingSupport.scalaTest.ScalaTestRunner])
    new CodingChaptersStateProvider(this, runnerInfo)
  }
}
