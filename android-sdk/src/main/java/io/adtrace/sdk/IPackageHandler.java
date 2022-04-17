package io.adtrace.sdk;

import android.content.Context;

import io.adtrace.sdk.network.IActivityPackageSender;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */


public interface IPackageHandler {
    void init(IActivityHandler activityHandler,
              Context context,
              boolean startsSending,
              IActivityPackageSender packageHandlerActivityPackageSender);

    void addPackage(ActivityPackage activityPackage);

    void sendFirstPackage();

    void pauseSending();

    void resumeSending();

    void updatePackages(SessionParameters sessionParameters);

    void flush();

    void teardown();
}
