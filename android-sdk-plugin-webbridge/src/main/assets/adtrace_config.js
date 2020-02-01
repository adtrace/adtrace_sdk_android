function AdTraceConfig(appToken, environment, legacy) {
    this.allowSuppressLogLevel = null;

    if (arguments.length === 2) {
        // new format does not require bridge as first parameter
        this.appToken = appToken;
        this.environment = environment;
    } else if (arguments.length === 3) {
        // new format with allowSuppressLogLevel
        if (typeof(legacy) == typeof(true)) {
            this.appToken = appToken;
            this.environment = environment;
            this.allowSuppressLogLevel = legacy;
        } else {
            // old format with first argument being the bridge instance
            this.bridge = appToken;
            this.appToken = environment;
            this.environment = legacy;
        }
    }

    this.eventBufferingEnabled = null;
    this.sendInBackground = null;
    this.enableInstalledApps = null;
    this.logLevel = null;
    this.sdkPrefix = null;
    this.processName = null;
    this.defaultTracker = null;
    this.externalDeviceId = null;
    this.attributionCallbackName = null;
    this.attributionCallbackFunction = null;
    this.deviceKnown = null;
    this.eventSuccessCallbackName = null;
    this.eventSuccessCallbackFunction = null;
    this.eventFailureCallbackName = null;
    this.eventFailureCallbackFunction = null;
    this.sessionSuccessCallbackName = null;
    this.sessionSuccessCallbackFunction = null;
    this.sessionFailureCallbackName = null;
    this.sessionFailureCallbackFunction = null;
    this.openDeferredDeeplink = null;
    this.deferredDeeplinkCallbackName = null;
    this.deferredDeeplinkCallbackFunction = null;
    this.delayStart = null;
    this.userAgent = null;
    this.secretId = null;
    this.info1 = null;
    this.info2 = null;
    this.info3 = null;
    this.info4 = null;
    this.fbPixelDefaultEventToken = null;
    this.fbPixelMapping = [];
}

AdTraceConfig.EnvironmentSandbox = 'sandbox';
AdTraceConfig.EnvironmentProduction = 'production';

AdTraceConfig.LogLevelVerbose = 'VERBOSE',
AdTraceConfig.LogLevelDebug = 'DEBUG',
AdTraceConfig.LogLevelInfo = 'INFO',
AdTraceConfig.LogLevelWarn = 'WARN',
AdTraceConfig.LogLevelError = 'ERROR',
AdTraceConfig.LogLevelAssert = 'ASSERT',
AdTraceConfig.LogLevelSuppress = 'SUPPRESS',

AdTraceConfig.prototype.getBridge = function() {
    return this.bridge;
};

AdTraceConfig.prototype.setEventBufferingEnabled = function(isEnabled) {
    this.eventBufferingEnabled = isEnabled;
};

AdTraceConfig.prototype.setSendInBackground = function(isEnabled) {
    this.sendInBackground = isEnabled;
};

AdTraceConfig.prototype.setEnableInstalledApps = function(enableInstalledApps) {
    this.enableInstalledApps = enableInstalledApps;
};

AdTraceConfig.prototype.setLogLevel = function(logLevel) {
    this.logLevel = logLevel;
};

AdTraceConfig.prototype.getSdkPrefix = function() {
    return this.sdkPrefix;
};

AdTraceConfig.prototype.setSdkPrefix = function(sdkPrefix) {
    this.sdkPrefix = sdkPrefix;
};

AdTraceConfig.prototype.setProcessName = function(processName) {
    this.processName = processName;
};

AdTraceConfig.prototype.setDefaultTracker = function(defaultTracker) {
    this.defaultTracker = defaultTracker;
};

AdTraceConfig.prototype.setExternalDeviceId = function(externalDeviceId) {
    this.externalDeviceId = externalDeviceId;
};

AdTraceConfig.prototype.setAttributionCallback = function(callback) {
    if (typeof callback === 'string' || callback instanceof String) {
        this.attributionCallbackName = callback;
    } else {
        this.attributionCallbackName = 'AdTrace.getConfig().adtrace_attributionCallback';
        this.attributionCallbackFunction = callback;
    }
};

AdTraceConfig.prototype.adtrace_attributionCallback = function(attribution) {
    if (this.attributionCallbackFunction) {
        this.attributionCallbackFunction(attribution);
    }
};

AdTraceConfig.prototype.setDeviceKnown = function(deviceKnown) {
    this.deviceKnown = deviceKnown;
};

AdTraceConfig.prototype.setEventSuccessCallback = function(callback) {
    if (typeof callback === 'string' || callback instanceof String) {
        this.eventSuccessCallbackName = callback;
    } else {
        this.eventSuccessCallbackName = 'AdTrace.getConfig().adtrace_eventSuccessCallback';
        this.eventSuccessCallbackFunction = callback;
    }
};

AdTraceConfig.prototype.adtrace_eventSuccessCallback = function(eventSuccess) {
    if (this.eventSuccessCallbackFunction) {
        this.eventSuccessCallbackFunction(eventSuccess);
    }
};

AdTraceConfig.prototype.setEventFailureCallback = function(callback) {
    if (typeof callback === 'string' || callback instanceof String) {
        this.eventFailureCallbackName = callback;
    } else {
        this.eventFailureCallbackName = 'AdTrace.getConfig().adtrace_eventFailureCallback';
        this.eventFailureCallbackFunction = callback;
    }
};

AdTraceConfig.prototype.adtrace_eventFailureCallback = function(eventFailure) {
    if (this.eventFailureCallbackFunction) {
        this.eventFailureCallbackFunction(eventFailure);
    }
};

AdTraceConfig.prototype.setSessionSuccessCallback = function(callback) {
    if (typeof callback === 'string' || callback instanceof String) {
        this.sessionSuccessCallbackName = callback;
    } else {
        this.sessionSuccessCallbackName = 'AdTrace.getConfig().adtrace_sessionSuccessCallback';
        this.sessionSuccessCallbackFunction = callback;
    }
};

AdTraceConfig.prototype.adtrace_sessionSuccessCallback = function(sessionSuccess) {
    if (this.sessionSuccessCallbackFunction) {
        this.sessionSuccessCallbackFunction(sessionSuccess);
    }
};

AdTraceConfig.prototype.setSessionFailureCallback = function(callback) {
    if (typeof callback === 'string' || callback instanceof String) {
        this.sessionFailureCallbackName = callback;
    } else {
        this.sessionFailureCallbackName = 'AdTrace.getConfig().adtrace_sessionFailureCallback';
        this.sessionFailureCallbackFunction = callback;
    }
};

AdTraceConfig.prototype.adtrace_sessionFailureCallback = function(sessionFailure) {
    if (this.sessionFailureCallbackFunction) {
        this.sessionFailureCallbackFunction(sessionFailure);
    }
};

AdTraceConfig.prototype.setOpenDeferredDeeplink = function(shouldOpen) {
    this.openDeferredDeeplink = shouldOpen;
};

AdTraceConfig.prototype.setDeferredDeeplinkCallback = function(callback) {
    if (typeof callback === 'string' || callback instanceof String) {
        this.deferredDeeplinkCallbackName = callback;
    } else {
        this.deferredDeeplinkCallbackName = 'AdTrace.getConfig().adtrace_deferredDeeplinkCallback';
        this.deferredDeeplinkCallbackFunction = callback;
    }
};

AdTraceConfig.prototype.adtrace_deferredDeeplinkCallback = function(deeplink) {
    if (this.deferredDeeplinkCallbackFunction) {
        this.deferredDeeplinkCallbackFunction(deeplink);
    }
};

AdTraceConfig.prototype.setDelayStart = function(delayStart) {
    this.delayStart = delayStart;
};

AdTraceConfig.prototype.setUserAgent = function(userAgent) {
    this.userAgent = userAgent;
};

AdTraceConfig.prototype.setAppSecret = function(secretId, info1, info2, info3, info4) {
    this.secretId = secretId;
    this.info1 = info1;
    this.info2 = info2;
    this.info3 = info3;
    this.info4 = info4;
};

AdTraceConfig.prototype.setReadMobileEquipmentIdentity = function(readMobileEquipmentIdentity) {};

AdTraceConfig.prototype.setFbPixelDefaultEventToken = function(fbPixelDefaultEventToken) {
    this.fbPixelDefaultEventToken = fbPixelDefaultEventToken;
};

AdTraceConfig.prototype.addFbPixelMapping = function(fbEventNameKey, adjEventTokenValue) {
    this.fbPixelMapping.push(fbEventNameKey);
    this.fbPixelMapping.push(adjEventTokenValue);
};