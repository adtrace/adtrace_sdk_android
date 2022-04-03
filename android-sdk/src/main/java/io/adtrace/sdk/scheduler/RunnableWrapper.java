package io.adtrace.sdk.scheduler;

import io.adtrace.sdk.AdTraceFactory;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public class RunnableWrapper implements Runnable {
    private Runnable runnable;

    RunnableWrapper(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            runnable.run();
        } catch (Throwable t) {
            AdTraceFactory.getLogger().error("Runnable error [%s] of type [%s]",
                    t.getMessage(), t.getClass().getCanonicalName());
        }
    }
}
