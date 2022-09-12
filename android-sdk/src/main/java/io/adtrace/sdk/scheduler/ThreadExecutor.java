package io.adtrace.sdk.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public interface ThreadExecutor {
    void submit(Runnable task);
    void teardown();
}
