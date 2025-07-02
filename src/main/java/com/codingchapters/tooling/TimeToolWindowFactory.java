package com.codingchapters.tooling;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.jcef.JBCefBrowser;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class TimeToolWindowFactory implements ToolWindowFactory {

    private AnAction setActionIcon(AnAction action, String tooltip, Icon icon) {
        action.getTemplatePresentation().setIcon(icon);
        action.getTemplatePresentation().setText(tooltip);
        return action;
    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        toolWindow.setIcon(AllIcons.Nodes.Tag);
        toolWindow.setStripeTitle("Current Time");
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JBCefBrowser browser = new JBCefBrowser();

        DefaultActionGroup toolBarActionGroup = new DefaultActionGroup();

        AnAction timeAction = setActionIcon(
            new RefreshTimeAction(),
            "Show Current Time",
            AllIcons.General.Information
        );
        toolBarActionGroup.addAction(timeAction);

        var toolbar = ActionManager.getInstance().createActionToolbar(
            ActionPlaces.TOOLWINDOW_TITLE,
            toolBarActionGroup,
            true
        );

        // Create main panel and add toolbar and browser
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(toolbar.getComponent(), BorderLayout.NORTH);
        mainPanel.add(browser.getComponent(), BorderLayout.CENTER);

        // Set the target component for the toolbar to fix the warning
        toolbar.setTargetComponent(mainPanel);

        var content = ContentFactory.getInstance().createContent(mainPanel, "", false);

        // Store the browser instance in the content's user data for later access
        content.putUserData(TimeToolWindow.KEY, browser);

        toolWindow.getContentManager().addContent(content);

        // Initialize the time display
        browser.loadHTML(TimeHtmlGenerator.timeHtmlString());
    }
}
