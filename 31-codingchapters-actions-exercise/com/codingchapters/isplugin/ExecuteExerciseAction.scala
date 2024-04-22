package com.codingchapters.isplugin

import com.intellij.execution.{ExecutionManager, RunManager, RunnerAndConfigurationSettings}
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.ExecutionUtil
import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import com.intellij.openapi.project.Project
import org.jetbrains.plugins.scala.extensions.withProgressSynchronously
import org.jetbrains.plugins.scala.testingSupport.test.AbstractTestRunConfiguration
import org.jetbrains.plugins.scala.testingSupport.test.testdata.ClassTestData

import scala.jdk.CollectionConverters._
import scala.util.Try

class ExecuteExerciseAction extends AnAction with ProjectExtensions {

  private def createNewConfiguration(project: Project): RunnerAndConfigurationSettings = {
    val confType = CodingChaptersConfigurationType()
    val runManager = RunManager.getInstance(project)
    val confName: String = runManager.suggestUniqueName(s"Checking code", confType)
    val factory = confType.getConfigurationFactories.head.asInstanceOf[CodingChaptersConfigurationFactory]
    val runAndConfSettings: RunnerAndConfigurationSettings = runManager.createConfiguration(confName, factory)
    runAndConfSettings
  }

  def execute(project: Project, specFqn: String): Try[Unit] = {

    val executionManagerWithEnvironmentTried = for {
      module <- findBuildModule(project)

      newRunnerAndConfigurationSettings <- Try(createNewConfiguration(project))
      runConf <- Try(newRunnerAndConfigurationSettings.getConfiguration.asInstanceOf[AbstractTestRunConfiguration])

      classTestData <- for {
        map <- Try(Map[String, String]())

        data = {
          val classTestData = new ClassTestData(runConf)
          classTestData.testClassPath = specFqn
          classTestData.envs = map.asJava
          classTestData.initWorkingDirIfEmpty()
          classTestData
        }
      } yield data

      _ <- Try {
        runConf.setModule(module)
        runConf.testConfigurationData = classTestData
      }

      environment <- Try {
        val customRunner = new CodingChaptersJavaProgramRunner()
        val builder = ExecutionUtil.createEnvironment(DefaultRunExecutor.getRunExecutorInstance, newRunnerAndConfigurationSettings)
        builder.activeTarget
        builder.runnerAndSettings(customRunner, newRunnerAndConfigurationSettings)
        val environment = builder.build()
        environment
      }

      executionManager <- Try(ExecutionManager.getInstance(newRunnerAndConfigurationSettings.getConfiguration.getProject))

    } yield (executionManager, environment)

    for {
      executionManagerWithEnvironment <- executionManagerWithEnvironmentTried
      (executionManager, environment) = executionManagerWithEnvironment
      status <- Try(executionManager.restartRunProfile(environment))
    } yield status
  }

  override def actionPerformed(e: AnActionEvent): Unit = {

    val specFqn = "com.example.specs.ConnectToServerAndTakesSomeTimeSimulationSpec"

    val eitherResult = for {
      timeToDisplay <- withProgressSynchronously("executing tests")(execute(e.getProject, specFqn))
    } yield timeToDisplay

    //    either match {
    //      case Left(errorMsg) =>
    //        Messages.showErrorDialog(errorMsg, "Error")
    //        println(errorMsg)
    //      case Right(value) => ()
    //    }
  }
}