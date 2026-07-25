# AdTrace Android Web Bridge SDK — Technical Documentation

Technical reference for integrating the **AdTrace Web Bridge** into an Android app that loads content in a `WebView`.

Aligned with AdTrace Android SDK / Web Bridge **v2.6.0**.

For a short walkthrough of this sample, see [README.md](README.md).  
For native-only apps (no WebView), see [example-app-java](../example-app-java/README.md) or [example-app-kotlin](../example-app-kotlin/README.md).

---

## Table of contents

1. [Overview](#1-overview)
2. [Choose your integration case](#2-choose-your-integration-case)
3. [Case A — WebBridge-only (no native events)](#3-case-a--webbridge-only-no-native-events)
4. [Case B — Hybrid (native events + Web Bridge)](#4-case-b--hybrid-native-events--web-bridge)
5. [Shared setup (both cases)](#5-shared-setup-both-cases)
6. [JavaScript APIs (events, callbacks, config)](#6-javascript-apis-events-callbacks-config)
7. [Deep links, privacy, advanced](#7-deep-links-privacy-advanced)
8. [Verification & troubleshooting](#8-verification--troubleshooting)
9. [API reference](#9-api-reference)
10. [Example app map](#10-example-app-map)

---

## 1. Overview

The Web Bridge lets HTML/JavaScript in a `WebView` talk to the **same native AdTrace SDK** used by Android code.

```
HTML / JavaScript  →  adtrace.js  →  AdTraceBridge (JavascriptInterface)
                                              ↓
                                    io.adtrace.sdk (native)  →  AdTrace backend
```

**Important:** there is only **one** SDK instance per app process.  
You initialize it **once** — either from JavaScript **or** from native Android — never both.

---

## 2. Choose your integration case

Ask one question: **Do you already track AdTrace events from Android native code (Activity / Application / Service)?**

| | **Case A — WebBridge-only** | **Case B — Hybrid** |
|--|-----------------------------|---------------------|
| Native events? | **No** — tracking only from HTML/JS | **Yes** — Activities/Services already call `AdTrace.trackEvent` |
| Who calls `AdTrace.onCreate`? | **JavaScript** in the WebView page | **Native** (`Application` / early Activity) |
| Native `GlobalApplication` init? | Optional / not required for AdTrace | **Required** |
| JS `AdTrace.onCreate`? | **Yes** (after bridge is registered) | **No** — skip it |
| Native `AdTrace.trackEvent`? | Not used | Yes (buttons, services, etc.) |
| JS `AdTrace.trackEvent`? | Yes | Yes (same SDK) |
| Typical app | In-app browser / mostly HTML funnel | Mixed native UI + WebView screens |
| This sample repo | Documented below | **Implemented** in this example |

```
                    Do you track events in Android native code?
                           /                    \
                         NO                      YES
                          |                       |
                     Case A                  Case B
                 WebBridge-only               Hybrid
                          |                       |
              JS: AdTrace.onCreate      Native: AdTrace.onCreate
              JS: AdTrace.trackEvent    Native: AdTrace.trackEvent
              Native: only bridge       JS: AdTrace.trackEvent only
                                        (no JS onCreate)
```

Calling `onCreate` twice logs: **`AdTrace already initialized`**.

### Demo both cases in this sample app

The launcher shows **two buttons**, each loading its **own HTML file**:

1. **Open Case A** → [`AdTraceExample-CaseA-WebBridgeOnly.html`](src/main/assets/AdTraceExample-CaseA-WebBridgeOnly.html)  
   - JS calls `AdTrace.onCreate` (**SDK init**)
2. **Open Case B** → native SDK init + native event → [`AdTraceExample-CaseB-Hybrid.html`](src/main/assets/AdTraceExample-CaseB-Hybrid.html)  
   - JS does **not** call `onCreate`

> **`AdTrace.onCreate` = initialize / start the AdTrace SDK.**  
> Same idea as native `AdTrace.onCreate(AdTraceConfig)`. Call it **once** per process.

Because the SDK starts only once per process, the first choice is locked until you **force-stop** the app and relaunch.

---

## 3. Case A — WebBridge-only (no native events)

Use when **all** AdTrace traffic comes from the WebView page. Native Android only hosts the WebView and registers the bridge.

### 3.1 Architecture

```
Application (no AdTrace.onCreate)
        │
        ▼
WebViewActivity
  AdTraceBridge.registerAndGetInstance(app, webView)
  webView.loadUrl(...)
        │
        ▼
HTML page
  AdTrace.onCreate(config)     ← initialize HERE
  AdTrace.trackEvent(...)
```

### 3.2 Native side (bridge only)

No `GlobalApplication` AdTrace init. No native `trackEvent`.

```java
public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // Must run BEFORE loadUrl so JS can see AdTraceBridge
        AdTraceBridge.registerAndGetInstance(getApplication(), webView);

        webView.loadUrl("file:///android_asset/AdTraceExample-CaseA-WebBridgeOnly.html");
    }

    @Override
    protected void onDestroy() {
        AdTraceBridge.unregister();
        super.onDestroy();
    }
}
```

Manifest: declare the Activity as usual. You do **not** need `android:name=".GlobalApplication"` for AdTrace in Case A.

Copy-paste reference: [`AdTraceExample-CaseA-WebBridgeOnly.html`](src/main/assets/AdTraceExample-CaseA-WebBridgeOnly.html)

### 3.3 JavaScript side (init + events)

```html
<script src="adtrace_event.js"></script>
<script src="adtrace_third_party_sharing.js"></script>
<script src="adtrace_config.js"></script>
<script src="adtrace.js"></script>

<script>
  // 1) Initialize SDK from JS
  var config = new AdTraceConfig(
      '{YourAppToken}',
      AdTraceConfig.EnvironmentSandbox
  );
  config.setLogLevel(AdTraceConfig.LogLevelVerbose);

  config.setAttributionCallback(function (a) { /* ... */ });
  config.setEventSuccessCallback(function (e) { /* ... */ });
  config.setEventFailureCallback(function (e) { /* ... */ });
  config.setSessionSuccessCallback(function (s) { /* ... */ });
  config.setSessionFailureCallback(function (s) { /* ... */ });
  config.setDeferredDeeplinkCallback(function (d) { /* ... */ });

  AdTrace.onCreate(config);

  // 2) Track events from the page
  function trackSimple() {
      var event = new AdTraceEvent('{eventToken}');
      AdTrace.trackEvent(event);
  }
</script>
```

### 3.4 Case A order of operations

1. Enable JavaScript on `WebView`
2. `AdTraceBridge.registerAndGetInstance(application, webView)`
3. `loadUrl(...)`
4. HTML loads `adtrace*.js`
5. JS: `AdTrace.onCreate(config)` (+ callbacks on config first)
6. JS: `AdTrace.trackEvent(...)` as needed
7. Activity `onDestroy` → `AdTraceBridge.unregister()`

### 3.5 Case A checklist

- [ ] Dependencies: core SDK + webbridge plugin
- [ ] Permissions: `INTERNET`, `ACCESS_NETWORK_STATE`, `AD_ID`
- [ ] Bridge registered **before** page load
- [ ] JS calls **`AdTrace.onCreate` once**
- [ ] Native code does **not** call `AdTrace.onCreate`
- [ ] Logcat `tag:AdTrace` shows install/session after page load

---

## 4. Case B — Hybrid (native events + Web Bridge)

Use when Android native code **already** (or also) tracks events, and WebView HTML should track more events into the **same** SDK.

This is what **`example-app-webbridge` implements**.

### 4.1 Architecture

```
GlobalApplication
  AdTrace.onCreate(config)              ← initialize ONCE (native)
  registerActivityLifecycleCallbacks
        │
        ├──────────────────────────────┐
        ▼                              ▼
 MainActivity / other UI         WebViewActivity
 AdTrace.trackEvent(...)         AdTraceBridge.registerAndGetInstance(...)
 (native events)                        │
                                        ▼
                                 HTML / JavaScript
                                 AdTrace.trackEvent(...)   ← NO AdTrace.onCreate
```

### 4.2 Step B1 — Initialize on native side (once)

```java
// GlobalApplication.java
public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AdTraceConfig config = new AdTraceConfig(
                this,
                "{YourAppToken}",
                AdTraceConfig.ENVIRONMENT_SANDBOX);
        config.setLogLevel(LogLevel.VERBOSE);
        config.setSendInBackground(true);
        // Set native callbacks here if you need them:
        // config.setOnEventTrackingSucceededListener(...);

        AdTrace.onCreate(config);
        registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
    }
}
```

```xml
<application android:name=".GlobalApplication" ...>
```

Example: [`GlobalApplication.java`](src/main/java/io/adtrace/examples/GlobalApplication.java)

### 4.3 Step B2 — Track events from native code

```java
// Any Activity / Service — same as a normal native integration
AdTraceEvent event = new AdTraceEvent("{simpleEventToken}");
AdTrace.trackEvent(event);
```

In this sample, **Open Case B** tracks a native simple event, then opens the WebView:

```java
AdTraceDemoSession.ensureNativeSdkInitialized(getApplication());
AdTraceEvent event = new AdTraceEvent(AdTraceConstants.EVENT_TOKEN_SIMPLE);
AdTrace.trackEvent(event);
startActivity(new Intent(this, WebViewActivity.class)
        .putExtra(AdTraceDemoSession.EXTRA_MODE, AdTraceDemoSession.MODE_CASE_B));
```

Example: [`MainActivity.java`](src/main/java/io/adtrace/examples/MainActivity.java)

### 4.4 Step B3 — Register Web Bridge (no second init)

Same bridge registration as Case A:

```java
AdTraceBridge.registerAndGetInstance(getApplication(), webView);
webView.loadUrl("file:///android_asset/AdTraceExample-CaseB-Hybrid.html");
// onDestroy → AdTraceBridge.unregister();
```

The bridge connects JS to the **already running** native SDK.

Example: [`WebViewActivity.java`](src/main/java/io/adtrace/examples/WebViewActivity.java)

### 4.5 Step B4 — Track from JavaScript (skip `onCreate`)

Open [`AdTraceExample-CaseB-Hybrid.html`](src/main/assets/AdTraceExample-CaseB-Hybrid.html) — it only calls `AdTrace.trackEvent` (no `AdTrace.onCreate`).

```html
<script src="adtrace_event.js"></script>
<script src="adtrace_config.js"></script>
<script src="adtrace.js"></script>
<script>
  // Do NOT call AdTrace.onCreate — native already did.

  document.getElementById('btnTrack').onclick = function () {
      var event = new AdTraceEvent('{eventToken}');
      AdTrace.trackEvent(event);  // same native SDK
  };
</script>
```

### 4.6 What belongs where (Case B)

| Concern | Native Android | JavaScript (bridge) |
|---------|----------------|---------------------|
| `AdTrace.onCreate` + session lifecycle | **Yes** | No |
| Events from Activities / Services | **Yes** | — |
| Events from HTML / web funnels | — | **Yes** |
| Callbacks / log level | On native `AdTraceConfig` | Only if you used Case A |
| Install referrer / Play deps | **Yes** | — |
| Deep links from `Intent` | `AdTrace.appWillOpenUrl(uri, context)` | `AdTrace.appWillOpenUrl(url)` if page has URL |

### 4.7 Case B checklist

- [ ] One native `AdTrace.onCreate` (Application recommended)
- [ ] Same app token / environment everywhere
- [ ] Native events via `io.adtrace.sdk.AdTrace` / `AdTraceEvent`
- [ ] Bridge registered before loading HTML
- [ ] HTML **does not** call `AdTrace.onCreate`
- [ ] HTML still calls `AdTrace.trackEvent` (and other JS APIs) as needed
- [ ] `AdTraceBridge.unregister()` in WebView Activity `onDestroy`

### 4.8 This sample’s Case B button

1. User taps **Open Case B**  
2. `AdTraceDemoSession.ensureNativeSdkInitialized` starts the SDK (`AdTrace.onCreate` = init)  
3. Native simple event (`p6j3p7`) is tracked  
4. `WebViewActivity` loads [`AdTraceExample-CaseB-Hybrid.html`](src/main/assets/AdTraceExample-CaseB-Hybrid.html) (no JS `onCreate`)  

---

## 5. Shared setup (both cases)

### 5.1 Requirements

| Item | Requirement |
|------|-------------|
| minSdkVersion | **17** (Web Bridge) |
| Core SDK | `io.adtrace:android-sdk` (same version as webbridge) |
| Plugin | `io.adtrace:android-sdk-plugin-webbridge` |
| WebView | JavaScript **enabled** |
| Permissions | `INTERNET`, `ACCESS_NETWORK_STATE`, `AD_ID` (API 31+) |
| Google Play | `play-services-ads-identifier` + `installreferrer` recommended |

### 5.2 Gradle

```gradle
dependencies {
    implementation 'io.adtrace:android-sdk:2.6.0'
    implementation 'io.adtrace:android-sdk-plugin-webbridge:2.6.0'
    implementation 'com.android.installreferrer:installreferrer:2.2'
    implementation 'com.google.android.gms:play-services-ads-identifier:18.0.0'
}
```

In this repo:

```gradle
implementation project(':android-sdk')
implementation project(':android-sdk-plugin-webbridge')
```

### 5.3 Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />
```

### 5.4 JavaScript assets

Shipped by the webbridge plugin (merged into the APK):

| File | Role |
|------|------|
| `adtrace.js` | Main `AdTrace` JS API |
| `adtrace_config.js` | `AdTraceConfig` |
| `adtrace_event.js` | `AdTraceEvent` |
| `adtrace_third_party_sharing.js` | Third-party sharing helper |

Include order:

```html
<script src="adtrace_event.js"></script>
<script src="adtrace_third_party_sharing.js"></script>
<script src="adtrace_config.js"></script>
<script src="adtrace.js"></script>
```

For remote HTML, host these files next to your page (or use absolute URLs).

### 5.5 Native bridge API (`AdTraceBridge`)

| Method | Description |
|--------|-------------|
| `registerAndGetInstance(Application, WebView)` | Register singleton + JS interface |
| `getDefaultInstance()` | Get singleton |
| `setWebView(WebView)` | Rebind WebView after recreation |
| `setApplicationContext(Application)` | Update app context |
| `unregister()` | Remove JS interface; clear singleton |

After registration, Android injects the JS object **`AdTraceBridge`**. `adtrace.js` wraps it as **`AdTrace`**.

### 5.6 Session lifecycle

| Case | Who drives resume/pause |
|------|-------------------------|
| **A** | Bridge registers `ActivityLifecycleCallbacks` when you call `registerAndGetInstance` |
| **B** | Prefer your own native lifecycle callbacks from `Application` (as in this sample). Bridge may also register — both call into the same SDK |

---

## 6. JavaScript APIs (events, callbacks, config)

These JS APIs work in **both** cases once the SDK is started (by JS in Case A, or by native in Case B).

### 6.1 When to call `AdTrace.onCreate` from JS

| Case | Call JS `AdTrace.onCreate`? |
|------|-----------------------------|
| A — WebBridge-only | **Yes** |
| B — Hybrid | **No** |

### 6.2 Config (Case A — before `onCreate`)

```javascript
var config = new AdTraceConfig(appToken, AdTraceConfig.EnvironmentSandbox);
config.setLogLevel(AdTraceConfig.LogLevelVerbose);
config.setSendInBackground(true);
config.setEventBufferingEnabled(true);
config.setDelayStart(5.5);            // max 10 seconds
config.setDefaultTracker('{tracker}');
config.setUrlStrategy(AdTraceConfig.UrlStrategyIR);
config.setCoppaCompliantEnabled(true);
config.setPlayStoreKidsAppEnabled(true);
config.setAppSecret(secretId, info1, info2, info3, info4);
AdTrace.onCreate(config);
```

**Environment:** `EnvironmentSandbox` | `EnvironmentProduction`  
**Log levels:** `LogLevelVerbose` | `Debug` | `Info` | `Warn` | `Error` | `Assert` | `Suppress`  
**URL strategy:** `UrlStrategyIR` | `UrlStrategyMOBI` | `DataResidencyIR`

After delay start: `AdTrace.sendFirstPackages()`.

In **Case B**, set the same options on the **native** `AdTraceConfig` instead.

### 6.3 Event tracking (JS — both cases)

```javascript
// Simple
var event = new AdTraceEvent('{eventToken}');
AdTrace.trackEvent(event);

// Revenue
event.setRevenue(52000.0, 'IRR');

// Parameters
event.addCallbackParameter('key', 'value');
event.addEventParameter('sku', '123');
event.addPartnerParameter('foo', 'bar');
event.setOrderId('{orderId}');
event.setCallbackId('my-correlation-id');

// Ad revenue
AdTrace.trackAdRevenue(source, payloadJsonString);
```

### 6.4 Native event tracking (Case B only)

```java
AdTraceEvent event = new AdTraceEvent("{eventToken}");
event.setRevenue(52000.0, "IRR");
event.addCallbackParameter("key", "value");
AdTrace.trackEvent(event);
```

### 6.5 Callbacks

**Case A:** set on JS `AdTraceConfig` **before** `AdTrace.onCreate`.

```javascript
config.setAttributionCallback(function (attribution) { /* ... */ });
config.setEventSuccessCallback(function (data) { /* ... */ });
config.setEventFailureCallback(function (data) { /* ... */ });
config.setSessionSuccessCallback(function (data) { /* ... */ });
config.setSessionFailureCallback(function (data) { /* ... */ });
config.setOpenDeferredDeeplink(true);
config.setDeferredDeeplinkCallback(function (deeplink) { /* ... */ });
```

**Case B:** set equivalent listeners on **native** `AdTraceConfig` before native `onCreate`.  
JS callback setters have no effect if JS never calls `onCreate`.

### 6.6 Other JS helpers (both cases)

```javascript
AdTrace.setEnabled(true);
AdTrace.isEnabled();
AdTrace.setOfflineMode(true);
AdTrace.addSessionCallbackParameter('k', 'v');
AdTrace.setPushToken(fcmToken);
AdTrace.getGoogleAdId(function (id) { /* ... */ });
AdTrace.getAdid();
AdTrace.getSdkVersion();
AdTrace.appWillOpenUrl(url);
AdTrace.gdprForgetMe();
AdTrace.disableThirdPartySharing();
AdTrace.trackMeasurementConsent(true);
```

---

## 7. Deep links, privacy, advanced

### 7.1 Deep links

| Source | API |
|--------|-----|
| URL known in HTML | `AdTrace.appWillOpenUrl(deeplinkUrl)` |
| Android `Intent` data | Native `AdTrace.appWillOpenUrl(uri, context)` |

### 7.2 Install referrer

Configure Play Install Referrer on the **native** side for both cases (library + optional receiver). See [root README](../README.md).

### 7.3 Privacy

```javascript
AdTrace.gdprForgetMe();
AdTrace.disableThirdPartySharing();
AdTrace.trackThirdPartySharing(obj);
AdTrace.trackMeasurementConsent(true);
```

COPPA / Kids: set on the config that performs `onCreate` (JS in Case A, native in Case B).

### 7.4 WebView recreation

```java
AdTraceBridge.setWebView(newWebView);
```

### 7.5 Remote HTML

1. Host `adtrace*.js` with your page  
2. Register bridge **before** `loadUrl`  
3. Follow Case A or B rules for `onCreate`

### 7.6 ProGuard

```
-keep class io.adtrace.sdk.** { *; }
-keep class io.adtrace.sdk.webbridge.** { *; }
-keep public class com.android.installreferrer.** { *; }
```

---

## 8. Verification & troubleshooting

### 8.1 Success checks

**Both cases**

- [ ] Bridge registered before page load  
- [ ] JS enabled; `adtrace*.js` load without 404  
- [ ] Logcat `adb logcat -s AdTrace` shows traffic  
- [ ] Tracked event shows `Path: /event`

**Case A only**

- [ ] JS `AdTrace.onCreate` runs after bridge registration  
- [ ] No native `AdTrace.onCreate`

**Case B only**

- [ ] Native `AdTrace.onCreate` in Application  
- [ ] Native event appears when tapping native UI  
- [ ] HTML does **not** call `AdTrace.onCreate`  
- [ ] No log line `AdTrace already initialized` from a second `onCreate`

### 8.2 Common issues

| Symptom | Likely cause | Fix |
|---------|--------------|-----|
| `AdTrace already initialized` | Both native and JS called `onCreate` | Pick Case A **or** B — only one `onCreate` |
| `AdTraceBridge` undefined in JS | Bridge after `loadUrl`, or wrong WebView | Register before load |
| No events from HTML (Case B) | Forgot bridge registration | Call `registerAndGetInstance` |
| No events from native (Case B) | SDK never started | Add native `AdTrace.onCreate` |
| No events from HTML (Case A) | Forgot JS `onCreate` | Call `AdTrace.onCreate` in page |
| Scripts 404 | Remote page without JS assets | Host `adtrace*.js` |
| minSdk errors | API &lt; 17 | Raise minSdk for webbridge |

### 8.3 Run this example

```bash
./gradlew :example-app-webbridge:installDebug
```

**Try Case A:** open app → **Open Case A** → tap HTML **Track Simple event**  
**Try Case B:** force-stop app → open app → **Open Case B** (native event first) → tap HTML events  

---

## 9. API reference

### 9.1 Native — `AdTraceBridge`

| Method | Description |
|--------|-------------|
| `registerAndGetInstance(Application, WebView)` | Register JS interface |
| `getDefaultInstance()` | Get singleton |
| `setWebView(WebView)` | Rebind WebView |
| `setApplicationContext(Application)` | Update context |
| `unregister()` | Tear down bridge |

### 9.2 Native — core SDK (Case B)

Use `io.adtrace.sdk.AdTrace`, `AdTraceConfig`, `AdTraceEvent` as in the [Java](../example-app-java/README.md) / [Kotlin](../example-app-kotlin/README.md) examples.

### 9.3 JavaScript — `AdTrace`

| Method | Description |
|--------|-------------|
| `onCreate(config)` | Init SDK (**Case A only**) |
| `trackEvent(event)` | Track event |
| `trackAdRevenue(source, payload)` | Ad revenue |
| `onResume` / `onPause` | Manual session hooks |
| `setEnabled` / `isEnabled` | Enable flag |
| `appWillOpenUrl` | Deep link |
| `setReferrer` | Referrer string |
| `setOfflineMode` | Offline queue |
| `sendFirstPackages` | End delay-start |
| `add/remove/resetSession*Parameter(s)` | Session params |
| `setPushToken` | FCM token |
| `gdprForgetMe` / `disableThirdPartySharing` | Privacy |
| `trackThirdPartySharing` / `trackMeasurementConsent` | Consent |
| `getGoogleAdId` / `getAmazonAdId` / `getAdid` | Device IDs |
| `getAttribution` / `getSdkVersion` | Attribution / version |
| `teardown` | Tear down JS/native bridge state |

### 9.4 JavaScript — `AdTraceConfig` / `AdTraceEvent`

See [§6](#6-javascript-apis-events-callbacks-config). Full setter list matches `adtrace_config.js` / `adtrace_event.js` in the plugin assets.

---

## 10. Example app map

```
example-app-webbridge/
├── README.md
├── TECHNICAL.md
├── build.gradle
└── src/main/
    ├── AndroidManifest.xml
    ├── java/.../GlobalApplication.java    ← empty shell (init is lazy for dual demo)
    ├── java/.../AdTraceDemoSession.java   ← locks Case A/B + Case B native init
    ├── java/.../AdTraceConstants.java
    ├── java/.../MainActivity.java         ← two buttons
    ├── java/.../WebViewActivity.java      ← bridge + Case A/B HTML file
    └── assets/
        ├── adtrace_constants.js
        ├── AdTraceExample-CaseA-WebBridgeOnly.html  ← Case A: JS AdTrace.onCreate (init)
        └── AdTraceExample-CaseB-Hybrid.html         ← Case B: trackEvent only
```

| File | Case A | Case B |
|------|--------|--------|
| `MainActivity` button | Open Case A | Open Case B (+ native event) |
| Native `AdTrace.onCreate` (SDK init) | No | Yes (`AdTraceDemoSession`) |
| HTML file | `...CaseA-WebBridgeOnly.html` | `...CaseB-Hybrid.html` |
| HTML `AdTrace.onCreate` (SDK init) | Yes | No |
| HTML `trackEvent` | Yes | Yes |

**Demo tokens**

| Name | Value |
|------|-------|
| App token | `humkdip1g0yl` |
| Simple | `p6j3p7` |
| Revenue | `ijdu0k` |
| Callback | `0issyz` |
| Partner | `905cgb` |
| Event params | `llo5i9` |

---

## Related docs

- [Root README](../README.md) — full native SDK guide  
- [example-app-webbridge README](README.md) — sample walkthrough  
- [example-app-java](../example-app-java/README.md) / [example-app-kotlin](../example-app-kotlin/README.md)  
- Plugin sources: `android-sdk-plugin-webbridge/`
