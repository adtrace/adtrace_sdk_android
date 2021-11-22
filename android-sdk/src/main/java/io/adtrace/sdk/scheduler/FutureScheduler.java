package io.adtrace.sdk.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public interface FutureScheduler {
    ScheduledFuture<?> scheduleFuture(Runnable command, long millisecondDelay);
    ScheduledFuture<?> scheduleFutureWithFixedDelay(Runnable command,
                                                    long initialMillisecondDelay,
                                                    long millisecondDelay);
    <V> ScheduledFuture<V> scheduleFutureWithReturn(Callable<V> callable, long millisecondDelay);

    void teardown();
}
