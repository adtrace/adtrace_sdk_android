package io.adtrace.sdk;

import static io.adtrace.sdk.Constants.ADTRACE_PREINSTALL_CONTENT_PROVIDER_INTENT_ACTION;
import static io.adtrace.sdk.Constants.ADTRACE_PREINSTALL_CONTENT_URI_AUTHORITY;
import static io.adtrace.sdk.Constants.ADTRACE_PREINSTALL_CONTENT_URI_PATH;
import static io.adtrace.sdk.Constants.ADTRACE_PREINSTALL_FILE_SYSTEM_PATH;
import static io.adtrace.sdk.Constants.ADTRACE_PREINSTALL_SYSTEM_PROPERTY_PATH;
import static io.adtrace.sdk.Constants.ADTRACE_PREINSTALL_SYSTEM_PROPERTY_PREFIX;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class PreinstallUtil {

    private static final long SYSTEM_PROPERTY_BITMASK = 1;                  //00...000000001
    private static final long SYSTEM_PROPERTY_REFLECTION_BITMASK = 2;       //00...000000010
    private static final long SYSTEM_PROPERTY_PATH_BITMASK = 4;             //00...000000100
    private static final long SYSTEM_PROPERTY_PATH_REFLECTION_BITMASK = 8;  //00...000001000
    private static final long CONTENT_PROVIDER_BITMASK = 16;                //00...000010000
    private static final long CONTENT_PROVIDER_INTENT_ACTION_BITMASK = 32;  //00...000100000
    private static final long FILE_SYSTEM_BITMASK = 64;                     //00...001000000
    private static final long CONTENT_PROVIDER_NO_PERMISSION_BITMASK = 128; //00...010000000

    // bitwise OR (|) of all above locations
    private static final long ALL_LOCATION_BITMASK = (SYSTEM_PROPERTY_BITMASK |
            SYSTEM_PROPERTY_REFLECTION_BITMASK |
            SYSTEM_PROPERTY_PATH_BITMASK |
            SYSTEM_PROPERTY_PATH_REFLECTION_BITMASK |
            CONTENT_PROVIDER_BITMASK |
            CONTENT_PROVIDER_INTENT_ACTION_BITMASK |
            FILE_SYSTEM_BITMASK |
            CONTENT_PROVIDER_NO_PERMISSION_BITMASK);                        //00...011111111

    public static boolean hasAllLocationsBeenRead(long status) {
        // Check if the given status has none of the valid location with bit `0`, indicating it has
        // not been read
        return (status & ALL_LOCATION_BITMASK) == ALL_LOCATION_BITMASK;
    }

    public static boolean hasNotBeenRead(String location, long status) {
        // Check if the given status has bit '0` (not `1`) for the given location, indicating it has not been read
        switch(location) {
            case Constants.SYSTEM_PROPERTIES :
                return (status & SYSTEM_PROPERTY_BITMASK) != SYSTEM_PROPERTY_BITMASK;
            case Constants.SYSTEM_PROPERTIES_REFLECTION :
                return (status & SYSTEM_PROPERTY_REFLECTION_BITMASK) != SYSTEM_PROPERTY_REFLECTION_BITMASK;
            case Constants.SYSTEM_PROPERTIES_PATH :
                return (status & SYSTEM_PROPERTY_PATH_BITMASK) != SYSTEM_PROPERTY_PATH_BITMASK;
            case Constants.SYSTEM_PROPERTIES_PATH_REFLECTION :
                return (status & SYSTEM_PROPERTY_PATH_REFLECTION_BITMASK) != SYSTEM_PROPERTY_PATH_REFLECTION_BITMASK;
            case Constants.CONTENT_PROVIDER :
                return (status & CONTENT_PROVIDER_BITMASK) != CONTENT_PROVIDER_BITMASK;
            case Constants.CONTENT_PROVIDER_INTENT_ACTION :
                return (status & CONTENT_PROVIDER_INTENT_ACTION_BITMASK) != CONTENT_PROVIDER_INTENT_ACTION_BITMASK;
            case Constants.FILE_SYSTEM :
                return (status & FILE_SYSTEM_BITMASK) != FILE_SYSTEM_BITMASK;
            case Constants.CONTENT_PROVIDER_NO_PERMISSION:
                return (status & CONTENT_PROVIDER_NO_PERMISSION_BITMASK) != CONTENT_PROVIDER_NO_PERMISSION_BITMASK;
        }
        return false;
    }

    public static long markAsRead(String location, long status) {
        // Set the bit to '1` for the given location, indicating it has been read
        switch(location) {
            case Constants.SYSTEM_PROPERTIES :
                return (status | SYSTEM_PROPERTY_BITMASK);
            case Constants.SYSTEM_PROPERTIES_REFLECTION :
                return (status | SYSTEM_PROPERTY_REFLECTION_BITMASK);
            case Constants.SYSTEM_PROPERTIES_PATH :
                return (status | SYSTEM_PROPERTY_PATH_BITMASK);
            case Constants.SYSTEM_PROPERTIES_PATH_REFLECTION :
                return (status | SYSTEM_PROPERTY_PATH_REFLECTION_BITMASK);
            case Constants.CONTENT_PROVIDER :
                return (status | CONTENT_PROVIDER_BITMASK);
            case Constants.CONTENT_PROVIDER_INTENT_ACTION :
                return (status | CONTENT_PROVIDER_INTENT_ACTION_BITMASK);
            case Constants.FILE_SYSTEM :
                return (status | FILE_SYSTEM_BITMASK);
            case Constants.CONTENT_PROVIDER_NO_PERMISSION:
                return (status | CONTENT_PROVIDER_NO_PERMISSION_BITMASK);
        }
        return status;
    }

    public static String getPayloadFromSystemProperty(final String packageName,
                                                      final ILogger logger)
    {
        return readSystemProperty(
                ADTRACE_PREINSTALL_SYSTEM_PROPERTY_PREFIX + packageName, logger);
    }

    public static String getPayloadFromSystemPropertyReflection(final String packageName,
                                                                final ILogger logger)
    {
        return readSystemPropertyReflection(
                ADTRACE_PREINSTALL_SYSTEM_PROPERTY_PREFIX + packageName, logger);
    }


    public static String getPayloadFromSystemPropertyFilePath(final String packageName,
                                                              final ILogger logger)
    {
        String filePath = readSystemProperty(ADTRACE_PREINSTALL_SYSTEM_PROPERTY_PATH, logger);
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        String content = readFileContent(filePath, logger);
        if (content == null || content.isEmpty()) {
            return null;
        }

        return readPayloadFromJsonString(content, packageName, logger);
    }

    public static String getPayloadFromSystemPropertyFilePathReflection(final String packageName,
                                                                        final ILogger logger)
    {
        String filePath = readSystemPropertyReflection(ADTRACE_PREINSTALL_SYSTEM_PROPERTY_PATH, logger);

        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        String content = readFileContent(filePath, logger);
        if (content == null || content.isEmpty()) {
            return null;
        }

        return readPayloadFromJsonString(content, packageName, logger);
    }


    public static String getPayloadFromContentProviderDefault(final Context context,
                                                              final String packageName,
                                                              final ILogger logger)
    {
        if (!Util.resolveContentProvider(context, ADTRACE_PREINSTALL_CONTENT_URI_AUTHORITY)) {
            return null;
        }

        String defaultContentUri = Util.formatString("content://%s/%s",
                ADTRACE_PREINSTALL_CONTENT_URI_AUTHORITY, ADTRACE_PREINSTALL_CONTENT_URI_PATH);
        return readContentProvider(context, defaultContentUri, packageName, logger);
    }

    public static List<String> getPayloadsFromContentProviderIntentAction(
            final Context context,
            final String packageName,
            final ILogger logger)
    {
        return readContentProviderIntentAction(context,
                                               packageName,
                                               Manifest.permission.INSTALL_PACKAGES,
                                               logger);
    }

    public static List<String> getPayloadsFromContentProviderNoPermission(
            final Context context,
            final String packageName,
            final ILogger logger)
    {
        return readContentProviderIntentAction(context,
                                               packageName,
                                               null,// no permission
                                               logger);
    }

    public static String getPayloadFromFileSystem(final String packageName,
                                                  final String filePath,
                                                  final ILogger logger)
    {
        String content = readFileContent(ADTRACE_PREINSTALL_FILE_SYSTEM_PATH, logger);

        if (content == null || content.isEmpty()) {
            if (filePath != null && !filePath.isEmpty()) {
                content = readFileContent(filePath, logger);
            }

            if (content == null || content.isEmpty()) {
                return null;
            }
        }

        return readPayloadFromJsonString(content, packageName, logger);
    }

    private static String readSystemProperty(final String propertyKey,
                                             final ILogger logger) {
        try {
            return System.getProperty(propertyKey);
        } catch (Exception e) {
            logger.error("Exception read system property key [%s] error [%s]", propertyKey, e.getMessage());
        }
        return null;
    }

    @SuppressLint("PrivateApi")
    private static String readSystemPropertyReflection(final String propertyKey,
                                                       final ILogger logger) {
        try {
            Class<?> classObject = Class.forName("android.os.SystemProperties");
            Method methodObject = classObject.getDeclaredMethod("get", String.class);
            return (String) methodObject.invoke(classObject, propertyKey);
        } catch (Exception e) {
            logger.error("Exception read system property using reflection key [%s] error [%s]", propertyKey, e.getMessage());
        }
        return null;
    }

    private static String readContentProvider(final Context context,
                                              final String contentUri,
                                              final String packageName,
                                              final ILogger logger)
    {
        try {
            final ContentResolver contentResolver = context.getContentResolver();
            Uri uri = Uri.parse(contentUri);
            final String encryptedDataColunn = "encrypted_data";
            final String[] projection = {encryptedDataColunn};
            String selection = "package_name=?";
            String[] selectionArgs = {packageName};
            final Cursor cursor = contentResolver.query(uri, projection,
                    selection, selectionArgs, null);

            if (cursor == null) {
                logger.debug("Read content provider cursor null content uri [%s]", contentUri);
                return null;
            }
            if (!cursor.moveToFirst()) {
                logger.debug("Read content provider cursor empty content uri [%s]", contentUri);
                cursor.close();
                return null;
            }

            String payload = cursor.getString(0);

            cursor.close();
            return payload;
        } catch (Exception e) {
            logger.error("Exception read content provider uri [%s] error [%s]", contentUri, e.getMessage());
            return null;
        }
    }

    private static List<String> readContentProviderIntentAction(final Context context,
                                                                final String packageName,
                                                                final String permission,
                                                                final ILogger logger)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return null;
        }

        List<ResolveInfo> providers;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            providers = context.getPackageManager()
              .queryIntentContentProviders(
                new Intent(ADTRACE_PREINSTALL_CONTENT_PROVIDER_INTENT_ACTION),
                PackageManager.ResolveInfoFlags.of(0));
        } else {
            providers = context.getPackageManager()
              .queryIntentContentProviders(
                new Intent(ADTRACE_PREINSTALL_CONTENT_PROVIDER_INTENT_ACTION), 0);
        }

        List<String> payloads = new ArrayList<String>();
        for (ResolveInfo provider : providers) {
            boolean permissionGranted = true;
            if (permission != null) {
                int result = context.getPackageManager().checkPermission(
                        permission, provider.providerInfo.packageName);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = false;
                }
            }

                if (permissionGranted) {
                    String authority = provider.providerInfo.authority;
                    if (authority != null && !authority.isEmpty()) {
                        String contentUri = Util.formatString("content://%s/%s",
                                                              authority, ADTRACE_PREINSTALL_CONTENT_URI_PATH);
                        String payload = readContentProvider(context, contentUri, packageName, logger);
                        if (payload != null && !payload.isEmpty()) {
                            payloads.add(payload);
                        }
                    }
                }
            }

        if (!payloads.isEmpty()) {
            return payloads;
        }

        return null;
    }

    private static String readFileContent(final String filePath, final ILogger logger) {
        File file = new File(filePath);
        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                int length = (int) file.length();

                if (length <= 0) {
                    logger.debug("Read file content empty file");
                    return null;
                }

                byte[] bytes = new byte[length];

                FileInputStream in = new FileInputStream(file);
                try {
                    in.read(bytes);
                } catch (Exception e) {
                    logger.error("Exception read file input stream error [%s]", e.getMessage());
                    return null;
                } finally {
                    in.close();
                }

                return new String(bytes);

            } catch (Exception e) {
                logger.error("Exception read file content error [%s]", e.getMessage());
            }
        }
        return null;
    }

    private static String readPayloadFromJsonString(final String jsonString,
                                                    final String packageName,
                                                    final ILogger logger) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString.trim());
            return jsonObject.optString(packageName);
        } catch (Exception e) {
            logger.error("Exception read payload from json string error [%s]", e.getMessage());
        }
        return null;
    }
}