package com.codingchapters.isplugin;

import com.intellij.execution.testframework.TestConsoleProperties;
import com.intellij.execution.testframework.sm.runner.SMTestProxy;
import com.intellij.execution.testframework.sm.runner.ui.SMTRunnerConsoleView;
import com.intellij.execution.testframework.sm.runner.ui.SMTestRunnerResultsForm;
import com.intellij.execution.testframework.ui.TestResultsPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CodingChaptersConsoleView extends SMTRunnerConsoleView implements PrivateAccess {

    private final String splitterProperty;

    public CodingChaptersConsoleView(TestConsoleProperties consoleProperties,
                                     @Nullable String splitterProperty) {
        super(consoleProperties, splitterProperty);
        this.splitterProperty = splitterProperty;
    }

    @Override
    protected TestResultsPanel createTestResultsPanel() {

        SMTestRunnerResultsForm smTestRunnerResultsForm = new SMTestRunnerResultsForm(getConsole(), myProperties, splitterProperty) {
            @Override
            public void onSuiteStarted(@NotNull SMTestProxy newSuite) {
                //newSuite.setPresentableName("checking code");
                super.onSuiteStarted(newSuite);
            }

            @Override
            public void onSuiteFinished(@NotNull SMTestProxy suite, @Nullable String nodeId) {
                System.out.println("suite finished");
                super.onSuiteFinished(suite, nodeId);
            }

            @Override
            public void onTestingFinished(SMTestProxy.SMRootTestProxy testsRoot) {
                System.out.println("after : test finished");
                super.onTestingFinished(testsRoot);
            }
        };
        try {
            writeSuperPrivateField(this, "myResultsViewer", smTestRunnerResultsForm);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return smTestRunnerResultsForm;
    }
}
