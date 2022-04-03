function AdTraceThirdPartySharing(isEnabled) {
    this.isEnabled = isEnabled;
    this.granularOptions = [];
}

AdTraceThirdPartySharing.prototype.addGranularOption = function(partnerName, key, value) {
    this.granularOptions.push(partnerName);
    this.granularOptions.push(key);
    this.granularOptions.push(value);
};
