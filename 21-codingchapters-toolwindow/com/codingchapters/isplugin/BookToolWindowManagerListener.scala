package com.codingchapters.isplugin

import com.intellij.notification.{NotificationGroupManager, NotificationType}
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ex.ToolWindowManagerListener

import scala.util.{Failure, Success, Try}

case class BookToolWindowManagerListener(project: Project) extends ToolWindowManagerListener with ProjectExtensions {

  private val BOOK_TOOL_WINDOW_ID = "book-tool-window-id"

  override def toolWindowShown(toolWindow: ToolWindow): Unit = {

    if (!toolWindow.getId.equals(BOOK_TOOL_WINDOW_ID)) {
      return ()
    }
    //IMPORTANT below logic is only applicable for book-tool-window-id

    if (isIndexingInProgress(project)) {
      toolWindow.hide()
      val notificationGroup = NotificationGroupManager.getInstance.getNotificationGroup("CodingChaptersNotificationGroup")
      val notification = notificationGroup.createNotification("wait for indexing to complete before you open book", NotificationType.INFORMATION)
      notification.notify(project)
      return ()
    }

    ApplicationManager.getApplication.invokeLater(() =>
      try {
        bookToolWindowShown()
      }
      catch {
        case ex: Exception =>
          println("error occurred in listener")
          ex.printStackTrace()
      }
    )
  }

  private def bookToolWindowShown(): Unit = {

    val content =
      """
        |<!DOCTYPE html>
        |<html lang="en">
        |<head>
        |<meta charset="UTF-8">
        |<meta name="viewport" content="width=device-width, initial-scale=1.0">
        |<title>Universal Text Color Example</title>
        |<style>
        |    body {
        |        background-color: #FFFFFF; /* Default to white background */
        |        color: #333333; /* Default to dark gray text */
        |    }
        |
        |    /* Styling for the paragraph */
        |    p {
        |        padding: 20px;
        |        font-size: 18px;
        |    }
        |</style>
        |</head>
        |<body>
        |    <div>
        |        <p>Clicking on above green button</p>
        |        <p>will run com.example.specs.ConnectToServerAndTakesSomeTimeSimulationSpec</p>
        |    </div>
        |</body>
        |</html>
        |""".stripMargin

    val tried = for {
      service <- Try(project.getService(classOf[BookWindowComponentsService]))
      browser <- Try(service.browser)
      _ <- Try(browser.loadHTML(content))
    } yield ()

    tried match {
      case Failure(exception) => Messages.showErrorDialog(exception.getMessage, "Error")
      case Success(value) => ()
    }
  }
}