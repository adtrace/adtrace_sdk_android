# AdTrace Java Integration Example

Minimal, runnable example of integrating the **AdTrace Android SDK** in a Java app.

Use this folder as a reference when adding AdTrace to your own project. Each integration step maps to a specific file below.

> Looking for Kotlin? See [`example-app-kotlin/README.md`](../example-app-kotlin/README.md).

## Integration checklist

| Step | What to do | File in this example |
|------|------------|----------------------|
| 1 | Add SDK dependencies | [`build.gradle`](build.gradle) |
| 2 | Declare permissions, Application class, and install referrer receiver | [`AndroidManifest.xml`](src/main/AndroidManifest.xml) |
| 3 | Set your app token and environment | [`AdTraceConstants.java`](src/main/java/io/adtrace/examples/AdTraceConstants.java) |
| 4 | Initialize the SDK on app start | [`GlobalApplication.java`](src/main/java/io/adtrace/examples/GlobalApplication.java) |
| 5 | Track sessions via activity lifecycle | [`AdTraceLifecycleCallbacks.java`](src/main/java/io/adtrace/examples/AdTraceLifecycleCallbacks.java) |
| 6 | Track events and handle deep links | [`MainActivity.java`](src/main/java/io/adtrace/examples/MainActivity.java) |
| 7 | *(Optional)* Callbacks, offline mode, COPPA, etc. | [`AdTraceAdvancedConfig.java`](src/main/java/io/adtrace/examples/AdTraceAdvancedConfig.java) |

## Step 1 — Dependencies

In your app module `build.gradle`:

```java
dependencies {
    // AdTrace SDK (use your release version in production)
    implementation 'io.adtrace:android-sdk:2.6.0'

    // Required for Google Play install referrer
    implementation 'com.android.installreferrer:installreferrer:2.2'

    // Required for Google Advertising ID
    implementation 'com.google.android.gms:play-services-ads-identifier:18.0.0'
}
```

When working inside **this repository**, the example uses `implementation project(':android-sdk')` instead of Maven.

## Step 2 — AndroidManifest.xml

```xml
<!-- Required permissions -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />

<application
    android:name=".GlobalApplication"
    ...>

    <!-- Install referrer receiver (Google Play Store intent) -->
    <receiver
        android:name="io.adtrace.sdk.AdTraceReferrerReceiver"
        android:exported="true">
        <intent-filter>
            <action android:name="com.android.vending.INSTALL_REFERRER" />
        </intent-filter>
    </receiver>

    <!-- Your activities ... -->
</application>
```

See [`AndroidManifest.xml`](src/main/AndroidManifest.xml) for deep-link intent filters and optional components.

## Step 3 — App token

Replace the placeholder in [`AdTraceConstants.java`](src/main/java/io/adtrace/examples/AdTraceConstants.java):

```java
public static final String APP_TOKEN = "{YourAppToken}";  // from AdTrace dashboard
public static final String ENVIRONMENT = AdTraceConfig.ENVIRONMENT_SANDBOX;  // use PRODUCTION in release builds
```

## Step 4 — Initialize the SDK

Create an `Application` subclass and call `AdTrace.onCreate()` in `onCreate()`:

```java
public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AdTraceConfig config = new AdTraceConfig(
                this,
                AdTraceConstants.APP_TOKEN,
                AdTraceConstants.ENVIRONMENT);

        // Optional: callbacks, log level, etc.
        AdTraceAdvancedConfig.apply(config);

        AdTrace.onCreate(config);
        registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
    }
}
```

Full implementation: [`GlobalApplication.java`](src/main/java/io/adtrace/examples/GlobalApplication.java)

## Step 5 — Session tracking

Register lifecycle callbacks so the SDK knows when the app is in the foreground:

```java
registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
```

Implementation: [`AdTraceLifecycleCallbacks.java`](src/main/java/io/adtrace/examples/AdTraceLifecycleCallbacks.java)

> **Note:** If `minSdkVersion` is below 14, call `AdTrace.onResume()` / `AdTrace.onPause()` manually in every Activity instead.

## Step 6 — Track events

```java
AdTraceEvent event = new AdTraceEvent("your_event_token");
event.setRevenue(52000.0, "IRR");           // optional
event.addCallbackParameter("key", "value"); // optional
AdTrace.trackEvent(event);
```

Examples: [`MainActivity.java`](src/main/java/io/adtrace/examples/MainActivity.java)

### Deep links

Call `AdTrace.appWillOpenUrl(uri, context)` when your activity receives a deep link (in `onCreate` and `onNewIntent`).

## Run this example

From the repository root:

```bash
./gradlew :example-app-java:installDebug
```

Open the app, then filter Logcat by tag `AdTrace` or `AdTraceExample`.

### In-app SDK log panel

This example mirrors AdTrace SDK logs inside the app UI (bottom panel on the main screen) — the same request payloads you would see in Logcat with tag `AdTrace`:

```
Path:      /event
ClientSdk: android2.6.0
Parameters:
     app_token        ...
     event_token      ...
```

Implementation (example-only, not needed in production apps):

1. [`InAppLogger.java`](src/main/java/io/adtrace/examples/InAppLogger.java) — implements `ILogger` and forwards to Logcat + [`AdTraceLogStore`](src/main/java/io/adtrace/examples/AdTraceLogStore.java)
2. Register it **before** `AdTraceConfig` in [`GlobalApplication.java`](src/main/java/io/adtrace/examples/GlobalApplication.java):

```java
AdTraceFactory.setLogger(new InAppLogger());
```

3. Keep log level at `VERBOSE` (set in [`AdTraceAdvancedConfig.java`](src/main/java/io/adtrace/examples/AdTraceAdvancedConfig.java)) to see full request parameters.

## Project structure

```
example-app-java/
├── README.md                          ← You are here
├── build.gradle                       ← Step 1: dependencies
└── src/main/
    ├── AndroidManifest.xml            ← Step 2: permissions & components
    └── java/io/adtrace/examples/
        ├── AdTraceConstants.java      ← Step 3: app token & event tokens
        ├── GlobalApplication.java     ← Step 4: SDK initialization
        ├── AdTraceLifecycleCallbacks.java ← Step 5: session lifecycle
        ├── AdTraceAdvancedConfig.java ← Step 7: optional SDK settings
        ├── InAppLogger.java           ← Mirrors SDK logs to the in-app panel
        ├── AdTraceLogStore.java       ← In-memory log buffer for the UI
        ├── MainActivity.java          ← Step 6: events, deep links, log panel
        ├── ServiceActivity.java       ← Background service demo
        └── ServiceExample.java        ← Event tracking from a Service
```

## Full SDK documentation

Integration details for all features (ProGuard, OAID plugins, uninstall tracking, etc.) are in the [root README](../README.md).
