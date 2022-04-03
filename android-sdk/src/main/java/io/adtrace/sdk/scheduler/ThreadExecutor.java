package io.adtrace.sdk.scheduler;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */



public interface ThreadExecutor {
    void submit(Runnable task);
    void teardown();
}
