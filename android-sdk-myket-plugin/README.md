# AdTrace Myket Install Referrer Plugin

Optional plugin that reads install referrer from [Myket](https://myket.ir) and sends it to AdTrace as an `sdk_click`, the same way Google Play / Samsung / Xiaomi / Vivo referrers work.

Reference: [myketstore/referrer-sample](https://github.com/myketstore/referrer-sample)

## Dependencies

```gradle
repositories {
    maven { url "https://maven.myket.ir" }
}

dependencies {
    implementation "io.adtrace:android-sdk:<version>"
    implementation "io.adtrace:android-sdk-plugin-myket:<version>"
}
```

The plugin depends on `ir.myket:referrersdk:1.0.3`. Manifest permission and package visibility for Myket are merged from that AAR.

## Usage

No extra code is required. When the plugin is on the classpath, AdTrace reads Myket referrer on first session and sends:

- `referrer`
- `click_time`
- `install_begin_time`
- `referrer_api=myket`

Optional:

```java
// Disable Myket referrer reads
AdTraceMyketReferrer.doNotReadMyketReferrer();

// Re-enable
AdTraceMyketReferrer.readMyketReferrer(context);
```

## ProGuard

Consumer rules are shipped with the plugin. If needed manually:

```
-keep interface com.myket.installreferrer.** { *; }
-keep class com.myket.installreferrer.** { *; }
-keep class ir.myket.referrersdk.** { *; }
```
