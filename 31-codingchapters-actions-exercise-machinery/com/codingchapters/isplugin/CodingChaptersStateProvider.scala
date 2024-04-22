package com.codingchapters.isplugin

import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import org.jetbrains.plugins.scala.testingSupport.test.CustomTestRunnerBasedStateProvider.TestFrameworkRunnerInfo
import org.jetbrains.plugins.scala.testingSupport.test.{AbstractTestRunConfiguration, RunStateProvider}

final class CodingChaptersStateProvider(configuration: AbstractTestRunConfiguration,
                                        runnerInfo: TestFrameworkRunnerInfo) extends RunStateProvider {

  override def commandLineState(
                                 env: ExecutionEnvironment,
                                 failedTests: Option[Seq[(String, String)]]
                               ): RunProfileState =
    new CodingChaptersCommandLineState(configuration, env, failedTests, runnerInfo)
}