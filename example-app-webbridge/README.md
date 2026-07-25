# AdTrace WebBridge Integration Example

Minimal, runnable example of using the **AdTrace Android SDK inside a WebView** via the JavaScript bridge plugin.

Use this folder as a reference when embedding AdTrace in HTML/JS loaded by a WebView. Each integration step maps to a specific file below.

> Looking for native Kotlin / Java? See [`example-app-kotlin`](../example-app-kotlin/README.md) and [`example-app-java`](../example-app-java/README.md).

## How this example is structured

```
MainActivity (native launcher)
    └─ "Show WebView" button
         └─ WebViewActivity
              ├─ AdTraceBridge.registerAndGetInstance(...)   ← native side
              └─ loads AdTraceExample-WebView.html
                   ├─ adtrace_constants.js                   ← tokens
                   ├─ adtrace.js / adtrace_config.js / ...   ← from webbridge plugin
                   └─ AdTrace.onCreate / trackEvent          ← JS side
```

## Integration checklist

| Step | What to do | File in this example |
|------|------------|----------------------|
| 1 | Add core SDK + webbridge plugin dependencies | [`build.gradle`](build.gradle) |
| 2 | Declare permissions and activities | [`AndroidManifest.xml`](src/main/AndroidManifest.xml) |
| 3 | Open a WebView and register `AdTraceBridge` | [`WebViewActivity.java`](src/main/java/io/adtrace/examples/WebViewActivity.java) |
| 4 | Include AdTrace JS files in your HTML | [`AdTraceExample-WebView.html`](src/main/assets/AdTraceExample-WebView.html) |
| 5 | Set your app token and environment | [`adtrace_constants.js`](src/main/assets/adtrace_constants.js) |
| 6 | Initialize the SDK and track events in JavaScript | [`AdTraceExample-WebView.html`](src/main/assets/AdTraceExample-WebView.html) |

[`MainActivity.java`](src/main/java/io/adtrace/examples/MainActivity.java) is only a native entry screen (same pattern as the other example apps). It is **not** required for WebBridge integration.

## Step 1 — Dependencies

In your app module `build.gradle`:

```java
dependencies {
    implementation 'io.adtrace:android-sdk:2.6.0'
    implementation 'io.adtrace:android-sdk-plugin-webbridge:2.6.0'

    implementation 'com.android.installreferrer:installreferrer:2.2'
    implementation 'com.google.android.gms:play-services-ads-identifier:18.0.0'
}
```

When working inside **this repository**, the example uses:

```java
implementation project(':android-sdk')
implementation project(':android-sdk-plugin-webbridge')
```

**Note:** WebBridge requires **minSdkVersion 17+**.

The plugin ships these JS assets (merged into your APK automatically):

- `adtrace.js`
- `adtrace_config.js`
- `adtrace_event.js`
- `adtrace_third_party_sharing.js`

## Step 2 — AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />

<application ...>
    <!-- Your activity that hosts the WebView -->
    <activity android:name=".WebViewActivity" ... />
</application>
```

See [`AndroidManifest.xml`](src/main/AndroidManifest.xml) for the full example.

## Step 3 — Register the native bridge

In the Activity that owns your `WebView`:

```java
WebView webView = findViewById(R.id.webView);
webView.getSettings().setJavaScriptEnabled(true);
webView.setWebChromeClient(new WebChromeClient());
webView.setWebViewClient(new WebViewClient());

// Connect native SDK ↔ JavaScript
AdTraceBridge.registerAndGetInstance(getApplication(), webView);

webView.loadUrl("file:///android_asset/AdTraceExample-WebView.html");
// or: webView.loadUrl("https://your.cdn.com/page.html");
```

Unregister when the activity is destroyed:

```java
@Override
protected void onDestroy() {
    AdTraceBridge.unregister();
    super.onDestroy();
}
```

Full implementation: [`WebViewActivity.java`](src/main/java/io/adtrace/examples/WebViewActivity.java)

## Step 4 — Include JS files in HTML

```html
<script type="text/javascript" src="adtrace_event.js"></script>
<script type="text/javascript" src="adtrace_third_party_sharing.js"></script>
<script type="text/javascript" src="adtrace_config.js"></script>
<script type="text/javascript" src="adtrace.js"></script>
<script type="text/javascript" src="adtrace_constants.js"></script>
```

If your HTML is hosted remotely, copy the JS files from the webbridge plugin assets next to your page (or serve them from the same origin).

## Step 5 — App token

Edit [`adtrace_constants.js`](src/main/assets/adtrace_constants.js):

```javascript
var AdTraceExampleConstants = {
    APP_TOKEN: '{YourAppToken}',
    ENVIRONMENT: AdTraceConfig.EnvironmentSandbox,
    EVENT_TOKEN_SIMPLE: 'p6j3p7',
    // ...
};
```

## Step 6 — Initialize and track events (JavaScript)

```javascript
var config = new AdTraceConfig(
    AdTraceExampleConstants.APP_TOKEN,
    AdTraceExampleConstants.ENVIRONMENT
);
config.setLogLevel(AdTraceConfig.LogLevelVerbose);
AdTrace.onCreate(config);

// Track an event
var event = new AdTraceEvent(AdTraceExampleConstants.EVENT_TOKEN_SIMPLE);
AdTrace.trackEvent(event);
```

Full demo page: [`AdTraceExample-WebView.html`](src/main/assets/AdTraceExample-WebView.html)

## Run this example

```bash
./gradlew :example-app-webbridge:installDebug
```

1. App opens on the native launcher (`MainActivity`)
2. Tap **Show WebView**
3. Use the HTML buttons to track events / toggle SDK state
4. Filter Logcat by tag `AdTrace`

## Project structure

```
example-app-webbridge/
├── README.md                          ← You are here
├── build.gradle                       ← Step 1: dependencies
└── src/main/
    ├── AndroidManifest.xml            ← Step 2: permissions & activities
    ├── java/io/adtrace/examples/
    │   ├── MainActivity.java          ← Native launcher (Show WebView)
    │   └── WebViewActivity.java       ← Step 3: AdTraceBridge + WebView
    ├── res/layout/
    │   ├── activity_main.xml          ← Launcher UI
    │   └── activity_webview.xml       ← Full-screen WebView
    └── assets/
        ├── adtrace_constants.js       ← Step 5: app & event tokens
        └── AdTraceExample-WebView.html ← Steps 4 & 6: JS init + events
```

## Tokens (same as Java / Kotlin examples)

| Constant | Token |
|----------|-------|
| `APP_TOKEN` | `humkdip1g0yl` |
| `EVENT_TOKEN_SIMPLE` | `p6j3p7` |
| `EVENT_TOKEN_REVENUE` | `ijdu0k` |
| `EVENT_TOKEN_CALLBACK` | `0issyz` |
| `EVENT_TOKEN_PARTNER` | `905cgb` |
| `EVENT_TOKEN_PARAMS` | `llo5i9` |

## Full SDK documentation

Native integration details (ProGuard, OAID, uninstall tracking, etc.) are in the [root README](../README.md).
