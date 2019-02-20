package io.adtrace.sdk.scheduler;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface ThreadExecutor {
    void submit(Runnable task);
    void teardown();
}
