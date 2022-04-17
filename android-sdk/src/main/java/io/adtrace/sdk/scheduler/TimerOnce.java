package io.adtrace.sdk.scheduler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.adtrace.sdk.AdTraceFactory;
import io.adtrace.sdk.ILogger;
import io.adtrace.sdk.Util;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */


public class TimerOnce {
    private FutureScheduler scheduler;

    private ScheduledFuture waitingTask;
    private String name;
    private Runnable command;
    private ILogger logger;

    public TimerOnce(Runnable command, String name) {
        this.name = name;
        this.scheduler = new SingleThreadFutureScheduler(name, true);
        this.command = command;
        this.logger = AdTraceFactory.getLogger();
    }

    public void startIn(long fireIn) {
        // cancel previous
        cancel(false);

        String fireInSeconds = Util.SecondsDisplayFormat.format(fireIn / 1000.0);

        logger.verbose("%s starting. Launching in %s seconds", name, fireInSeconds);

        waitingTask = scheduler.scheduleFuture(new Runnable() {
            @Override
            public void run() {
                logger.verbose("%s fired", name);
                command.run();
                waitingTask = null;
            }
        }, fireIn);
    }

    public long getFireIn() {
        if (waitingTask == null) {
            return 0;
        }
        return waitingTask.getDelay(TimeUnit.MILLISECONDS);
    }

    private void cancel(boolean mayInterruptIfRunning) {
        if (waitingTask != null) {
            waitingTask.cancel(mayInterruptIfRunning);
        }
        waitingTask = null;

        logger.verbose("%s canceled", name);
    }

    public void cancel() {
        cancel(false);
    }

    public void teardown() {
        cancel(true);

        if (scheduler != null) {
            scheduler.teardown();
        }

        scheduler = null;
    }
}
