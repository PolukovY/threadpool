package io.github.polukovy.threadpool;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Utility class for converting a throwable's stack trace to a string.
 */
public class StackTraceLogger {

    /**
     * Converts the stack trace of a throwable to a string representation.
     *
     * @param throwable The throwable whose stack trace is to be converted.
     * @return A string representation of the stack trace.
     * @throws RuntimeException If an I/O error occurs while converting the stack trace.
     */
    public static String getStackTraceAsString(Throwable throwable) {
        try (var stringWriter = new StringWriter()) {
            var printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
