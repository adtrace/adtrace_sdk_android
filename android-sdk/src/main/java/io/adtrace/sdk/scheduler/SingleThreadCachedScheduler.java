package io.adtrace.sdk.scheduler;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.adtrace.sdk.AdTraceFactory;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class SingleThreadCachedScheduler implements ThreadScheduler {
    private final List<Runnable> queue;
    private boolean isThreadProcessing;
    private boolean isTeardown;
    private ThreadPoolExecutor threadPoolExecutor;

    public SingleThreadCachedScheduler(final String source) {
        this.queue = new ArrayList<>();
        isThreadProcessing = false;
        isTeardown = false;

        // Same configuration as Executors.newCachedThreadPool().
        threadPoolExecutor = new ThreadPoolExecutor(
            0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadFactoryWrapper(source),
            new RejectedExecutionHandler() {     // Logs rejected runnables rejected from the entering the pool
                @Override
                public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
                    AdTraceFactory.getLogger().warn("Runnable [%s] rejected from [%s] ",
                            runnable.toString(), source);
                }
            }
        );
    }

    @Override
    public void submit(Runnable task) {
        synchronized (queue) {
            if (isTeardown) {
                return;
            }
            if (!isThreadProcessing) {
                isThreadProcessing = true;
                processQueue(task);
            }
            else {
                queue.add(task);
            }
        }
    }

    @Override
    public void schedule(final Runnable task, final long millisecondsDelay) {
        synchronized (queue) {
            if (isTeardown) {
                return;
            }

            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(millisecondsDelay);
                    } catch (InterruptedException e) {
                        AdTraceFactory.getLogger().warn("Sleep delay exception: %s",
                                e.getMessage());
                    }

                    submit(task);
                }
            });
        }
    }

    private void processQueue(final Runnable firstRunnable) {
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // Execute the first task.
                tryExecuteRunnable(firstRunnable);

                Runnable runnable;
                // Process all available items in the queue.
                while (true) {
                    synchronized (queue) {
                        // Possible teardown happened meanwhile.
                        if (isTeardown) {
                            return;
                        }

                        if (queue.isEmpty()) {
                            isThreadProcessing = false;
                            break;
                        }
                        runnable = queue.get(0);
                        queue.remove(0);
                    }
                    tryExecuteRunnable(runnable);
                }
            }
        });
    }

    private void tryExecuteRunnable(Runnable runnable) {
        try {
            if (isTeardown) {
                return;
            }
            runnable.run();
        } catch (Throwable t) {
            AdTraceFactory.getLogger().warn("Execution failed: %s", t.getMessage());
        }
    }

    @Override
    public void teardown() {
        synchronized (queue) {
            isTeardown = true;
            queue.clear();
            threadPoolExecutor.shutdown();
        }
    }
}
