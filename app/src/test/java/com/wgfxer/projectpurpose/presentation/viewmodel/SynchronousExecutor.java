package com.wgfxer.projectpurpose.presentation.viewmodel;

import java.util.concurrent.Executor;

class SynchronousExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}