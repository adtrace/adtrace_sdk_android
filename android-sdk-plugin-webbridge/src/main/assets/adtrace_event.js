function AdTraceEvent(eventToken) {
    this.eventToken = eventToken;
    this.revenue = null;
    this.currency = null;
    this.callbackParameters = [];
    this.eventParameters = [];
    this.orderId = null;
    this.callbackId = null;
}

AdTraceEvent.prototype.setRevenue = function(revenue, currency) {
    this.revenue = revenue;
    this.currency = currency;
};

AdTraceEvent.prototype.addCallbackParameter = function(key, value) {
    this.callbackParameters.push(key);
    this.callbackParameters.push(value);
};

AdTraceEvent.prototype.addEventParameter = function(key, value) {
    this.eventParameters.push(key);
    this.eventParameters.push(value);
};

AdTraceEvent.prototype.setOrderId = function(orderId) {
    this.orderId = orderId;
};

AdTraceEvent.prototype.setCallbackId = function(callbackId) {
    this.callbackId = callbackId;
};
