package io.adtrace.sdk;

public class AdTracePurchase {
        private final String productId;
        private final String purchaseToken;

    public AdTracePurchase(final String productId, final String purchaseToken) {
        this.productId = productId;
        this.purchaseToken = purchaseToken;
    }

    String getProductId() {
        return productId;
    }

    String getPurchaseToken() {
        return purchaseToken;
    }
}
