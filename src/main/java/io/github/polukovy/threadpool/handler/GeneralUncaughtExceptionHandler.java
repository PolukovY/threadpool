package io.github.polukovy.threadpool.handler;

import lombok.extern.slf4j.Slf4j;

import static io.github.polukovy.threadpool.StackTraceLogger.getStackTraceAsString;

/**
 * A general uncaught exception handler implementation that prints the stack trace of the uncaught exception.
 */
@Slf4j
public class GeneralUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    /**
     * Invoked when a thread terminates due to the given uncaught exception.
     * This implementation prints the stack trace of the uncaught exception to the standard error stream.
     *
     * @param thread The thread that terminated.
     * @param e      The uncaught exception.
     */
    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        log.error("Uncaught exception in thread: {} stackTrace {}", thread.getName(), getStackTraceAsString(e));
    }
}
