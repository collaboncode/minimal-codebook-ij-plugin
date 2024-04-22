package com.codingchapters.isplugin

import com.intellij.execution.configurations.ConfigurationFactory
import org.jetbrains.plugins.scala.testingSupport.test.scalatest.ScalaTestConfigurationType

case class CodingChaptersConfigurationType() extends ScalaTestConfigurationType {

  println("<init> of CodogenicsConfigurationType")

  override def getConfigurationFactories: Array[ConfigurationFactory] = {
    Array[ConfigurationFactory](new CodingChaptersConfigurationFactory(this))
  }
}
