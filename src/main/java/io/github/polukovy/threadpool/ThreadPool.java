package io.github.polukovy.threadpool;

/**
 * This interface represents a thread pool for managing and executing tasks asynchronously.
 */
public interface ThreadPool {

    /**
     * The default thread name format for threads in the thread pool.
     */
    String THREAD_NAME = "ThreadPoll-%s";

    /**
     * The constant representing a single-threaded pool.
     */
    int SINGLE_THREAD_POOL = 1;

    /**
     * Submits a task to be executed by the thread pool.
     *
     * @param task The task to be executed.
     */
    void submit(Runnable task);

    /**
     * Initiates an orderly shutdown of the thread pool.
     * Previously submitted tasks are executed, but no new tasks will be accepted.
     */
    void shutdown();

    /**
     * Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and
     * returns a list of the tasks that were awaiting execution.
     *
     * @return A list of tasks that were awaiting execution.
     */
    void shutdownNow();
}

