package com.codingchapters.isplugin

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.testframework.sm.SMTestRunnerConnectionUtil.getSplitterPropertyName
import com.intellij.execution.testframework.sm.runner.{SMTRunnerConsoleProperties, SMTRunnerEventsAdapter, SMTRunnerEventsListener, SMTestProxy}
import com.intellij.execution.testframework.sm.runner.ui.SMTRunnerConsoleView
import com.intellij.openapi.project.Project
import org.jetbrains.plugins.scala.testingSupport.test.CustomTestRunnerBasedStateProvider.TestFrameworkRunnerInfo
import org.jetbrains.plugins.scala.testingSupport.test.scalatest.ScalaTestRunConfiguration
import org.jetbrains.plugins.scala.testingSupport.test.{CustomTestRunnerBasedStateProvider, RunStateProvider, ScalaTestFrameworkConsoleProperties}

class CodingChaptersRunConfiguration(project: Project,
                                     configurationFactory: ConfigurationFactory,
                                     name: String) extends ScalaTestRunConfiguration(project, configurationFactory, name){

  override def createTestConsoleProperties(executor: Executor): SMTRunnerConsoleProperties = {
    new ScalaTestFrameworkConsoleProperties(this, testFramework.getName(), executor) ;
  }

  override def runStateProvider: RunStateProvider = {

    val runnerInfo = TestFrameworkRunnerInfo(classOf[org.jetbrains.plugins.scala.testingSupport.scalaTest.ScalaTestRunner])

    val connection = project.getMessageBus.connect
    connection.subscribe(SMTRunnerEventsListener.TEST_STATUS, new SMTRunnerEventsAdapter() {
      override def onSuiteStarted(suite: SMTestProxy): Unit = {
        suite.setPresentableName("sample")
        super.onSuiteStarted(suite)
      }

      override def onSuiteFinished(suite: SMTestProxy): Unit = {
        super.onSuiteFinished(suite)
      }

      override def onTestingFinished(testsRoot: SMTestProxy.SMRootTestProxy): Unit = {
        super.onTestingFinished(testsRoot)
      }
    })

    new CustomTestRunnerBasedStateProvider(this, runnerInfo)
  }
}
