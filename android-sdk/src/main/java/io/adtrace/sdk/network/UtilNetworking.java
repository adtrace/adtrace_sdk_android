package io.adtrace.sdk.network;

import io.adtrace.sdk.AdTraceFactory;
import io.adtrace.sdk.Constants;
import io.adtrace.sdk.ILogger;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class UtilNetworking {
    private static String userAgent;

    private static ILogger getLogger() {
        return AdTraceFactory.getLogger();
    }

    public static void setUserAgent(String userAgent) {
        UtilNetworking.userAgent = userAgent;
    }

    public interface IConnectionOptions {
        void applyConnectionOptions(HttpsURLConnection connection, String clientSdk);
    }

    public static IConnectionOptions createDefaultConnectionOptions() {
        return new IConnectionOptions() {
            @Override
            public void applyConnectionOptions(final HttpsURLConnection connection,
                                               final String clientSdk)
            {
                connection.setRequestProperty("Client-SDK", clientSdk);
                connection.setConnectTimeout(Constants.ONE_MINUTE);
                connection.setReadTimeout(Constants.ONE_MINUTE);

                if (userAgent != null) {
                    connection.setRequestProperty("User-Agent", userAgent);
                }

            }
        };
    }

    public interface IHttpsURLConnectionProvider {
        HttpsURLConnection generateHttpsURLConnection(URL url) throws IOException;
    }

    public static IHttpsURLConnectionProvider createDefaultHttpsURLConnectionProvider() {
        return new IHttpsURLConnectionProvider() {
            @Override
            public HttpsURLConnection generateHttpsURLConnection(final URL url)
                    throws IOException
            {
                return (HttpsURLConnection) url.openConnection();
            }
        };
    }

    public static String extractJsonString(final JSONObject jsonObject, final String name) {
        // taken from JSONObject.optString(...) to add null fallback
        final Object object = jsonObject.opt(name);
        if (object instanceof String) {
            return (String) object;
        }
        if (object != null) {
            return object.toString();
        }
        return null;
    }

    public static Long extractJsonLong(final JSONObject jsonObject,
                                       final String name)
    {
        // taken from JSONObject.optLong(...) to add null fallback
        final Object object = jsonObject.opt(name);
        if (object instanceof Long) {
            return (Long) object;
        }
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        if (object instanceof String) {
            try {
                return (long) Double.parseDouble((String) object);
            } catch (final NumberFormatException ignored) {
            }
        }
        return null;
    }
}
