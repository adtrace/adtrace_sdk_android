package io.adtrace.sdk.scheduler;


import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.adtrace.sdk.AdTraceFactory;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class SingleThreadFutureScheduler implements FutureScheduler {
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public SingleThreadFutureScheduler(final String source, boolean doKeepAlive) {
        this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
                1,
                new ThreadFactoryWrapper(source),
                new RejectedExecutionHandler() {     // Logs rejected runnables rejected from the entering the pool
                    @Override
                    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
                        AdTraceFactory.getLogger().warn("Runnable [%s] rejected from [%s] ",
                                runnable.toString(), source);
                    }
                }
        );

        if (!doKeepAlive) {
            scheduledThreadPoolExecutor.setKeepAliveTime(10L, TimeUnit.MILLISECONDS);
            scheduledThreadPoolExecutor.allowCoreThreadTimeOut(true);
        }
    }

    @Override
    public ScheduledFuture<?> scheduleFuture(Runnable command, long millisecondDelay) {
        return scheduledThreadPoolExecutor.schedule(new RunnableWrapper(command), millisecondDelay, TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduledFuture<?> scheduleFutureWithFixedDelay(Runnable command, long initialMillisecondDelay, long millisecondDelay) {
        return scheduledThreadPoolExecutor.scheduleWithFixedDelay(new RunnableWrapper(command), initialMillisecondDelay, millisecondDelay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void teardown() {
        scheduledThreadPoolExecutor.shutdownNow();
    }
}
