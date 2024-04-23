package com.codingchapters.isplugin;

import com.intellij.notification.NotificationGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;

public class BookToolWindowManagerListener implements ToolWindowManagerListener, ProjectExtensions{

    private final String BOOK_TOOL_WINDOW_ID = "book-tool-window-id";

    @Override
    public void toolWindowShown(ToolWindow toolWindow) {
        if (!toolWindow.getId().equals(BOOK_TOOL_WINDOW_ID)) {
            return;
        }
        // IMPORTANT below logic is only applicable for book-tool-window-id

        Project project = toolWindow.getProject();
        if (isIndexingInProgress(project)) {
            toolWindow.hide();
            NotificationGroup notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("CodingChaptersNotificationGroup");
            Notification notification = notificationGroup.createNotification("Wait for indexing to complete before you open book", NotificationType.INFORMATION);
            notification.notify(project);
            return;
        }

        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                BookWindowComponentsService service = project.getService(BookWindowComponentsService.class);
                service.browser().loadHTML("Click green button"); ;
            } catch (Exception ex) {
                System.out.println("Error occurred in listener");
                ex.printStackTrace();
            }
        });
    }
}