package io.adtrace.sdk;

import java.util.HashMap;
import java.util.Map;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class AdTraceThirdPartySharing {
    Boolean isEnabled;
    Map<String, Map<String, String>> granularOptions;
    Map<String, Map<String, Boolean>> partnerSharingSettings;

    public AdTraceThirdPartySharing(final Boolean isEnabled) {
        this.isEnabled = isEnabled;
        granularOptions = new HashMap<>();
        partnerSharingSettings = new HashMap<>();
    }

    public void addGranularOption(final String partnerName,
                                  final String key,
                                  final String value)
    {
        if (partnerName == null || key == null || value == null) {
            ILogger logger = AdTraceFactory.getLogger();
            logger.error("Cannot add granular option with any null value");
            return;
        }

        Map<String, String> partnerOptions = granularOptions.get(partnerName);
        if (partnerOptions == null) {
            partnerOptions = new HashMap<>();
            granularOptions.put(partnerName, partnerOptions);
        }

        partnerOptions.put(key, value);
    }

    public void addPartnerSharingSetting(final String partnerName,
                                         final String key,
                                         final boolean value)
    {
        if (partnerName == null || key == null) {
            ILogger logger = AdTraceFactory.getLogger();
            logger.error("Cannot add partner sharing setting with any null value");
            return;
        }

        Map<String, Boolean> partnerSharingSetting = this.partnerSharingSettings.get(partnerName);
        if (partnerSharingSetting == null) {
            partnerSharingSetting = new HashMap<>();
            partnerSharingSettings.put(partnerName, partnerSharingSetting);
        }

        partnerSharingSetting.put(key, value);
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public Map<String, Map<String, String>> getGranularOptions() {
        return granularOptions;
    }

    public Map<String, Map<String, Boolean>> getPartnerSharingSettings() {
        return partnerSharingSettings;
    }
}
