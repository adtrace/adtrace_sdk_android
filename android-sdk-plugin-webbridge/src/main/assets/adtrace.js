var AdTrace = {
    onCreate: function (adtraceConfig) {
        if (adtraceConfig && !adtraceConfig.getSdkPrefix()) {
            adtraceConfig.setSdkPrefix(this.getSdkPrefix());
        }
        this.adtraceConfig = adtraceConfig;
        if (AdTraceBridge) {
            AdTraceBridge.onCreate(JSON.stringify(adtraceConfig));
        }
    },

    getConfig: function () {
        return this.adtraceConfig;
    },

    trackEvent: function (adtraceEvent) {
        if (AdTraceBridge) {
            AdTraceBridge.trackEvent(JSON.stringify(adtraceEvent));
        }
    },

    trackAdRevenue: function(source, payload) {
        if (AdTraceBridge) {
            AdTraceBridge.trackAdRevenue(source, payload);
        }
    },

    onResume: function () {
        if (AdTraceBridge) {
            AdTraceBridge.onResume();
        }
    },

    onPause: function () {
        if (AdTraceBridge) {
            AdTraceBridge.onPause();
        }
    },

    setEnabled: function (enabled) {
        if (AdTraceBridge) {
            AdTraceBridge.setEnabled(enabled);
        }
    },

    isEnabled: function (callback) {
        if (!AdTraceBridge) {
            return undefined;
        }
        // supports legacy return with callback
        if (arguments.length === 1) {
            // with manual string call
            if (typeof callback === 'string' || callback instanceof String) {
                this.isEnabledCallbackName = callback;
            } else {
                // or save callback and call later
                this.isEnabledCallbackName = 'AdTrace.adtrace_isEnabledCallback';
                this.isEnabledCallbackFunction = callback;
            }
            AdTraceBridge.isEnabled(this.isEnabledCallbackName);
        } else {
            return AdTraceBridge.isEnabled();
        }
    },

    adtrace_isEnabledCallback: function (isEnabled) {
        if (AdTraceBridge && this.isEnabledCallbackFunction) {
            this.isEnabledCallbackFunction(isEnabled);
        }
    },

    appWillOpenUrl: function (url) {
        if (AdTraceBridge) {
            AdTraceBridge.appWillOpenUrl(url);
        }
    },

    setReferrer: function (referrer) {
        if (AdTraceBridge) {
            AdTraceBridge.setReferrer(referrer);
        }
    },

    setOfflineMode: function(isOffline) {
        if (AdTraceBridge) {
            AdTraceBridge.setOfflineMode(isOffline);
        }
    },

    sendFirstPackages: function() {
        if (AdTraceBridge) {
            AdTraceBridge.sendFirstPackages();
        }
    },

    addSessionCallbackParameter: function(key, value) {
        if (AdTraceBridge) {
            AdTraceBridge.addSessionCallbackParameter(key, value);
        }
    },

    addSessionPartnerParameter: function(key, value) {
        if (AdTraceBridge) {
            AdTraceBridge.addSessionPartnerParameter(key, value);
        }
    },

    removeSessionCallbackParameter: function(key) {
        if (AdTraceBridge) {
            AdTraceBridge.removeSessionCallbackParameter(key);
        }
    },

    removeSessionPartnerParameter: function(key) {
        if (AdTraceBridge) {
            AdTraceBridge.removeSessionPartnerParameter(key);
        }
    },

    resetSessionCallbackParameters: function() {
        if (AdTraceBridge) {
            AdTraceBridge.resetSessionCallbackParameters();
        }
    },

    resetSessionPartnerParameters: function() {
        if (AdTraceBridge) {
            AdTraceBridge.resetSessionPartnerParameters();
        }
    },

    setPushToken: function(token) {
        if (AdTraceBridge) {
            AdTraceBridge.setPushToken(token);
        }
    },

    gdprForgetMe: function() {
        if (AdTraceBridge) {
            AdTraceBridge.gdprForgetMe();
        }
    },

    disableThirdPartySharing: function() {
        if (AdTraceBridge) {
            AdTraceBridge.disableThirdPartySharing();
        }
    },

    trackThirdPartySharing: function(adtraceThirdPartySharing) {
        if (AdTraceBridge) {
            AdTraceBridge.trackThirdPartySharing(JSON.stringify(adtraceThirdPartySharing));
        }
    },

    trackMeasurementConsent: function(consentMeasurement) {
        if (AdTraceBridge) {
            AdTraceBridge.trackMeasurementConsent(consentMeasurement);
        }
    },

    getGoogleAdId: function (callback) {
        if (AdTraceBridge) {
            if (typeof callback === 'string' || callback instanceof String) {
                this.getGoogleAdIdCallbackName = callback;
            } else {
                this.getGoogleAdIdCallbackName = 'AdTrace.adtrace_getGoogleAdIdCallback';
                this.getGoogleAdIdCallbackFunction = callback;
            }
            AdTraceBridge.getGoogleAdId(this.getGoogleAdIdCallbackName);
        }
    },

    adtrace_getGoogleAdIdCallback: function (googleAdId) {
        if (AdTraceBridge && this.getGoogleAdIdCallbackFunction) {
            this.getGoogleAdIdCallbackFunction(googleAdId);
        }
    },

    getAmazonAdId: function (callback) {
        if (AdTraceBridge) {
            return AdTraceBridge.getAmazonAdId();
        } else {
            return undefined;
        }
    },

    getAdid: function () {
        if (AdTraceBridge) {
            return AdTraceBridge.getAdid();
        } else {
            return undefined;
        }
    },

    getAttribution: function (callback) {
        if (AdTraceBridge) {
            AdTraceBridge.getAttribution(callback);
        }
    },

    getSdkVersion: function () {
        if (AdTraceBridge) {
             return this.getSdkPrefix() + '@' + AdTraceBridge.getSdkVersion();
        } else {
            return undefined;
        }
    },

    getSdkPrefix: function () {
        if (this.adtraceConfig) {
            return this.adtraceConfig.getSdkPrefix();
        } else {
            return 'web-bridge2.4.1';
        }
    },

    teardown: function() {
        if (AdTraceBridge) {
            AdTraceBridge.teardown();
        }
        this.adtraceConfig = undefined;
        this.isEnabledCallbackName = undefined;
        this.isEnabledCallbackFunction = undefined;
        this.getGoogleAdIdCallbackName = undefined;
        this.getGoogleAdIdCallbackFunction = undefined;
    },
};
