package io.adtrace.sdk.cafebazaar;

import android.app.Application;
import android.content.Context;

import com.cafebazaar.referrersdk.ReferrerClient;
import com.cafebazaar.referrersdk.model.ReferrerDetails;
import com.cafebazaar.servicebase.state.ClientError;
import com.cafebazaar.servicebase.state.ClientStateListener;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.adtrace.sdk.ILogger;

public class CafeBazaarReferrerClientHelper {

    public static ReferrerDetails getReferrer(Context context, final ILogger logger, long maxWaitTimeInMilli) {
        try {
            Context appContext = context.getApplicationContext();
            if (!(appContext instanceof Application)) {
                logger.error("CafeBazaarReferrer: application context is not an Application instance");
                return null;
            }

            final ReferrerClient referrerClient =
                    ReferrerClient.Companion.getClient((Application) appContext);
            final BlockingQueue<ReferrerDetails> referrerDetailsHolder =
                    new LinkedBlockingQueue<ReferrerDetails>(1);

            // Cafe Bazaar requires startConnection off the main thread.
            referrerClient.startConnection(new ClientStateListener() {
                @Override
                public void onReady() {
                    try {
                        ReferrerDetails details = referrerClient.getReferrerDetails();
                        if (details != null) {
                            referrerDetailsHolder.offer(details);
                        } else {
                            logger.info("CafeBazaarReferrer getReferrerDetails: no referrer");
                        }
                    } catch (Exception e) {
                        logger.error("CafeBazaarReferrer getReferrerDetails: " + e.getMessage());
                    } finally {
                        referrerClient.endConnection();
                    }
                }

                @Override
                public void onError(ClientError clientError) {
                    try {
                        if (clientError != null) {
                            logger.info("CafeBazaarReferrer onError: %s", clientError.name());
                        } else {
                            logger.info("CafeBazaarReferrer onError: unknown");
                        }
                    } finally {
                        referrerClient.endConnection();
                    }
                }
            });

            return referrerDetailsHolder.poll(maxWaitTimeInMilli, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("Exception while getting Cafe Bazaar referrer: " + e.getMessage());
        }

        return null;
    }
}
