package io.adtrace.sdk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class AdTraceAdRevenue {
    String source;
    Double revenue;
    String currency;
    Integer adImpressionsCount;
    String adRevenueNetwork;
    String adRevenueUnit;
    String adRevenuePlacement;
    Map<String, String> callbackParameters;
    Map<String, String> partnerParameters;

    private static final ILogger logger = AdTraceFactory.getLogger();

    public AdTraceAdRevenue(final String source) {
        if (!isValidSource(source)) {
            return;
        }

        this.source = source;
    }

    public void setRevenue(final Double revenue, final String currency) {
        this.revenue = revenue;
        this.currency = currency;
    }

    public void setAdImpressionsCount(final Integer adImpressionsCount) {
        this.adImpressionsCount = adImpressionsCount;
    }

    public void setAdRevenueNetwork(final String adRevenueNetwork) {
        this.adRevenueNetwork = adRevenueNetwork;
    }

    public void setAdRevenueUnit(final String adRevenueUnit) {
        this.adRevenueUnit = adRevenueUnit;
    }

    public void setAdRevenuePlacement(final String adRevenuePlacement) {
        this.adRevenuePlacement = adRevenuePlacement;
    }

    public void addCallbackParameter(final String key, final String value) {
        if (!Util.isValidParameter(key, "key", "Callback")) return;
        if (!Util.isValidParameter(value, "value", "Callback")) return;

        if (callbackParameters == null) {
            callbackParameters = new LinkedHashMap<String, String>();
        }

        String previousValue = callbackParameters.put(key, value);

        if (previousValue != null) {
            logger.warn("Key %s was overwritten", key);
        }
    }

    public void addPartnerParameter(String key, String value) {
        if (!Util.isValidParameter(key, "key", "Partner")) return;
        if (!Util.isValidParameter(value, "value", "Partner")) return;

        if (partnerParameters == null) {
            partnerParameters = new LinkedHashMap<String, String>();
        }

        String previousValue = partnerParameters.put(key, value);

        if (previousValue != null) {
            logger.warn("Key %s was overwritten", key);
        }
    }

    public boolean isValid() {
        return isValidSource(this.source);
    }

    public String getSource() {
        return source;
    }

    public Double getRevenue() {
        return revenue;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getAdImpressionsCount() {
        return adImpressionsCount;
    }

    public String getAdRevenueNetwork() {
        return adRevenueNetwork;
    }

    public String getAdRevenueUnit() {
        return adRevenueUnit;
    }

    public String getAdRevenuePlacement() {
        return adRevenuePlacement;
    }

    public Map<String, String> getCallbackParameters() {
        return callbackParameters;
    }

    public Map<String, String> getPartnerParameters() {
        return partnerParameters;
    }

    private boolean isValidSource(final String param) {
        if (param == null) {
            logger.error("Missing source");
            return false;
        }
        if (param.isEmpty()) {
            logger.error("Source can't be empty");
            return false;
        }
        return true;
    }
}
