package io.adtrace.sdk.scheduler;


import io.adtrace.sdk.AdTraceFactory;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
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
