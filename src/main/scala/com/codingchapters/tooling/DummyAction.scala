package com.codingchapters.tooling

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import com.intellij.openapi.ui.Messages
import org.jetbrains.plugins.scala.testingSupport.test.testdata.ClassTestData

import scala.util.{Failure, Success, Try}

class DummyAction extends AnAction{

  private inline def strToInt(str : String) : Either[String, Int] = {
    Try(str.toInt) match {
      case Failure(exception) => Left(s"$str is not a number")
      case Success(value) => Right(value)
    }
  }

  private inline def someLogicThatUsedScalaPluginClasses() : Unit = {
    val classTestData : ClassTestData = ClassTestData(null, "")
  }

  override def actionPerformed(e: AnActionEvent): Unit = {

    val e1: Either[String, Int] = strToInt("2")
    val e2: Either[String, Int] = strToInt("two")

    someLogicThatUsedScalaPluginClasses()

    Messages.showInfoMessage("verification results failing", "test msg")
  }
}
