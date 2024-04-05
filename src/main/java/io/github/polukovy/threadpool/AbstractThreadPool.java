package io.github.polukovy.threadpool;

import io.github.polukovy.threadpool.thread.WorkerThread;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An abstract implementation of the ThreadPool interface that provides basic functionality
 * for managing and executing tasks asynchronously.
 */
@Slf4j
public class AbstractThreadPool implements ThreadPool {

    /**
     * The queue for holding tasks submitted to the thread pool.
     */
    protected final Queue<Runnable> taskQueue;
    /**
     * The list of worker threads in the thread pool.
     */
    protected final List<WorkerThread> workers;

    /**
     * The uncaught exception handler for handling exceptions in worker threads.
     */
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    /**
     * A flag indicating whether the thread pool is running or shut down.
     */
    protected volatile boolean running;

    /**
     * Constructs a new AbstractThreadPool with the specified maximum number of threads
     * and uncaught exception handler.
     *
     * @param maxThreads               The maximum number of threads in the thread pool.
     * @param uncaughtExceptionHandler The uncaught exception handler for worker threads.
     */
    public AbstractThreadPool(int maxThreads, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.running = true;
        this.taskQueue = new LinkedList<>();
        this.workers = new ArrayList<>(maxThreads);
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;

        createWorkerThreads(maxThreads);
    }

    /**
     * Creates the worker threads for the thread pool.
     *
     * @param maxThreads The maximum number of threads in the thread pool.
     */
    private void createWorkerThreads(int maxThreads) {
        for (int i = 0; i < maxThreads; i++) {
            workers.add(createWorkerThread(i));
        }
    }

    /**
     * Creates a new worker thread for the thread pool.
     *
     * @param i The index of the worker thread.
     * @return The created worker thread.
     */
    protected WorkerThread createWorkerThread(int i) {
        var workerThread = new WorkerThread(taskQueue, running);
        workerThread.setName(THREAD_NAME.formatted(i));
        workerThread.start();
        workerThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        return workerThread;
    }

    /**
     * Submits a task to be executed by the thread pool.
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
            log.debug("Accept task {}", task);
            taskQueue.add(task);
            taskQueue.notifyAll(); // Notify waiting threads that a new task is available
        }
    }

    /**
     * Initiates an orderly shutdown of the thread pool.
     * Previously submitted tasks are executed, but no new tasks will be accepted.
     */
    @Override
    public void shutdown() {
        synchronized (taskQueue) {
            running = false;
            taskQueue.notifyAll(); // Notify all waiting threads to exit
        }
    }

    /**
     * Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and
     * interrupts all worker threads.
     */
    @Override
    public void shutdownNow() {
        shutdown();
        workers.forEach(Thread::interrupt); // Interrupt all worker threads
    }
}
