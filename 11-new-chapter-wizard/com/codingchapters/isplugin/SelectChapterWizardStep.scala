package com.codingchapters.isplugin

import cats.implicits._
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.ui.Messages
import mouse.all._
import org.jetbrains.plugins.scala.extensions.applyTo
import org.jetbrains.sbt.project.template.SComboBox

import java.awt.{BorderLayout, GridLayout}
import javax.swing._
import javax.swing.event.{PopupMenuEvent, PopupMenuListener}
import scala.util.{Failure, Success, Try}

class SelectChapterWizardStep(selectChapterBuilder: SelectChapterBuilder) extends ModuleWizardStep {


  private val chaptersCombo = applyTo(new SComboBox[String]())(
    _.setItems {
      val items = Array("Select Chapter", "1", "2", "3")
      items
    }
  )

  override def getComponent: JPanel = {
    val mainPanel = new JPanel()
    mainPanel.setLayout(new BorderLayout())

    val panel = new JPanel()
    panel.setLayout(new GridLayout(1, 2))
    panel.add(new JLabel("Select Chapter"))
    panel.add(chaptersCombo)

    mainPanel.add(panel, BorderLayout.NORTH)
    mainPanel
  }

  override def updateDataModel(): Unit = {
    println("[updateDataModel] : SelectChapterWizardStep")
    selectChapterBuilder.chapterNumber = Try(chaptersCombo.getSelectedItem.asInstanceOf[String].trim.toInt).getOrElse(0)
  }

  override def validate(): Boolean = {
    println("[validate] : SelectChapterWizardStep")

    val chapterNumberEither = for {
      chapterNumberString <- Try(chaptersCombo.getSelectedItem.asInstanceOf[String].trim).filter(_.nonEmpty).toEither.leftMap(t => s"chapter name shouldn't be empty \n[${t.getMessage}]")
      chapterNumber <- Try(chapterNumberString.toInt).toEither.leftMap(t => s"chapter [$chapterNumberString] is not number")
    } yield chapterNumber

    val jdkMajorVersionEither = for {
      jdkMajorVersionString <- Option(s"${selectChapterBuilder.supportedJdkMajorVersion}").filter(_.nonEmpty).toRight("chapter jdk shouldn't be empty")
      jdkMajorVersion <- Try(jdkMajorVersionString.toInt).toEither.leftMap(t => s"jdk version [$jdkMajorVersionString] is not number")
      supportedMajorVersions = List(8, 11, 17)
      isSupported = supportedMajorVersions.contains(jdkMajorVersion)
      _ <- isSupported.either(s"chapter jdk version $jdkMajorVersion should be [${supportedMajorVersions.mkString(",")}]", isSupported)
    } yield jdkMajorVersion

    val either = for {
      chapterNumber <- chapterNumberEither
      jdkMajorVersion <- jdkMajorVersionEither
    } yield (chapterNumber, jdkMajorVersion)

    either match {
      case Left(errorMsg) => Messages.showErrorDialog(errorMsg, "Error")
      case Right(_) => ()
    }

    either.isRight
  }
}