package io.github.polukovy.threadpool.impl;

import io.github.polukovy.threadpool.AbstractThreadPool;
import io.github.polukovy.threadpool.thread.WorkerThread;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * A thread pool executor that dynamically adjusts the number of threads based on the workload.
 */
@Slf4j
public class CachedThreadPollExecutor extends AbstractThreadPool {

    /**
     * The default number of threads to add when increasing the thread pool size.
     */
    private static final int DEFAULT_THREADS_TO_ADD = 10;

    /**
     * Constructs a new CachedThreadPoolExecutor with the specified maximum number of threads
     * and uncaught exception handler.
     *
     * @param maxThreads               The maximum number of threads in the thread pool.
     * @param uncaughtExceptionHandler The uncaught exception handler for worker threads.
     */
    public CachedThreadPollExecutor(int maxThreads, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        super(maxThreads, uncaughtExceptionHandler);
    }

    /**
     * Submits a task to be executed by the thread pool.
     * If the task queue is growing larger than the number of worker threads, additional threads are added.
     *
     * @param task The task to be executed.
     */
    @Override
    public void submit(Runnable task) {
        synchronized (taskQueue) {
            if (!running) {
                log.debug("Thread pool is shut down. Will not accept new task...");
                return;

                //throw new IllegalStateException("Thread pool is shut down. Will not accept new task...");
            }
            log.debug("Accept task " + task);
            taskQueue.add(task);


            increaseWorkerThreadIfNeeded();

            taskQueue.notifyAll(); // Notify waiting threads that a new task is available
        }
    }

    /**
     * Increases the number of worker threads if the task queue size exceeds the current number of threads.
     */
    private void increaseWorkerThreadIfNeeded() {
        if (taskQueue.size() > workers.size()) {
            log.debug("Increase workerThread {} to {} because taskQueue size is crowing", workers.size(), (workers.size() + DEFAULT_THREADS_TO_ADD));
            for (int i = 0; i < DEFAULT_THREADS_TO_ADD; i++) {
                int index = workers.size() + i;
                workers.add(createWorkerThread(index));
            }
        }
    }

    public List<WorkerThread> getWorkerThreads() {
        return workers;
    }
}
