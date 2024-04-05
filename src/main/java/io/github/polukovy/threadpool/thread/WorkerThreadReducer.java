package io.github.polukovy.threadpool.thread;

import io.github.polukovy.threadpool.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.State.TIMED_WAITING;
import static java.lang.Thread.State.WAITING;

@Slf4j
public class WorkerThreadReducer extends Thread {

    private final List<WorkerThread> workers;
    private final int  frequencyInSec;


    public WorkerThreadReducer(List<WorkerThread> workers, int frequencyInSec) {
        setName("WorkerThreadReducerCleaner");
        this.workers = workers;
        this.frequencyInSec = frequencyInSec;
    }

    @Override
    public void run() {
        while (true) {

            ThreadUtils.sleep(frequencyInSec);

            Iterator<WorkerThread> iterator = workers.iterator();

            while(iterator.hasNext()) {
                if (workers.size() <= 20) {
                    log.debug("No need to clean we have only 20 active threads");
                    break;
                }

                log.debug("Cleaning time...");
                WorkerThread worker = iterator.next();


                if (worker.getState() == TIMED_WAITING || worker.getState() == WAITING) {
                    log.debug("Remove worker thread {}", worker.getName());
                    iterator.remove();
                    worker.setTerminate(true);
                    worker.interrupt();
                }
            }
        }
    }
}
