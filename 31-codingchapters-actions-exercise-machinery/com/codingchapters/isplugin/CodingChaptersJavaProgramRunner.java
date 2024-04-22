package com.codingchapters.isplugin;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.impl.DefaultJavaProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import org.jetbrains.annotations.NotNull;

public class CodingChaptersJavaProgramRunner extends DefaultJavaProgramRunner {

    public static final String runnerId = "CodogenicsJavaProgramRunner"  ;
    @Override
    public @NotNull String getRunnerId() {
        return runnerId;
    }

    @Override
    protected RunContentDescriptor doExecute(@NotNull RunProfileState state, @NotNull ExecutionEnvironment env) throws ExecutionException {

        try {
            return super.doExecute(state, env);
        } catch (ExecutionException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
