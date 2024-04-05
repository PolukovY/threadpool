package io.github.polukovy.threadpool.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;

/**
 * A worker thread responsible for executing tasks from a task queue.
 */
@Slf4j
public class WorkerThread extends Thread {

    /**
     * The queue of tasks to be executed by this worker thread.
     */
    private final Queue<Runnable> taskQueue;
    /**
     * A flag indicating whether this worker thread is running.
     */
    private volatile boolean running;

    private volatile boolean terminate;

    /**
     * Constructs a new WorkerThread with the specified task queue and running flag.
     *
     * @param taskQueue The queue of tasks to be executed by this worker thread.
     * @param running   A flag indicating whether this worker thread is running.
     */
    public WorkerThread(Queue<Runnable> taskQueue, boolean running) {
        this.taskQueue = taskQueue;
        this.running = running;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    /**
     * Executes tasks from the task queue until interrupted or stopped.
     */
    @Override
    public void run() {
        while (true) {
            Runnable task;

            if (terminate) {
                log.debug("Terminated worker thread, exit the loop");
                break;
            }

            synchronized (taskQueue) {
                while (taskQueue.isEmpty() && running) {
                    try {
                        log.trace("Wait for tasks if the queue is empty");
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                if (!running && taskQueue.isEmpty()) {
                    log.trace("If running flag is false and task queue is empty, exit the loop");
                    break;
                }

                if (Thread.currentThread().isInterrupted()) {
                    log.trace("{} was interrupted...", Thread.currentThread().getName());
                    break;
                }

                task = taskQueue.poll();
            }

            if (task != null) {
                long startTime = System.currentTimeMillis();
                log.trace("Execute task {}", task);
                task.run();
                log.trace("Execute task completed {}. Elapsed time: {} seconds" , task, (System.currentTimeMillis() - startTime) / 1000 );

            }
        }
    }
}
