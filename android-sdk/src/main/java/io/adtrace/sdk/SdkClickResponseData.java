package io.adtrace.sdk;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class SdkClickResponseData extends ResponseData {
    boolean isInstallReferrer;
    long clickTime;
    long installBegin;
    String installReferrer;
    long clickTimeServer;
    long installBeginServer;
    String installVersion;
    Boolean googlePlayInstant;
    Boolean isClick;
    String referrerApi;
}
