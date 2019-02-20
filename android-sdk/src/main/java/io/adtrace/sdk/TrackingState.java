package io.adtrace.sdk;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
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
