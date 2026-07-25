# AdTrace WebBridge Integration Example

Native Android example that opens a WebView and uses the **AdTrace JavaScript bridge**.

## How it works

1. [`MainActivity`](src/main/java/io/adtrace/examples/MainActivity.java) — native launcher screen (like the other example apps)
2. Tap **Show WebView** → opens [`WebViewActivity`](src/main/java/io/adtrace/examples/WebViewActivity.java)
3. WebView loads [`AdTraceExample-WebView.html`](src/main/assets/AdTraceExample-WebView.html) and talks to the SDK via `AdTraceBridge`

## Dependencies

```java
implementation 'io.adtrace:android-sdk:2.6.0'
implementation 'io.adtrace:android-sdk-plugin-webbridge:2.6.0'
```

In this repo the example uses:

```java
implementation project(':android-sdk')
implementation project(':android-sdk-plugin-webbridge')
```

## Bridge setup (in WebViewActivity)

```java
WebView webView = findViewById(R.id.webView);
webView.getSettings().setJavaScriptEnabled(true);
webView.setWebChromeClient(new WebChromeClient());
webView.setWebViewClient(new WebViewClient());

AdTraceBridge.registerAndGetInstance(getApplication(), webView);
webView.loadUrl("file:///android_asset/AdTraceExample-WebView.html");

// Later, in onDestroy:
AdTraceBridge.unregister();
```

## Run

```bash
./gradlew :example-app-webbridge:installDebug
```

## Tokens

Configured in the HTML asset (same as the Java / Kotlin examples):

| Usage | Token |
|-------|-------|
| App token | `humkdip1g0yl` |
| Simple event | `p6j3p7` |
| Revenue event | `ijdu0k` |
| Callback event | `0issyz` |
| Partner event | `905cgb` |
| Event with params | `llo5i9` |

## Full SDK documentation

See the [root README](../README.md) and the [Kotlin](../example-app-kotlin/README.md) / [Java](../example-app-java/README.md) guides for native integration.
