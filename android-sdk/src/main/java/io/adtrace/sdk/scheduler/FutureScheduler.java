package io.adtrace.sdk.scheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface FutureScheduler {
    ScheduledFuture<?> scheduleFuture(Runnable command, long millisecondDelay);
    ScheduledFuture<?> scheduleFutureWithFixedDelay(Runnable command,
                                                    long initialMillisecondDelay,
                                                    long millisecondDelay);
    void teardown();
}
