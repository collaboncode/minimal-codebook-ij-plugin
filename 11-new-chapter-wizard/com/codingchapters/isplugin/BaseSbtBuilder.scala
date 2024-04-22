package com.codingchapters.isplugin

import com.intellij.openapi.externalSystem.service.project.wizard.AbstractExternalModuleBuilder
import org.jetbrains.sbt.project.SbtProjectSystem
import org.jetbrains.sbt.project.settings.SbtProjectSettings

abstract class BaseSbtBuilder extends AbstractExternalModuleBuilder[SbtProjectSettings](
  SbtProjectSystem.Id,
  new SbtProjectSettings
)