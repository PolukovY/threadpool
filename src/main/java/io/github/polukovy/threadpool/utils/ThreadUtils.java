package io.github.polukovy.threadpool.utils;

import java.util.concurrent.TimeUnit;

public class ThreadUtils {

    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
