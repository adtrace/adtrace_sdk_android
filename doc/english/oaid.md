## OAID plugin

OAID is an advertising ID that the MSA (Mobile Security Alliance) announced all Chinese-manufactured devices should provide. You can use it to attribute and track Android devices in many markets where Google Play Services is not available.

The OAID plugin enables the AdTrace Android SDK to read a device’s OAID value _in addition_ to the other device IDs it searches for by default. OAID is readable on all devices using the MSA SDK or HMS (Huawei Mobile Service) on Huawei devices.
Before getting started, make sure you have read the official [Android SDK README][readme] and successfully integrated the AdTrace SDK into your app.

To enable the AdTrace SDK to collect and track OAID, follow these steps. To only use the plugin to read the OAID of Huawei devices, you can skip the step [Add the MSA SDK to your app](#add-msa-sdk).

### Add the OAID plugin to your app

If you are using Maven, add the following OAID plugin dependency to your `build.gradle` file next to the existing AdTrace SDK dependency:

```groovy
implementation 'io.adtrace:android-sdk:2.5.1'
implementation 'io.adtrace:android-sdk-plugin-oaid:2.5.1'
```

You can also add the AdTrace OAID plugin as JAR file, which you can download from our [releases page][releases].

### <a id="add-msa-sdk"></a>Add the MSA SDK to your app

**Note:** It is not necessary to add the MSA SDK to read the OAID from Huawei devices. The OAID plugin can use the Huawei Mobile Service (version 2.6.2 or later) for this.

To enable the OAID plugin to read OAID values using the MSA SDK, copy the MSA SDK (AAR file) to the libs directory of your project and set the dependency. You also need to copy the supplierconfig.json to the assets directory of your project.

**Note**: MSA SDK is supported by AdTrace OAID plugin v2.0.1 and later.

You can find the MSA SDK and detailed instructions [here][msasdk].

### Proguard settings

If you’re using Proguard and will not publish your app in the Google Play Store, you can remove all of the rules related to Google Play Services and install referrer libraries in the [SDK README][readme proguard].

Use all `io.adtrace.sdk` package rules like this:

```
-keep public class io.adtrace.sdk.** { *; }
```

If you are adding the MSA SDK AAR as a dependency, then add the following rules:

```
-keep class com.bun.miitmdid.core.** { *; }
```

### Use the plugin

To read OAID values, call `AdTraceOaid.readOaid(applicationContext)` before starting the SDK:

```java
AdTraceOaid.readOaid(applicationContext);

// ...

AdTrace.onCreate(config);
```

To stop the SDK from reading OAID values, call `AdTraceOaid.doNotReadOaid()`.

[readme]: ../../../README.md
[releases]: https://github.com/adtrace/adtrace_sdk_android/releases
[readme proguard]: https://github.com/adtrace/adtrace_sdk_android/tree/beta#qs-proguard
[msasdk]: http://www.msa-alliance.cn/col.jsp?id=120
