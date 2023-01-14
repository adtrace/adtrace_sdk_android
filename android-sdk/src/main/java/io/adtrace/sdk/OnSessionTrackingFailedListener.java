package io.adtrace.sdk;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public interface OnSessionTrackingFailedListener {
    void onFinishedSessionTrackingFailed(AdTraceSessionFailure failureResponseData);
}
