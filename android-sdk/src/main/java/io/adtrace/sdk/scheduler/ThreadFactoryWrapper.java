package io.adtrace.sdk.scheduler;

import android.os.Process;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import io.adtrace.sdk.AdTraceFactory;
import io.adtrace.sdk.Constants;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public class ThreadFactoryWrapper implements ThreadFactory {
    private String source;

    public ThreadFactoryWrapper(String source) {
        this.source = source;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = Executors.defaultThreadFactory().newThread(runnable);

        thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND + Process.THREAD_PRIORITY_MORE_FAVORABLE);
        thread.setName(Constants.THREAD_PREFIX + thread.getName() + "-" + source);
        thread.setDaemon(true);

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread th, Throwable tr) {
                AdTraceFactory.getLogger().error("Thread [%s] with error [%s]",
                        th.getName(), tr.getMessage());
            }
        });

        return thread;
    }
}
