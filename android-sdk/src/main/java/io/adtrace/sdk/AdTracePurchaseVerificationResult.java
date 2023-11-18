package io.adtrace.sdk;

public class AdTracePurchaseVerificationResult {
    private final String verificationStatus;
    private final int code;
    private final String message;

    public AdTracePurchaseVerificationResult(final String verificationStatus,
                                             final int code,
                                             final String message) {
        this.verificationStatus = verificationStatus;
        this.code = code;
        this.message = message;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
