package io.github.polukovy.threadpool.impl;

import io.github.polukovy.threadpool.AbstractThreadPool;

/**
 * A thread pool executor with a single thread.
 */
public class SingleThreadPollExecutor extends AbstractThreadPool {

    /**
     * Constructs a new SingleThreadPoolExecutor with the specified maximum number of threads
     * and uncaught exception handler.
     *
     * @param maxThreads               The maximum number of threads in the thread pool.
     * @param uncaughtExceptionHandler The uncaught exception handler for worker threads.
     */
    public SingleThreadPollExecutor(int maxThreads, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        super(maxThreads, uncaughtExceptionHandler);
    }
}
