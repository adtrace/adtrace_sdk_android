## 2.5.0
- 
### Added:
- data residency support 
- Updated Samsung Install Referrer library version to 3.0.1.
- Updated IMEI reading attempt to only once.
- sending of App Set Identifier.
- support for purchase verification. In case you are using this feature, you can now use it by calling `verifyPurchase` method of the `AdTrace` instance.
- support for SigV3 library. Update authorization header building logic to use `adt_signing_id`.
- `setFinalAttributionEnabled(boolean)` method to `AdTraceConfig` to indicate if only final attribution is needed in attribution callback (by default attribution callback return intermediate attribution as well before final attribution if not enabled with this setter method).
- sending of event_callback_id parameter (if set) with the event payload.
- Updated Gradle version 8.1.1 and publishing plugins as aar.
- support for Meta install referrer.
- support for Google Play Games on PC.
- Getters for certain public classes.
- `setReadDeviceInfoOnceEnabled(boolean)` method to `AdTraceConfig` to indicate if device info to be read only once.

