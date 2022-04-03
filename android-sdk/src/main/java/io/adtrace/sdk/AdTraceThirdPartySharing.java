package io.adtrace.sdk;

import java.util.HashMap;
import java.util.Map;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public class AdTraceThirdPartySharing {
    Boolean isEnabled;
    Map<String, Map<String, String>> granularOptions;

    public AdTraceThirdPartySharing(final Boolean isEnabled) {
        this.isEnabled = isEnabled;
        granularOptions = new HashMap<>();
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
}
