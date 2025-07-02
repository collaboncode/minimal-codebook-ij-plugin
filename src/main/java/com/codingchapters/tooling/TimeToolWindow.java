package com.codingchapters.tooling;

import com.intellij.openapi.util.Key;
import com.intellij.ui.jcef.JBCefBrowser;

public class TimeToolWindow {
    public static final Key<JBCefBrowser> KEY = Key.create("TimeToolWindowBrowser");
}
