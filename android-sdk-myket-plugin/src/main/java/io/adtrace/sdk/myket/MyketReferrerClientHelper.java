package io.adtrace.sdk.myket;

import android.content.Context;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.adtrace.sdk.ILogger;
import ir.myket.referrersdk.api.InstallReferrerResponse;
import ir.myket.referrersdk.api.InstallReferrerStateListener;
import ir.myket.referrersdk.api.MyketReferrerClient;
import ir.myket.referrersdk.api.ReferrerDetails;

public class MyketReferrerClientHelper {

    public static ReferrerDetails getReferrer(Context context, final ILogger logger, long maxWaitTimeInMilli) {
        try {
            final MyketReferrerClient referrerClient = MyketReferrerClient.newBuilder(context).build();
            final BlockingQueue<ReferrerDetails> referrerDetailsHolder =
                    new LinkedBlockingQueue<ReferrerDetails>(1);

            referrerClient.startConnection(new InstallReferrerStateListener() {
                @Override
                public void onInstallReferrerSetupFinished(int responseCode) {
                    try {
                        switch (responseCode) {
                            case InstallReferrerResponse.OK:
                                try {
                                    ReferrerDetails details = referrerClient.getInstallReferrer();
                                    referrerDetailsHolder.offer(details);
                                } catch (Exception e) {
                                    logger.error("MyketReferrer getInstallReferrer: " + e.getMessage());
                                } finally {
                                    referrerClient.endConnection();
                                }
                                break;
                            case InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                                logger.info("MyketReferrer onInstallReferrerSetupFinished: FEATURE_NOT_SUPPORTED");
                                referrerClient.endConnection();
                                break;
                            case InstallReferrerResponse.DEVELOPER_ERROR:
                                logger.info("MyketReferrer onInstallReferrerSetupFinished: DEVELOPER_ERROR");
                                referrerClient.endConnection();
                                break;
                            case InstallReferrerResponse.NOT_FOUND:
                                logger.info("MyketReferrer onInstallReferrerSetupFinished: NOT_FOUND");
                                referrerClient.endConnection();
                                break;
                            default:
                                logger.info("MyketReferrer onInstallReferrerSetupFinished: response code %d",
                                        responseCode);
                                referrerClient.endConnection();
                                break;
                        }
                    } catch (Exception e) {
                        logger.error("MyketReferrer onInstallReferrerSetupFinished: " + e.getMessage());
                    }
                }

                @Override
                public void onInstallReferrerServiceDisconnected() {
                    referrerClient.endConnection();
                }
            });

            return referrerDetailsHolder.poll(maxWaitTimeInMilli, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("Exception while getting Myket referrer: " + e.getMessage());
        }

        return null;
    }
}
