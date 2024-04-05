package io.github.polukovy.threadpool.impl;

import io.github.polukovy.threadpool.ThreadPool;
import io.github.polukovy.threadpool.handler.GeneralUncaughtExceptionHandler;

import static io.github.polukovy.threadpool.ThreadPool.SINGLE_THREAD_POOL;

/**
 * Utility class for creating different types of thread pools.
 */
public class Executors {

    /**
     * Creates a fixed-size thread pool with the specified maximum number of threads.
     *
     * @param maxThreads The maximum number of threads in the thread pool.
     * @return A new fixed-size thread pool.
     */
    public static ThreadPool newFixThreadPool(int maxThreads) {
        return new FixedThreadPollExecutor(maxThreads, new GeneralUncaughtExceptionHandler());
    }

    /**
     * Creates a single-threaded thread pool.
     *
     * @return A new single-threaded thread pool.
     */
    public static ThreadPool singleThreadPool() {
        return new FixedThreadPollExecutor(SINGLE_THREAD_POOL, new GeneralUncaughtExceptionHandler());
    }

    /**
     * Creates a cached thread pool with the specified number of core threads.
     *
     * @param coreThreads The number of core threads in the thread pool.
     * @return A new cached thread pool.
     */
    public static ThreadPool cachedThreadPool(int coreThreads) {
        return new CachedThreadPollExecutor(coreThreads, new GeneralUncaughtExceptionHandler());
    }

    /**
     * Creates a fixed-size thread pool with the specified maximum number of threads and uncaught exception handler.
     *
     * @param maxThreads               The maximum number of threads in the thread pool.
     * @param uncaughtExceptionHandler The uncaught exception handler for worker threads.
     * @return A new fixed-size thread pool with the specified uncaught exception handler.
     */
    public static ThreadPool newFixThreadPool(int maxThreads, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        return new FixedThreadPollExecutor(maxThreads, uncaughtExceptionHandler);
    }
}
