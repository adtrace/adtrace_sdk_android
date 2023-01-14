package io.adtrace.sdk;

import android.content.Context;

import io.adtrace.sdk.network.IActivityPackageSender;
import io.adtrace.sdk.network.UtilNetworking;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import io.adtrace.sdk.network.IActivityPackageSender;
import io.adtrace.sdk.network.UtilNetworking;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class AdTraceFactory {
    private static IPackageHandler packageHandler = null;
    private static IAttributionHandler attributionHandler = null;
    private static IActivityHandler activityHandler = null;
    private static ILogger logger = null;
    private static ISdkClickHandler sdkClickHandler = null;

    private static long timerInterval = -1;
    private static long timerStart = -1;
    private static long sessionInterval = -1;
    private static long subsessionInterval = -1;
    private static BackoffStrategy sdkClickBackoffStrategy = null;
    private static BackoffStrategy packageHandlerBackoffStrategy = null;
    private static BackoffStrategy installSessionBackoffStrategy = null;
    private static long maxDelayStart = -1;
    private static String baseUrl = null;
    private static String gdprUrl = null;
    private static String subscriptionUrl = null;
    private static UtilNetworking.IConnectionOptions connectionOptions = null;
    private static UtilNetworking.IHttpsURLConnectionProvider httpsURLConnectionProvider = null;
    private static boolean tryInstallReferrer = true;

    public static class URLGetConnection {
        HttpsURLConnection httpsURLConnection;
        URL url;
        URLGetConnection(HttpsURLConnection httpsURLConnection, URL url) {
            this.httpsURLConnection = httpsURLConnection;
            this.url = url;
        }
    }

    public static IPackageHandler getPackageHandler(
            IActivityHandler activityHandler,
            Context context,
            boolean startsSending,
            IActivityPackageSender packageHandlerActivityPackageSender)
    {
        if (packageHandler == null) {
            return new PackageHandler(activityHandler,
                    context,
                    startsSending,
                    packageHandlerActivityPackageSender);
        }
        packageHandler.init(activityHandler,
                context,
                startsSending,
                packageHandlerActivityPackageSender);
        return packageHandler;
    }

    public static ILogger getLogger() {
        if (logger == null) {
            // Logger needs to be "static" to retain the configuration throughout the app
            logger = new Logger();
        }
        return logger;
    }

    public static long getTimerInterval() {
        if (timerInterval == -1) {
            return Constants.ONE_MINUTE;
        }
        return timerInterval;
    }

    public static long getTimerStart() {
        if (timerStart == -1) {
            return Constants.ONE_MINUTE;
        }
        return timerStart;
    }

    public static long getSessionInterval() {
        if (sessionInterval == -1) {
            return Constants.THIRTY_MINUTES;
        }
        return sessionInterval;
    }

    public static long getSubsessionInterval() {
        if (subsessionInterval == -1) {
            return Constants.ONE_SECOND;
        }
        return subsessionInterval;
    }

    public static BackoffStrategy getSdkClickBackoffStrategy() {
        if (sdkClickBackoffStrategy == null) {
            return BackoffStrategy.SHORT_WAIT;
        }
        return sdkClickBackoffStrategy;
    }

    public static BackoffStrategy getPackageHandlerBackoffStrategy() {
        if (packageHandlerBackoffStrategy == null) {
            return BackoffStrategy.LONG_WAIT;
        }
        return packageHandlerBackoffStrategy;
    }

    public static BackoffStrategy getInstallSessionBackoffStrategy() {
        if (installSessionBackoffStrategy == null) {
            return BackoffStrategy.SHORT_WAIT;
        }
        return installSessionBackoffStrategy;
    }

    public static IActivityHandler getActivityHandler(AdTraceConfig config) {
        if (activityHandler == null) {
            return ActivityHandler.getInstance(config);
        }
        activityHandler.init(config);
        return activityHandler;
    }

    public static IAttributionHandler getAttributionHandler(
            IActivityHandler activityHandler,
            boolean startsSending,
            IActivityPackageSender packageHandlerActivityPackageSender)
    {
        if (attributionHandler == null) {
            return new AttributionHandler(activityHandler,
                    startsSending,
                    packageHandlerActivityPackageSender);
        }
        attributionHandler.init(activityHandler,
                startsSending,
                packageHandlerActivityPackageSender);
        return attributionHandler;
    }

    public static ISdkClickHandler getSdkClickHandler(
            IActivityHandler activityHandler,
            boolean startsSending,
            IActivityPackageSender packageHandlerActivityPackageSender)
    {
        if (sdkClickHandler == null) {
            return new SdkClickHandler(activityHandler,
                    startsSending,
                    packageHandlerActivityPackageSender);
        }

        sdkClickHandler.init(activityHandler, startsSending, packageHandlerActivityPackageSender);
        return sdkClickHandler;
    }

    public static long getMaxDelayStart() {
        if (maxDelayStart == -1) {
            return Constants.ONE_SECOND * 10; // 10 seconds
        }
        return maxDelayStart;
    }

    public static String getBaseUrl() {
        return AdTraceFactory.baseUrl;
    }

    public static String getGdprUrl() {
        return AdTraceFactory.gdprUrl;
    }

    public static String getSubscriptionUrl() {
        return AdTraceFactory.subscriptionUrl;
    }

    public static UtilNetworking.IConnectionOptions getConnectionOptions() {
        if (connectionOptions == null) {
            return UtilNetworking.createDefaultConnectionOptions();
        }
        return connectionOptions;
    }

    public static UtilNetworking.IHttpsURLConnectionProvider getHttpsURLConnectionProvider() {
        if (httpsURLConnectionProvider == null) {
            return UtilNetworking.createDefaultHttpsURLConnectionProvider();
        }
        return httpsURLConnectionProvider;
    }

    public static boolean getTryInstallReferrer() {
        return tryInstallReferrer;
    }

    public static void setPackageHandler(IPackageHandler packageHandler) {
        AdTraceFactory.packageHandler = packageHandler;
    }

    public static void setLogger(ILogger logger) {
        AdTraceFactory.logger = logger;
    }

    public static void setTimerInterval(long timerInterval) {
        AdTraceFactory.timerInterval = timerInterval;
    }

    public static void setTimerStart(long timerStart) {
        AdTraceFactory.timerStart = timerStart;
    }

    public static void setSessionInterval(long sessionInterval) {
        AdTraceFactory.sessionInterval = sessionInterval;
    }

    public static void setSubsessionInterval(long subsessionInterval) {
        AdTraceFactory.subsessionInterval = subsessionInterval;
    }

    public static void setSdkClickBackoffStrategy(BackoffStrategy sdkClickBackoffStrategy) {
        AdTraceFactory.sdkClickBackoffStrategy = sdkClickBackoffStrategy;
    }

    public static void setPackageHandlerBackoffStrategy(BackoffStrategy packageHandlerBackoffStrategy) {
        AdTraceFactory.packageHandlerBackoffStrategy = packageHandlerBackoffStrategy;
    }

    public static void setActivityHandler(IActivityHandler activityHandler) {
        AdTraceFactory.activityHandler = activityHandler;
    }

    public static void setAttributionHandler(IAttributionHandler attributionHandler) {
        AdTraceFactory.attributionHandler = attributionHandler;
    }

    public static void setSdkClickHandler(ISdkClickHandler sdkClickHandler) {
        AdTraceFactory.sdkClickHandler = sdkClickHandler;
    }

    public static void setBaseUrl(String baseUrl) {
        AdTraceFactory.baseUrl = baseUrl;
    }

    public static void setGdprUrl(String gdprUrl) {
        AdTraceFactory.gdprUrl = gdprUrl;
    }

    public static void setSubscriptionUrl(String subscriptionUrl) {
        AdTraceFactory.subscriptionUrl = subscriptionUrl;
    }

    public static void setConnectionOptions(UtilNetworking.IConnectionOptions connectionOptions) {
        AdTraceFactory.connectionOptions = connectionOptions;
    }

    public static void setHttpsURLConnectionProvider(
            UtilNetworking.IHttpsURLConnectionProvider httpsURLConnectionProvider)
    {
        AdTraceFactory.httpsURLConnectionProvider = httpsURLConnectionProvider;
    }

    public static void setTryInstallReferrer(boolean tryInstallReferrer) {
        AdTraceFactory.tryInstallReferrer = tryInstallReferrer;
    }

    public static void enableSigning() {
        AdTraceSigner.enableSigning(getLogger());
    }

    public static void disableSigning() {
        AdTraceSigner.disableSigning(getLogger());
    }

    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);

        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();

            if (l == 1) {
                h = "0" + h;
            }

            if (l > 2) {
                h = h.substring(l - 2, l);
            }

            str.append(h.toUpperCase());

            // if (i < (arr.length - 1)) str.append(':');
        }
        return str.toString();
    }

    public static void teardown(Context context) {
        if(context != null) {
            ActivityHandler.deleteState(context);
            PackageHandler.deleteState(context);
        }
        packageHandler = null;
        attributionHandler = null;
        activityHandler = null;
        logger = null;
        sdkClickHandler = null;

        timerInterval = -1;
        timerStart = -1;
        sessionInterval = -1;
        subsessionInterval = -1;
        sdkClickBackoffStrategy = null;
        packageHandlerBackoffStrategy = null;
        maxDelayStart = -1;
        baseUrl = Constants.BASE_URL;
        gdprUrl = Constants.GDPR_URL;
        subscriptionUrl = Constants.SUBSCRIPTION_URL;
        connectionOptions = null;
        httpsURLConnectionProvider = null;
        tryInstallReferrer = true;
    }
}
