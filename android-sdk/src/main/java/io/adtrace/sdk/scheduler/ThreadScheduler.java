package io.adtrace.sdk.scheduler;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface ThreadScheduler extends ThreadExecutor {
    void schedule(Runnable task, long millisecondsDelay);
}
