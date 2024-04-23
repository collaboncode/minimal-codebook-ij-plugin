package com.codingchapters.isplugin

import better.files.Dsl.mkdirs
import better.files.File

import com.intellij.ide.projectWizard.ProjectSettingsStep
import com.intellij.ide.util.projectWizard._
import com.intellij.openapi.module
import com.intellij.openapi.module.{JavaModuleType, ModifiableModuleModel, ModuleType}
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.{JavaSdk, SdkTypeId}
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.ui.Messages
import javax.swing.JLabel
import scala.util.Try

class SelectChapterBuilder extends BaseSbtBuilder with CodeTemplate {

  override def getModuleType: ModuleType[_] = JavaModuleType.getModuleType

  var chapterNumber = 0
  var supportedJdkMajorVersion = 17
  var mayBeModuleName : Option[String] = None

  override def createWizardSteps(wizardContext: WizardContext, modulesProvider: ModulesProvider): Array[ModuleWizardStep] = {
    Array[ModuleWizardStep](new SelectChapterWizardStep((this)))
  }

  override def modifySettingsStep(settingsStep: SettingsStep): ModuleWizardStep = {

    println("[modifySettingsStep]")

    mayBeModuleName = Try {
      val projectSettingStep = settingsStep.asInstanceOf[ProjectSettingsStep]
      val myTfNameTextField = projectSettingStep.getModuleNameField
      val moduleName = "chapter" + chapterNumber
      myTfNameTextField.setText(moduleName)
      myTfNameTextField.setEditable(false)
      moduleName
    }.toOption

    settingsStep.addSettingsField("", new JLabel(s"chapter${chapterNumber} supported jdk is >$supportedJdkMajorVersion<"))
    new JdkStepSettings(settingsStep, this, (_: SdkTypeId).isInstanceOf[JavaSdk], supportedJdkMajorVersion)
  }

  //IMPORTANT : this override is to by pass super class validation
  override def validate(currentProject: Project, project: Project): Boolean = {
    println(s"[validate] : SelectChapterBuilder : path is ${project.getBasePath}")

    import mouse.all._

    val either = for {
      moduleName <- mayBeModuleName.map(_.trim).filter(_.nonEmpty).toRight("module name should be there")
      baseDir = File(project.getBasePath)
      moduleDir = baseDir / moduleName
      locationNotEmpty <- moduleDir.notExists.either(s"[${moduleDir.path}] should not exists", ())
    } yield locationNotEmpty

    either match {
      case Left(msg) => Messages.showErrorDialog(msg, "Error")
      case Right(value) => ()
    }

    either.isRight
  }

  override def createModule(moduleModel: ModifiableModuleModel): module.Module = {
    val root = File(moduleModel.getProject.getBasePath)
    val pomContent = pom(chapterNumber)
    mkdirs(root)
    val pomFile = root / "pom.xml"
    pomFile.append(pomContent)

    val code = specCode()
    val pkgDir = root / "src" / "test" / "scala" / "com" / "example" / "specs"
    mkdirs(pkgDir)
    val specFile = pkgDir /"ConnectToServerAndTakesSomeTimeSimulationSpec.scala"
    specFile.append(code)

    super.createModule(moduleModel)
  }
}