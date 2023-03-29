package io.adtrace.sdk.scheduler;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public abstract class AsyncTaskExecutor<Params, Result> {

    protected abstract Result doInBackground(Params[] params);

    protected void onPreExecute() { }

    protected void onPostExecute(Result result) { }

    @SafeVarargs
    public final AsyncTaskExecutor<Params, Result> execute(final Params ... params) {
        onPreExecute();

        final Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final Result result = doInBackground(params);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute(result);
                    }
                });
            }
        });

        return this;
    }
}
