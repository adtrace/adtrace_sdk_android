function AdTraceEvent(eventToken) {
    this.eventToken = eventToken;
    this.revenue = null;
    this.currency = null;
    this.callbackParameters = [];
    this.partnerParameters = [];
    this.orderId = null;
    this.callbackId = null;
    this.eventValue = null;
}

AdTraceEvent.prototype.setRevenue = function(revenue, currency) {
    this.revenue = revenue;
    this.currency = currency;
};

AdTraceEvent.prototype.addCallbackParameter = function(key, value) {
    this.callbackParameters.push(key);
    this.callbackParameters.push(value);
};

AdTraceEvent.prototype.addPartnerParameter = function(key, value) {
    this.partnerParameters.push(key);
    this.partnerParameters.push(value);
};

AdTraceEvent.prototype.setOrderId = function(orderId) {
    this.orderId = orderId;
};

AdTraceEvent.prototype.setCallbackId = function(callbackId) {
    this.callbackId = callbackId;
};

AdTraceEvent.prototype.setEventValue = function(eventValue) {
    this.eventValue = eventValue;
};