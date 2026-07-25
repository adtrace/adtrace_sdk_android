> **Full technical guide (two cases):** [TECHNICAL.md](TECHNICAL.md)
> - **Case A** — WebBridge-only (no native events; JS calls `AdTrace.onCreate`)
> - **Case B** — Hybrid (native events + Web Bridge)

**This sample’s launcher has both buttons.** Pick one per app process; force-stop to switch.

Looking for native Kotlin / Java? See [`example-app-kotlin`](../example-app-kotlin/README.md) and [`example-app-java`](../example-app-java/README.md).

## How to demo both scenarios

| Button on home screen | What happens |
|----------------------|--------------|
| **Open Case A (JS onCreate)** | Opens WebView only. HTML calls `AdTrace.onCreate`, then you track from JS. |
| **Open Case B (native event + WebView)** | Native `AdTrace.onCreate` + native simple event, then WebView. HTML tracks without `onCreate`. |

```
MainActivity
  ├─ Case A ─► WebViewActivity ─► AdTraceExample-CaseA-WebBridgeOnly.html
  │                                  HTML: AdTrace.onCreate (SDK init) + trackEvent
  │
  └─ Case B ─► native onCreate + trackEvent ─► WebViewActivity
                                               ─► AdTraceExample-CaseB-Hybrid.html
                                                  HTML: trackEvent only (no onCreate)
```

**Constraint:** the SDK initializes once per process. After you open Case A or B, the other button asks you to force-stop and relaunch.

## How this example is structured

```
MainActivity          ← choose Case A or Case B
AdTraceDemoSession    ← locks mode + lazy native init for Case B
WebViewActivity       ← AdTraceBridge + loads the matching HTML file
assets/
  AdTraceExample-CaseA-WebBridgeOnly.html  ← Case A (JS SDK init)
  AdTraceExample-CaseB-Hybrid.html         ← Case B (no JS init)
  adtrace_constants.js
```

## Integration checklist

| Step | What to do | File |
|------|------------|------|
| 1 | Dependencies | [`build.gradle`](build.gradle) |
| 2 | Manifest + empty `GlobalApplication` | [`AndroidManifest.xml`](src/main/AndroidManifest.xml) |
| 3 | Choose scenario on launcher | [`MainActivity.java`](src/main/java/io/adtrace/examples/MainActivity.java) |
| 4 | Case B native bootstrap | [`AdTraceDemoSession.java`](src/main/java/io/adtrace/examples/AdTraceDemoSession.java) |
| 5 | Register bridge + pick HTML | [`WebViewActivity.java`](src/main/java/io/adtrace/examples/WebViewActivity.java) |
| 6 | Tokens | [`AdTraceConstants.java`](src/main/java/io/adtrace/examples/AdTraceConstants.java) / [`adtrace_constants.js`](src/main/assets/adtrace_constants.js) |
| 7a | Case A page (JS init) | [`AdTraceExample-CaseA-WebBridgeOnly.html`](src/main/assets/AdTraceExample-CaseA-WebBridgeOnly.html) |
| 7b | Case B page (no JS init) | [`AdTraceExample-CaseB-Hybrid.html`](src/main/assets/AdTraceExample-CaseB-Hybrid.html) |

> **`AdTrace.onCreate` = SDK initialization (start AdTrace).**  
> Call it once: from JS in Case A, or from native in Case B — never both.

Full Case A / Case B docs: [TECHNICAL.md](TECHNICAL.md).

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

webView.loadUrl("file:///android_asset/AdTraceExample-CaseA-WebBridgeOnly.html");
// Case B: AdTraceExample-CaseB-Hybrid.html
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

Native: [`AdTraceConstants.java`](src/main/java/io/adtrace/examples/AdTraceConstants.java)  
JS: [`adtrace_constants.js`](src/main/assets/adtrace_constants.js)

Keep both files in sync.

## Step 6 — JS: init vs track only

**`AdTrace.onCreate(config)` = SDK initialization (start AdTrace).** Call it once.

| Case | Page | Init? |
|------|------|-------|
| A | [`AdTraceExample-CaseA-WebBridgeOnly.html`](src/main/assets/AdTraceExample-CaseA-WebBridgeOnly.html) | Yes — `AdTrace.onCreate` in HTML |
| B | [`AdTraceExample-CaseB-Hybrid.html`](src/main/assets/AdTraceExample-CaseB-Hybrid.html) | No — native already inited; only `trackEvent` |

```javascript
// Case A only — SDK init from JS:
var config = new AdTraceConfig(token, AdTraceConfig.EnvironmentSandbox);
AdTrace.onCreate(config);

// Both cases — track:
var event = new AdTraceEvent(AdTraceExampleConstants.EVENT_TOKEN_SIMPLE);
AdTrace.trackEvent(event);
```

## Run this example

```bash
./gradlew :example-app-webbridge:installDebug
```

1. Open the app → choose **Case A** or **Case B**
2. Use HTML buttons to track events
3. To try the other case: force-stop the app, then relaunch
4. Filter Logcat by tag `AdTrace`

## Project structure

```
example-app-webbridge/
├── README.md
├── TECHNICAL.md
├── build.gradle
└── src/main/
    ├── AndroidManifest.xml
    ├── java/io/adtrace/examples/
    │   ├── GlobalApplication.java      ← empty (init is lazy / per case)
    │   ├── AdTraceDemoSession.java     ← Case B native init + mode lock
    │   ├── AdTraceConstants.java
    │   ├── MainActivity.java           ← two case buttons
    │   └── WebViewActivity.java        ← loads Case A or Case B HTML
    ├── res/layout/
    │   ├── activity_main.xml
    │   └── activity_webview.xml
    └── assets/
        ├── adtrace_constants.js
        ├── AdTraceExample-CaseA-WebBridgeOnly.html  ← JS SDK init
        └── AdTraceExample-CaseB-Hybrid.html         ← no JS init
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

- **Two-case technical reference:** [TECHNICAL.md](TECHNICAL.md)
- Native SDK details: [root README](../README.md)
- Native examples: [Kotlin](../example-app-kotlin/README.md) · [Java](../example-app-java/README.md)
