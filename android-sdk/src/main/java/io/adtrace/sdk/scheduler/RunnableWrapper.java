package io.adtrace.sdk.scheduler;

import io.adtrace.sdk.AdTraceFactory;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
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
