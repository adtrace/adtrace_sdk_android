package io.adtrace.sdk;

import android.net.Uri;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */


public final class AdTraceLinkResolution {
    public interface AdTraceLinkResolutionCallback {
        void resolvedLinkCallback(Uri resolvedLink);
    }

    // https://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
    private static volatile ExecutorService executor;

    private static final int maxRecursions = 10;
    private static final String[] expectedUrlHostSuffixArray = {
            "adtrace.io",
            "adtrc.st",
            "go.link"
    };
    //todo: urls

    private AdTraceLinkResolution() { }

    public static void resolveLink(final String url,
                                   final String[] resolveUrlSuffixArray,
                                   final AdTraceLinkResolutionCallback adTraceLinkResolutionCallback)
    {
        if (adTraceLinkResolutionCallback == null) {
            return;
        }

        if (url == null) {
            adTraceLinkResolutionCallback.resolvedLinkCallback(null);
            return;
        }

        URL originalURL = null;
        try {
            originalURL = new URL(url);
        } catch (final MalformedURLException ignored) {
        }

        if (originalURL == null) {
            adTraceLinkResolutionCallback.resolvedLinkCallback(null);
            return;
        }

        if (! urlMatchesSuffix(originalURL.getHost(), resolveUrlSuffixArray)) {
            adTraceLinkResolutionCallback.resolvedLinkCallback(
                    AdTraceLinkResolution.convertToUri(originalURL));
            return;
        }

        if (executor == null) {
            synchronized (expectedUrlHostSuffixArray) {
                if (executor == null) {
                    executor = Executors.newSingleThreadExecutor();
                }
            }
        }

        final URL finalOriginalURL = originalURL;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                requestAndResolve(finalOriginalURL, 0, adTraceLinkResolutionCallback);
            }
        });
    }
    private static void resolveLink(
            final URL responseUrl,
            final URL previousUrl,
            final int recursionNumber,
            final AdTraceLinkResolutionCallback adTraceLinkResolutionCallback)
    {
        // return (possible null) previous url when the current one does not exist
        if (responseUrl == null) {
            adTraceLinkResolutionCallback.resolvedLinkCallback(
                    AdTraceLinkResolution.convertToUri(previousUrl));
            return;
        }

        // return found url with expected host
        if (isTerminalUrl(responseUrl.getHost())) {
            adTraceLinkResolutionCallback.resolvedLinkCallback(
                    AdTraceLinkResolution.convertToUri(responseUrl));
            return;
        }

        // return previous (non-null) url when it reached the max number of recursive tries
        if (recursionNumber > maxRecursions) {
            adTraceLinkResolutionCallback.resolvedLinkCallback(
                    AdTraceLinkResolution.convertToUri(responseUrl));
            return;
        }

        requestAndResolve(responseUrl, recursionNumber, adTraceLinkResolutionCallback);
    }

    private static void requestAndResolve(final URL urlToRequest,
                                          final int recursionNumber,
                                          final AdTraceLinkResolutionCallback adTraceLinkResolutionCallback)
    {
        final URL httpsUrl = convertToHttps(urlToRequest);
        URL resolvedURL = null;
        HttpURLConnection ucon = null;
        try {
            ucon = (HttpURLConnection) httpsUrl.openConnection();
            ucon.setInstanceFollowRedirects(false);

            ucon.connect();

            final String headerLocationField = ucon.getHeaderField("Location");
            if (headerLocationField != null) {
                resolvedURL = new URL(headerLocationField);
            }
        }
        catch (final Throwable ignored) {}
        finally {
            if (ucon != null) {
                ucon.disconnect();
            }

            resolveLink(resolvedURL,
                    httpsUrl,
                    recursionNumber + 1,
                    adTraceLinkResolutionCallback);
        }
    }

    private static boolean isTerminalUrl(final String urlHost) {
        return urlMatchesSuffix(urlHost, expectedUrlHostSuffixArray);
    }

    private static boolean urlMatchesSuffix(final String urlHost, final String[] suffixArray) {
        if (urlHost == null) {
            return false;
        }

        if (suffixArray == null) {
            return false;
        }

        for (final String expectedUrlHostSuffix : suffixArray) {
            if (urlHost.endsWith(expectedUrlHostSuffix)) {
                return true;
            }
        }

        return false;
    }

    private static URL convertToHttps(final URL urlToConvert) {
        if (urlToConvert == null) {
            return urlToConvert;
        }

        final String stringUrlToConvert = urlToConvert.toExternalForm();

        if (stringUrlToConvert == null) {
            return urlToConvert;
        }

        if (! stringUrlToConvert.startsWith("http:")) {
            return urlToConvert;
        }

        URL convertedUrl = urlToConvert;
        try {
            convertedUrl = new URL("https:" + stringUrlToConvert.substring(5));
        } catch (final MalformedURLException ignored) { }

        return convertedUrl;
    }

    private static Uri convertToUri(URL url) {
        if (url == null) {
            return null;
        }

        return Uri.parse(url.toString());
    }
}
