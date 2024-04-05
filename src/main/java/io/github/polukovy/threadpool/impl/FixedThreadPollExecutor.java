package io.github.polukovy.threadpool.impl;

import io.github.polukovy.threadpool.AbstractThreadPool;
import io.github.polukovy.threadpool.thread.WorkerThread;

import java.util.List;

/**
 * A thread pool executor with a fixed number of threads.
 */
public class FixedThreadPollExecutor extends AbstractThreadPool {

    /**
     * Constructs a new FixedThreadPoolExecutor with the specified maximum number of threads
     * and uncaught exception handler.
     *
     * @param maxThreads               The maximum number of threads in the thread pool.
     * @param uncaughtExceptionHandler The uncaught exception handler for worker threads.
     */
    public FixedThreadPollExecutor(int maxThreads, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        super(maxThreads, uncaughtExceptionHandler);
    }

    public List<WorkerThread> getWorkerThreads() {
        return workers;
    }

}
