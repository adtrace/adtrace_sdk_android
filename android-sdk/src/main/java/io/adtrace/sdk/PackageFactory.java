package io.adtrace.sdk;

import android.net.Uri;
import android.net.UrlQuerySanitizer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.adtrace.sdk.Constants.ENCODING;
import static io.adtrace.sdk.Constants.MALFORMED;


/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */

public class PackageFactory {
    private static final String ADTRACE_PREFIX = "adtrace_";

    public static ActivityPackage buildReftagSdkClickPackage(final String rawReferrer,
                                                              final long clickTime,
                                                              final ActivityState activityState,
                                                              final AdTraceConfig adTraceConfig,
                                                              final DeviceInfo deviceInfo,
                                                              final SessionParameters sessionParameters) {
        if (rawReferrer == null || rawReferrer.length() == 0) {
            return null;
        }

        String referrer;

        try {
            referrer = URLDecoder.decode(rawReferrer, ENCODING);
        } catch (UnsupportedEncodingException e) {
            referrer = MALFORMED;
            AdTraceFactory.getLogger().error("Referrer decoding failed due to UnsupportedEncodingException. Message: (%s)", e.getMessage());
        } catch (IllegalArgumentException e) {
            referrer = MALFORMED;
            AdTraceFactory.getLogger().error("Referrer decoding failed due to IllegalArgumentException. Message: (%s)", e.getMessage());
        } catch (Exception e) {
            referrer = MALFORMED;
            AdTraceFactory.getLogger().error("Referrer decoding failed. Message: (%s)", e.getMessage());
        }

        AdTraceFactory.getLogger().verbose("Referrer to parse (%s)", referrer);

        UrlQuerySanitizer querySanitizer = new UrlQuerySanitizer();
        querySanitizer.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
        querySanitizer.setAllowUnregisteredParamaters(true);
        querySanitizer.parseQuery(referrer);

        PackageBuilder clickPackageBuilder = queryStringClickPackageBuilder(
                querySanitizer.getParameterList(),
                activityState,
                adTraceConfig,
                deviceInfo,
                sessionParameters);

        if (clickPackageBuilder == null) {
            return null;
        }

        clickPackageBuilder.referrer = referrer;
        clickPackageBuilder.clickTimeInMilliseconds = clickTime;
        clickPackageBuilder.rawReferrer = rawReferrer;

        ActivityPackage clickPackage = clickPackageBuilder.buildClickPackage(Constants.REFTAG);

        return clickPackage;
    }

    public static ActivityPackage buildDeeplinkSdkClickPackage(final Uri url,
                                                              final long clickTime,
                                                              final ActivityState activityState,
                                                              final AdTraceConfig adTraceConfig,
                                                              final DeviceInfo deviceInfo,
                                                              final SessionParameters sessionParameters) {
        if (url == null) {
            return null;
        }

        String urlString = url.toString();

        if (urlString == null || urlString.length() == 0) {
            return null;
        }

        AdTraceFactory.getLogger().verbose("Url to parse (%s)", url);

        UrlQuerySanitizer querySanitizer = new UrlQuerySanitizer();
        querySanitizer.setUnregisteredParameterValueSanitizer(UrlQuerySanitizer.getAllButNulLegal());
        querySanitizer.setAllowUnregisteredParamaters(true);
        querySanitizer.parseUrl(urlString);

        PackageBuilder clickPackageBuilder = queryStringClickPackageBuilder(
                querySanitizer.getParameterList(),
                activityState,
                adTraceConfig,
                deviceInfo,
                sessionParameters);

        if (clickPackageBuilder == null) {
            return null;
        }

        clickPackageBuilder.deeplink = url.toString();
        clickPackageBuilder.clickTimeInMilliseconds = clickTime;

        ActivityPackage clickPackage = clickPackageBuilder.buildClickPackage(Constants.DEEPLINK);

        return clickPackage;
    }

    public static ActivityPackage buildInstallReferrerSdkClickPackage(final String installReferrer,
                                                                      final long clickTimeInSeconds,
                                                                      final long installBeginInSeconds,
                                                                      final ActivityState activityState,
                                                                      final AdTraceConfig adTraceConfig,
                                                                      final DeviceInfo deviceInfo,
                                                                      final SessionParameters sessionParameters) {
        if (installReferrer == null || installReferrer.length() == 0) {
            return null;
        }

        long now = System.currentTimeMillis();

        PackageBuilder clickPackageBuilder = new PackageBuilder(
                adTraceConfig,
                deviceInfo,
                activityState,
                sessionParameters,
                now);

        if (clickPackageBuilder == null) {
            return null;
        }

        clickPackageBuilder.referrer = installReferrer;
        clickPackageBuilder.clickTimeInSeconds = clickTimeInSeconds;
        clickPackageBuilder.installBeginTimeInSeconds = installBeginInSeconds;

        ActivityPackage clickPackage = clickPackageBuilder.buildClickPackage(Constants.INSTALL_REFERRER);

        return clickPackage;
    }

    private static PackageBuilder queryStringClickPackageBuilder(
            final List<UrlQuerySanitizer.ParameterValuePair> queryList,
            final ActivityState activityState,
            final AdTraceConfig adTraceConfig,
            final DeviceInfo deviceInfo,
            final SessionParameters sessionParameters) {
        if (queryList == null) {
            return null;
        }

        Map<String, String> queryStringParameters = new LinkedHashMap<String, String>();
        AdTraceAttribution queryStringAttribution = new AdTraceAttribution();

        for (UrlQuerySanitizer.ParameterValuePair parameterValuePair : queryList) {
            readQueryString(
                    parameterValuePair.mParameter,
                    parameterValuePair.mValue,
                    queryStringParameters,
                    queryStringAttribution);
        }

        long now = System.currentTimeMillis();
        String reftag = queryStringParameters.remove(Constants.REFTAG);

        // Check if activity state != null
        // (referrer can be called before onResume)
        if (activityState != null) {
            long lastInterval = now - activityState.lastActivity;
            activityState.lastInterval = lastInterval;
        }

        PackageBuilder builder = new PackageBuilder(
                adTraceConfig,
                deviceInfo,
                activityState,
                sessionParameters,
                now);

        builder.extraParameters = queryStringParameters;
        builder.attribution = queryStringAttribution;
        builder.reftag = reftag;

        return builder;
    }

    private static boolean readQueryString(final String key,
                                           final String value,
                                           final Map<String, String> extraParameters,
                                           AdTraceAttribution queryStringAttribution) {
        if (key == null || value == null) {
            return false;
        }

        // Parameter key does not start with "adtrace_" prefix.
        if (!key.startsWith(ADTRACE_PREFIX)) {
            return false;
        }

        String keyWOutPrefix = key.substring(ADTRACE_PREFIX.length());

        if (keyWOutPrefix.length() == 0) {
            return false;
        }

        if (value.length() == 0) {
            return false;
        }

        if (!tryToSetAttribution(queryStringAttribution, keyWOutPrefix, value)) {
            extraParameters.put(keyWOutPrefix, value);
        }

        return true;
    }

    private static boolean tryToSetAttribution(AdTraceAttribution queryStringAttribution,
                                               final String key,
                                               final String value) {
        if (key.equals("tracker")) {
            queryStringAttribution.trackerName = value;
            return true;
        }

        if (key.equals("campaign")) {
            queryStringAttribution.campaign = value;
            return true;
        }

        if (key.equals("adgroup")) {
            queryStringAttribution.adgroup = value;
            return true;
        }

        if (key.equals("creative")) {
            queryStringAttribution.creative = value;
            return true;
        }

        return false;
    }
}
