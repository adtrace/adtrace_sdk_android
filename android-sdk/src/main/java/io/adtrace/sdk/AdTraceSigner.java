package io.adtrace.sdk;

import android.content.Context;

import java.util.Map;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class AdTraceSigner {

    // https://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
    private static volatile Object signerInstance = null;

    private AdTraceSigner() {
    }

    public static void enableSigning(ILogger logger) {
        getSignerInstance();

        if (signerInstance == null) {
            return;
        }

        try {
            Reflection.invokeInstanceMethod(signerInstance, "enableSigning", null);
        } catch (Exception e) {
            logger.warn("Invoking Signer enableSigning() received an error [%s]", e.getMessage());
        }
    }

    public static void disableSigning(ILogger logger) {
        getSignerInstance();

        if (signerInstance == null) {
            return;
        }

        try {
            Reflection.invokeInstanceMethod(signerInstance, "disableSigning", null);
        } catch (Exception e) {
            logger.warn("Invoking Signer disableSigning() received an error [%s]", e.getMessage());
        }
    }

    public static void onResume(ILogger logger){
        getSignerInstance();

        if (signerInstance == null) {
            return;
        }

        try {
            Reflection.invokeInstanceMethod(signerInstance, "onResume", null);
        } catch (Exception e) {
            logger.warn("Invoking Signer onResume() received an error [%s]", e.getMessage());
        }
    }

    public static void sign(Map<String, String> parameters, String activityKind, String clientSdk,
                      Context context, ILogger logger) {
        getSignerInstance();

        if (signerInstance == null) {
            return;
        }

        try {
            Reflection.invokeInstanceMethod(signerInstance, "sign",
                            new Class[]{Context.class, Map.class, String.class, String.class},
                            context, parameters, activityKind, clientSdk);

        } catch (Exception e) {
            logger.warn("Invoking Signer sign() for %s received an error [%s]", activityKind, e.getMessage());
        }
    }

    private static void getSignerInstance() {
        if (signerInstance == null) {
            synchronized (AdTraceSigner.class) {
                if (signerInstance == null) {
                    signerInstance = Reflection.createDefaultInstance("io.adtrace.sdk.sig.Signer");
                }
            }
        }
    }
}
