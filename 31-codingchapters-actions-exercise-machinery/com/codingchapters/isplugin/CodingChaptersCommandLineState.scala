package com.codingchapters.isplugin

import com.intellij.execution.runners.{ExecutionEnvironment, ProgramRunner}
import com.intellij.execution.testframework.TestConsoleProperties
import com.intellij.execution.testframework.sm.SMTestRunnerConnectionUtil.{getSplitterPropertyName, initConsoleView}
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties
import com.intellij.execution.{ExecutionResult, Executor}
import org.jetbrains.plugins.scala.testingSupport.test.CustomTestRunnerBasedStateProvider.TestFrameworkRunnerInfo
import org.jetbrains.plugins.scala.testingSupport.test.utils.RawProcessOutputDebugLogger
import org.jetbrains.plugins.scala.testingSupport.test.{AbstractTestRunConfiguration, ScalaTestFrameworkCommandLineState}

class CodingChaptersCommandLineState(override val configuration: AbstractTestRunConfiguration,
                                     env: ExecutionEnvironment,
                                     override val failedTests: Option[Seq[(String, String)]],
                                     runnerInfo: TestFrameworkRunnerInfo)
  extends ScalaTestFrameworkCommandLineState(configuration, env, failedTests, runnerInfo){

  override def execute(executor: Executor, runner: ProgramRunner[_]): ExecutionResult = {
    val processHandler = startProcess()

    attachExtensionsToProcess(configuration, processHandler)

    RawProcessOutputDebugLogger.maybeAddListenerTo(processHandler)

    val consoleView = {
      val consoleProperties: SMTRunnerConsoleProperties = configuration.createTestConsoleProperties(executor)
      consoleProperties.setIdBasedTestTree(true)
      val testFrameworkName = "Scala"
      val splitterPropertyName = getSplitterPropertyName(testFrameworkName)

      TestConsoleProperties.HIDE_PASSED_TESTS.set(consoleProperties, false)
      TestConsoleProperties.HIDE_PASSED_TESTS.primSet(consoleProperties, false)

      val consoleView = new CodingChaptersConsoleView(consoleProperties, splitterPropertyName)
      initConsoleView(consoleView, testFrameworkName)
      consoleView
    }
    consoleView.attachToProcess(processHandler)

    val executionResult = createExecutionResult(consoleView, processHandler)

    println("execute method finished in CodogenicsCommandLineState")

    executionResult
  }
}
