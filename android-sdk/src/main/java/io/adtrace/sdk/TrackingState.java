package io.adtrace.sdk;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public enum TrackingState {
    OPTED_OUT(1);

    private int value;

    TrackingState(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
