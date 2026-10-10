# AdTrace Cafe Bazaar Install Referrer Plugin

Optional plugin that reads install referrer from [Cafe Bazaar](https://cafebazaar.ir) and sends it to AdTrace as an `sdk_click`, the same way Google Play / Samsung / Xiaomi / Vivo / Myket referrers work.

Reference: [cafebazaar/ReferrerSDK](https://github.com/cafebazaar/ReferrerSDK)

## Dependencies

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "io.adtrace:android-sdk:<version>"
    implementation "io.adtrace:android-sdk-plugin-cafebazaar:<version>"
}
```

The plugin depends on `com.github.cafebazaar:referrersdk`. Manifest permission and package visibility for Cafe Bazaar are merged from that AAR.

## Usage

No extra code is required. When the plugin is on the classpath, AdTrace reads Cafe Bazaar referrer on first session and sends:

- `referrer`
- `click_time`
- `install_begin_time`
- `install_version` (when available)
- `referrer_api=cafebazaar`

Optional:

```java
// Disable Cafe Bazaar referrer reads
AdTraceCafeBazaarReferrer.doNotReadCafeBazaarReferrer();

// Re-enable
AdTraceCafeBazaarReferrer.readCafeBazaarReferrer(context);
```

## ProGuard

Consumer rules are shipped with the plugin. If needed manually:

```
-keep class com.cafebazaar.referrersdk.** { *; }
-keep interface com.cafebazaar.referrersdk.** { *; }
-keep class com.cafebazaar.servicebase.** { *; }
-keep interface com.cafebazaar.servicebase.** { *; }
-keep interface com.farsitel.bazaar.referrerprovider.** { *; }
-keep class com.farsitel.bazaar.referrerprovider.** { *; }
```
