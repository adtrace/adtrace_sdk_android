<p align="center"><a href="https://adtrace.io" target="_blank" rel="noopener noreferrer"><img width="100" src="https://adtrace.io/fa/wp-content/uploads/2020/09/cropped-logo-sign-07-1.png" alt="Adtrace logo"></a></p>  
<p align="center">  
  <a href='https://opensource.org/licenses/MIT'><img src='https://img.shields.io/badge/License-MIT-green.svg'></a>  
</p>

## Summary

This is the Android SDK of AdTrace™. You can read more about AdTrace™ at [adtrace.io].




## Table of contents

### Quick start

- [Example apps](#qs-example-apps)
- [Getting started](#qs-getting-started)
  - [Add the SDK to your project](#qs-add-sdk)
  - [Add Google Play Services](#qs-gps)
  - [Add permissions](#qs-permissions)
  - [Proguard settings](#qs-proguard)
  - [Install referrer](#qs-install-referrer)
    - [Google Play Referrer API](#qs-gpr-api)
    - [Google Play Store intent](#qs-gps-intent)
    - [Huawei Referrer API](#qs-huawei-referrer-api)
- [Integrate the SDK into your app](#qs-integrate-sdk)
  - [Basic setup](#qs-basic-setup)
    - [Native App SDK](#qs-basic-setup-native)
    - [Web Views SDK](#qs-basic-setup-web)
  - [Session tracking](#qs-session-tracking)
    - [API level 14 and higher](#qs-session-tracking-api-14)
    - [API level between 9 and 13](#qs-session-tracking-api-9)
  - [SDK signature](#qs-sdk-signature)
  - [AdTrace logging](#qs-adtrace-logging)
  - [Build your app](#qs-build-the-app)

### Deep linking

- [Deep linking overview](#dl)
- [Standard deep linking scenario](#dl-standard)
- [Deferred deep linking scenario](#dl-deferred)
- [Reattribution via deep links](#dl-reattribution)

### Event tracking

- [Track event](#et-tracking)
- [Track revenue](#et-revenue)
- [Revenue deduplication](#et-revenue-deduplication)

### Custom parameters

- [Custom parameters overview](#cp)
- [Event parameters](#cp-event-parameters)
  - [Event callback parameters](#cp-event-callback-parameters)
  - [Event parameters](#cp-event-value-parameters)
  - [Event callback identifier](#cp-event-callback-id)
- [Session parameters](#cp-session-parameters)
  - [Session callback parameters](#cp-session-callback-parameters)
  - [Delay start](#cp-delay-start)

### Additional features

- [Push token (uninstall tracking)](#af-push-token)
- [Attribution callback](#af-attribution-callback)
- [Session and event callbacks](#af-session-event-callbacks)
- [User attribution](#af-user-attribution)
- [Uninstall tracking](#af-uninstall-tracking)
- [Device IDs](#af-device-ids)
  - [Google Play Services advertising identifier](#af-gps-adid)
  - [AdTrace device identifier](#af-adid)
- [Pre-installed trackers](#af-pre-installed-trackers)
- [Offline mode](#af-offline-mode)
- [Disable tracking](#af-disable-tracking)
- [Event buffering](#af-event-buffering)
- [Background tracking](#af-background-tracking)

* [COPPA compliance](#af-coppa-compliance)
* [Play Store Kids Apps](#af-play-store-kids-apps)

### Testing and troubleshooting

- [I'm seeing the "session failed (Ignoring too frequent session...)" error](#tt-session-failed)
- [Is my broadcast receiver capturing the install referrer?](#tt-broadcast-receiver)
- [Can I trigger an event at application launch?](#tt-event-at-launch)


## Quick start

## [Troubleshooting](./doc/fast-doc/android.md)

### <a id="qs-example-apps"></a>Example apps

There are Android example apps inside the [`example-app-java`](example-java)  
, [`example-app-kotlin`](example-kotlin) and [`example-app-keyboard`](example-keyboard)
directories, as well as example app that uses web views inside  
the [`example-webbridge`](example-webbridge) directory and Android TV example app inside the [`example-app-tv`](example-tv) directory. You can open the Android project to see these examples on how the AdTrace SDK can be integrated.

### <a id="qs-getting-started"></a>Getting started

These are the minimum required steps to integrate the AdTrace SDK in your Android app. We assume that you are using Android Studio for your Android development. The minimum supported Android API level for the AdTrace SDK integration is **9 (Gingerbread)**.

### <a id="qs-add-sdk"></a>Add the SDK to your project

If you are using Maven, add the following to your `build.gradle` file:

```java
implementation 'io.adtrace:android-sdk:2.1.0'
implementation 'com.android.installreferrer:installreferrer:2.2'
```

If you would prefer to use the AdTrace SDK inside web views in your app, please include this additional dependency as well:

```java
implementation 'io.adtrace:android-sdk-plugin-webbridge:2.1.1'
```

**Note**: The minimum supported Android API level for the web view extension is 17 (Jelly Bean).

You can also add the AdTrace SDK and web view extension as JAR files, which can be downloaded from our [releases page](releases).

### <a id="qs-gps"></a>Add Google Play Services

Since the 1st of August of 2014, apps in the Google Play Store must use the [Google Advertising ID](google-ad-id) to uniquely identify devices. To enable the Google Advertising ID for our SDK, you must integrate [Google Play Services](google-play-services). If
you haven't done this yet, please add dependency to the Google Play Services library by adding the following dependecy to your `dependencies` block of app's `build.gradle` file:

```java
implementation 'com.google.android.gms:play-services-ads-identifier:18.0.0'
```

**Note**: The AdTrace SDK is not tied to any specific version of the `play-services-analytics`
part of the Google Play Services library. You can use the latest version of the library, or any other version you need.

### <a id="qs-permissions"></a>Add permissions

The AdTrace SDK requires the following permissions. Please add them to your `AndroidManifest.xml` file if they are not already present:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

If you are **not targeting the Google Play Store**, you must also add the following permission:

```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
```


#### <a id="gps-adid-permission"></a>Add permission to gather Google advertising ID

If you are targeting Android 12 and above (API level 31), you need to add the `com.google.android.gms.AD_ID` permission to read the device's advertising ID. Add the following line to your `AndroidManifest.xml` to enable the permission.

```xml
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```

For more information, see [Google's `AdvertisingIdClient.Info` documentation](https://developers.google.com/android/reference/com/google/android/gms/ads/identifier/AdvertisingIdClient.Info#public-string-getid).


### <a id="qs-proguard"></a>Proguard settings

If you are using Proguard, add these lines to your Proguard file:

```
-keep class io.adtrace.sdk.** { *; }
-keep class com.google.android.gms.common.ConnectionResult {
	int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
	com.google.android.gms.ads.identifier.AdvertisingIdClient$Info
	getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
	java.lang.String getId();
	boolean isLimitAdTrackingEnabled();
}
-keep public class com.android.installreferrer.** { *; }
```

If you are **not publishing your app in the Google Play Store**, use the
following `io.adtrace.sdk` package rules:

```
-keep public class io.adtrace.sdk.** { *; }
```

### <a id="qs-install-referrer"></a>Install referrer

In order to correctly attribute an app install to its source, AdTrace needs information about the **install referrer** . We can achieve this in two different ways: either by using the **Google Play Referrer API** or by collecting the **Google Play Store intent** with a broadcast receiver.

**Important**: Google introduced the Google Play Referrer API to provide a more reliable and
secure way to obtain install referrer information and to aid attribution providers in the fight against click injection. We **strongly advise** you to support this in your application. The Google Play Store intent is a less secure way of obtaining install referrer information. For now it exists in parallel with the new Google Play Referrer API, but will be deprecated in the future.

#### <a id="qs-gpr-api"></a>Google Play Referrer API

In order to support the Google Play Referrer API in your app, please make sure that you have followed our chapter on [adding the SDK to your project](#qs-add-sdk) correctly and that you have following line added to your `build.gradle` file:

```java
implementation 'com.android.installreferrer:installreferrer:2.2'
```

Please follow the directions for your [Proguard settings](#qs-proguard) carefully. Confirm that you have added all the rules mentioned in it, especially the one needed for this feature:

```
 -keep public class com.android.installreferrer.** { *; }
```

This feature is supported if you are using **AdTrace SDK v2.+ or above**.

#### <a id="qs-gps-intent"></a>Google Play Store intent

**Note**:
Google [has announced](https://android-developers.googleblog.com/2019/11/still-using-installbroadcast-switch-to.html)
deprecation of `INSTALL_REFERRER` intent usage to deliver referrer information as of March 1st 2020. If you are using this way of accessing referrer information, please migrate to [Google Play Referrer API](#qs-gpr-api) approach.

You should capture the Google Play Store `INSTALL_REFERRER` intent with a broadcast receiver. If you are **not using your own broadcast receiver** to receive the `INSTALL_REFERRER` intent, add the following `receiver` tag inside the `application` tag in your `AndroidManifest.xml`.

```xml
<receiver
  android:name="io.adtrace.sdk.AdTraceReferrerReceiver"
  android:permission="android.permission.INSTALL_PACKAGES"
  android:exported="true">
  <intent-filter>
    <action android:name="com.android.vending.INSTALL_REFERRER" />
  </intent-filter>
</receiver>
```

We use this broadcast receiver to retrieve the install referrer and pass it to our backend.

If you are using a different broadcast receiver for the `INSTALL_REFERRER` intent, follow [these instructions](referrer) to properly ping the AdTrace broadcast receiver.

#### <a id="qs-huawei-referrer-api"></a>Huawei Referrer API

As of v2.0.1, the AdTrace SDK supports install tracking on Huawei devices with Huawei App Gallery version 10.4 and higher. No additional integration steps are needed to start using the Huawei Referrer API. for more information read [OAID Documentation](doc/english/oaid.md).

### <a id="qs-integrate-sdk"></a>Integrate the SDK into your app

First, we'll set up basic session tracking.

### <a id="qs-basic-setup"></a>Basic setup

If you are integrating the SDK into a native app, follow the directions for
a [Native App SDK](#qs-basic-setup-native). If you are integrating the SDK for usage inside web views, please follow the directions for a [Web Views SDK](#qs-basic-setup-web) below.

#### <a id="qs-basic-setup-native"></a>Native App SDK

We recommend using a global
Android [Application](https://developer.android.com/reference/android/app/Application.html) class to
initialize the SDK. If you don't have one in your app, follow these steps:

- Create a class that extends the `Application`.
- Open the `AndroidManifest.xml` file of your app and locate the `<application>` element.
- Add the attribute `android:name` and set it to the name of your new application class.

  In our example app, we use an `Application` class named `GlobalApplication`. Therefore, we configure the manifest file as:

```xml
<application
	android:name=".GlobalApplication"
	...
</application>
```

- In your `Application` class, find or create the `onCreate` method. Add the following code to initialize the AdTrace SDK:

```java
import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceConfig;

public class GlobalApplication extends Application {


   @Override
   public void onCreate() {
       super.onCreate();
       String appToken = "{YourAppToken}";
       String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;
       AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
       AdTrace.onCreate(config);
   }
}
```

Replace `{YourAppToken}` with your app token. You can find this in your [dashboard].

Next, you must set the `environment` to either sandbox or production mode:

```java
String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;
String environment = AdTraceConfig.ENVIRONMENT_PRODUCTION;
```

**Important:** Set the value to `AdTraceConfig.ENVIRONMENT_SANDBOX` if (and only if) you or someone else is testing your app. Make sure to set the environment to `AdTraceConfig.ENVIRONMENT_PRODUCTION`  
before you publish the app. Set it back to `AdTraceConfig.ENVIRONMENT_SANDBOX` if you start developing and testing it again.

We use this environment to distinguish between real traffic and test traffic from test devices. Keeping the environment updated according to your current status is very important!

#### <a id="qs-basic-setup-web"></a>Web Views SDK

After you have obtained the reference to your `WebView` object:

- Call `webView.getSettings().setJavaScriptEnabled(true)`, to enable Javascript in the web view
- Start the default instance of `AdTraceBridgeInstance` by  
  calling `AdTraceBridge.registerAndGetInstance(getApplication(), webview)`
- This will also register the AdTrace bridge as a Javascript Interface to the web view
- Call `AdTraceBridge.setWebView()` to set new `WebView` if needed.
- Call `AdTraceBridge.unregister()` to uregister the `AdTraceBridgeInstance` and `WebView`.

After these steps, your activity should look like this:

```java
public class MainActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        AdTraceBridge.registerAndGetInstance(getApplication(), webView);
        try {
            webView.loadUrl("file:///android_asset/AdTraceExample-WebView.html");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override protected void onDestroy() {
        AdTraceBridge.unregister();
        super.onDestroy();
    }
}
```

After you complete this step, you will have successfully added the AdTrace bridge to your app. The Javascript bridge is now enabled to communicate between AdTrace's native Android SDK and your page, which will be loaded in the web view.

In your HTML file, import the AdTrace Javascript files which are located in the root of the assets folder. If your HTML file is there as well, import them like this:

```html
<script type="text/javascript" src="adtrace.js"></script>
<script type="text/javascript" src="adtrace_event.js"></script>
<script type="text/javascript" src="adtrace_third_party_sharing.js"></script>
<script type="text/javascript" src="adtrace_config.js"></script>
```

Once you add your references to the Javascript files, use them in your HTML file to initialise the AdTrace SDK:

```js
let yourAppToken = "{YourAppToken}";
let environment = AdTraceConfig.EnvironmentSandbox;
let adtraceConfig = new AdTraceConfig(yourAppToken, environment);
AdTrace.onCreate(adtraceConfig);
```

Replace `{YourAppToken}` with your app token. You can find this in your [dashboard].

Next, set your `environment` to the corresponding value, depending on whether you are still testing or are in production mode:

```js
let environment = AdTraceConfig.EnvironmentSandbox;
let environment = AdTraceConfig.EnvironmentProduction;
```

**Important:** Set your value to `AdTraceConfig.EnvironmentSandbox` if (and only if) you or someone else is testing your app. Make sure you set the environment
to `AdTraceConfig.EnvironmentProduction` just before you publish the app. Set it back to `AdTraceConfig.EnvironmentSandbox` if you start developing and testing again.

We use this environment to distinguish between real traffic and test traffic from test devices. Keeping it updated according to your current status is very important!

### <a id="qs-session-tracking"></a>Session tracking

**Note**: This step is **very important**. Please **make sure that you implement it properly in your app**. Completing this step correctly ensures that the AdTrace SDK can properly track sessions in your app.

#### <a id="qs-session-tracking-api-14"></a>API level 14 and higher

- Add a private class that implements the `ActivityLifecycleCallbacks` interface. If you don't have access to this interface, your app is targeting an Android API level lower than 14. You will
  have to manually update each activity by following these [instructions](#qs-session-tracking-api-9).
  If you have `AdTrace.onResume` and `AdTrace.onPause` calls on each of your app's activities, you should remove them.
- Edit the `onActivityResumed(Activity activity)` method and add a call to `AdTrace.onResume()`.  
  Edit the `onActivityPaused(Activity activity)` method and add a call to `AdTrace.onPause()`.
- Add the `onCreate()` method with the AdTrace SDK is configured and call `registerActivityLifecycleCallbacks` with an instance of the created `ActivityLifecycleCallbacks` class.

```java
import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceConfig;

public class GlobalApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        String appToken = "{YourAppToken}";
        String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;
        AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
        AdTrace.onCreate(config);

        registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
        //...
    }
    private static final class AdTraceLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override public void onActivityResumed(Activity activity) {
            AdTrace.onResume();
        }
        @Override public void onActivityPaused(Activity activity) {
            AdTrace.onPause();
        }
        //...
    }
}
```

#### <a id="qs-session-tracking-api-9"></a>API level between 9 and 13

If your app `minSdkVersion` in gradle is between `9` and `13`, consider updating it to at least `14` to simplify the integration process. Consult the official Android [dashboard](android-dashboard)
to find out the latest market share of the major versions.

To provide proper session tracking, certain AdTrace SDK methods are called every time an activity resumes or pauses (otherwise the SDK might miss a session start or end). In order to do so, follow these steps **for each Activity** of your app:

- In your Activity's `onResume` method, call `AdTrace.onResume()`. Create the method if needed.
- In your Activity's `onPause` method, call `AdTrace.onPause()`. Create the method if needed.

After these steps, your activity should look like this:

```java
import io.adtrace.sdk.AdTrace;

// ...
public class YourActivity extends Activity {
    protected void onResume() {
        super.onResume();
        AdTrace.onResume();
    }

    protected void onPause() {
        super.onPause();
        AdTrace.onPause();
    }
    // ...
}
```

Repeat these steps for **every Activity** in your app. Don't forget to repeat these steps whenever you create a new activity in the future. Depending on your coding style, you might want to implement this in a common superclass of all your activities.

### <a id="qs-sdk-signature"></a>SDK signature

An account manager must activate the AdTrace SDK Signature. Contact AdTrace support (info@adtrace.io) if you are interested in using this feature.

If the SDK signature has already been enabled on your account and you have access to App Secrets in your AdTrace Dashboard, please use the method below to integrate the SDK signature into your app.

An App Secret is set by calling `setAppSecret` on your config instance:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
config.setAppSecret(secretId, info1, info2, info3, info4);
AdTrace.onCreate(config);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceConfig = new AdTraceConfig(yourAppToken, environment);
adtraceConfig.setAppSecret(secretId, info1, info2, info3, info4);
AdTrace.onCreate(adtraceConfig);
```

</td>    
</tr>    
</table>

### <a id="qs-adtrace-logging"></a>AdTrace logging

You can increase or decrease the amount of logs that you see during testing by
calling `setLogLevel` on your config instance with one of the following parameters:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
config.setLogLevel(LogLevel.VERBOSE); // enable all logs
config.setLogLevel(LogLevel.DEBUG); // disable verbose logs
config.setLogLevel(LogLevel.INFO); // disable debug logs (default)
config.setLogLevel(LogLevel.WARN); // disable info logs
config.setLogLevel(LogLevel.ERROR); // disable warning logs
config.setLogLevel(LogLevel.ASSERT); // disable error logs
config.setLogLevel(LogLevel.SUPRESS); // disable all logs
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelVerbose); // enable all logs
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelDebug); // disable verbose logs
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelInfo); // disable debug logs (default)
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelWarn); // disable info logs
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelError); // disable warning logs
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelAssert); // disable error logs
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelSuppress); // disable all logs
```

</td>    
</tr>    
</table>

If you want to disable all of your log output, set the log level to suppress, and use the constructor for config object (which gets boolean parameters indicating whether or not suppress log level should be supported):

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment, true);
config.setLogLevel(LogLevel.SUPRESS);
AdTrace.onCreate(config);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceConfig = new AdTraceConfig(yourAppToken, environment, true);
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelSuppress);
AdTrace.onCreate(adtraceConfig);
```

</td>    
</tr>    
</table>

### <a id="qs-build-the-app"></a>Build your app

Build and run your Android app. In your `LogCat` viewer, set the filter `tag:AdTrace` to hide all other logs. After your app has launched you should see the following AdTrace log: `Install tracked`.

## Deep linking

### <a id="dl"></a>Deep linking Overview

If you are using AdTrace tracker URLs with deeplinking enabled, it is possible to receive information about the deeplink URL and its content. Users may interact with the URL regardless of whether they have your app installed on their device (standard deep linking scenario) or not (deferred deep linking scenario). In the standard deep linking scenario, the Android platform natively offers the possibility for you to receive deep link content information. The Android platform does not automatically support deferred deep linking scenario; in this case, the AdTrace SDK offers the mechanism you need to get the information about the deep link content.

### <a id="dl-standard"></a>Standard deep linking scenario

If a user has your app installed and you want it to launch after they engage with an AdTrace tracker URL with the `deep_link` parameter in it, enable deeplinking in your app. This is done by choosing a desired **unique scheme name**. You'll assign it to the activity you want to launch once your app opens following a user selecting the tracker URL in the`AndroidManifest.xml` file. Add the `intent-filter` section to your desired activity definition in the manifest file and assign an `android:scheme` property value with the desired scheme name:

```xml
<activity
  android:name=".MainActivity"
  android:configChanges="orientation|keyboardHidden"
  android:label="@string/app_name"
  android:screenOrientation="portrait">
	<intent-filter>
		<action android:name="android.intent.action.MAIN" />
		<category android:name="android.intent.category.LAUNCHER" />
	</intent-filter>
	<intent-filter>
		<action android:name="android.intent.action.VIEW" />
		<category android:name="android.intent.category.DEFAULT" />
		<category android:name="android.intent.category.BROWSABLE" />
		<data android:scheme="adtraceExample" />
	</intent-filter>
</activity>
```

If you want your app to launch once the tracker URL is selected, use the assigned scheme name in
the AdTrace tracker URL's `deep_link` parameter. A tracker URL without any information added to the deeplink could look something like this:

```
https://app.adtrace.io/adt1ex?deep_link=adtraceExample%3A%2F%2F
```

Don't forget: **you must url encode** the `deep_link` parameter value in the URL.

With the app set as described above, your app will launch along with the `MainActivity` intent
when a user selects the tracker URL. Inside the `MainActivity` class, you will automatically receive the information about the `deep_link` parameter content. Once you receive this content, it will **not** be encoded (even though it was encoded in the URL).

The activity setting of your `android:launchMode` within the `AndroidManifest.xml` file will determine the delivery location of the `deep_link` parameter content within the activity file. For more information about the possible values of the `android:launchMode` property, check out Android's [official documentation](https://developer.android.com/guide/topics/manifest/activity-element.html)
.

Deeplink content information within your desired activity is delivered via the `Intent` object,
via either the activity's `onCreate` or `onNewIntent` methods. Once you've launched your app and have triggered **one of these methods**, you will be able to receive the actual deeplink passed in the `deep_link` parameter in the click URL. You can then use this information to conduct some additional logic in your app.

You can extract deeplink content from either two methods like so:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Intent intent = getIntent();
    Uri data = intent.getData();
    // data.toString() -> This is your deep_link parameter value.
}
```

```java
@Override
protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Uri data = intent.getData();
    // data.toString() -> This is your deep_link parameter value.
}
```

### <a id="dl-deferred"></a>Deferred deep linking scenario

Deferred deeplinking scenario occurs when a user clicks on an AdTrace tracker URL with a `deep_link` parameter contained in it, but does not have the app installed on the device at click time. When the user clicks the URL, they will be redirected to the Play Store to download and install your app. After opening it for the first time, `deep_link` parameter content will be delivered to your app.

The AdTrace SDK opens the deferred deep link by default. There is no extra configuration needed.

#### Deferred deep linking callback

If you wish to control if the AdTrace SDK will open the deferred deep link, you can do it with a callback method in the config object.

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
// Evaluate the deeplink to be launched.
config.setOnDeeplinkResponseListener(new OnDeeplinkResponseListener() {
    @Override
    public boolean launchReceivedDeeplink(Uri deeplink) {
        // ...
        if (shouldAdTraceSdkLaunchTheDeeplink(deeplink)) {
            return true;
        } else {
            return false;
        }
    }
});
AdTrace.onCreate(config);
```

After the AdTrace SDK receives the deep link information from our backend, the SDK will deliver you its content via the listener and expect the `boolean` return value from you. This return value represents your decision on whether or not the AdTrace SDK should launch the activity to which you have assigned the scheme name from the deeplink (like in the standard deeplinking scenario).

If you return `true`, we will launch it, triggering the scenario described in the [Standard deep linking scenario](#dl-standard) chapter. If you do not want the SDK to launch the activity, return `false` from the listener, and (based on the deep link content) decide on your own what to do next in your app.

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceConfig = new AdTraceConfig(yourAppToken, environment);
adtraceConfig.setDeferredDeeplinkCallback(function (deeplink) {});
AdTrace.onCreate(adtraceConfig);
```

In this deferred deep linking scenario, there is one additional setting you can set on the config object. Once the AdTrace SDK gets the deferred deep link information, you have the possibility to choose whether our SDK opens the URL or not. Set this option by calling the `setOpenDeferredDeeplink` method on the config object:

```js
// ...
function deferredDeeplinkCallback(deeplink) {}
let adtraceConfig = new AdTraceConfig(yourAppToken, environment);
adtraceConfig.setOpenDeferredDeeplink(true);
adtraceConfig.setDeferredDeeplinkCallback(deferredDeeplinkCallback);
AdTrace.start(adtraceConfig);
```

Remember that if you do not set the callback, **the AdTrace SDK will always attempt to launch the URL by default**.

</td>    
</tr>    
</table>


### <a id="dl-reattribution"></a>Reattribution via deeplinks

AdTrace enables you to run re-engagement campaigns with deeplinks.

If you are using this feature, you need to make one additional call to the AdTrace SDK in your app for us to properly reattribute your users.

Once you have received the deeplink content in your app, add a call to the `AdTrace.appWillOpenUrl(Uri, Context)` method. By making this call, the AdTrace SDK will send information to the AdTrace backend to check if there is any new attribution information inside of the deeplink. If your user is reattributed due to a click on the AdTrace tracker URL with deeplink content, you will see the [attribution callback](#af-attribution-callback) triggered in your app with new attribution info for this user.

Here's how the call to `AdTrace.appWillOpenUrl(Uri, Context)` should look:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Intent intent = getIntent();
    Uri data = intent.getData();
    AdTrace.appWillOpenUrl(data, getApplicationContext());
}
```

```java
@Override
protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    Uri data = intent.getData();
    AdTrace.appWillOpenUrl(data, getApplicationContext());
}
```

**Note**: `AdTrace.appWillOpenUrl(Uri)` method is marked as **deprecated** as of Android SDK v2+. Please use `AdTrace.appWillOpenUrl(Uri, Context)` method instead.

**Note for web view**: This call can also be made from the web view with the function `AdTrace.appWillOpenUrl` in Javascript like so:

```js
AdTrace.appWillOpenUrl(deeplinkUrl);
```


## Event tracking

### <a id="et-tracking"></a>Track event

You can use AdTrace to track any event in your app. Suppose you want to track every tap on a button. To do so, you'll create a new event token in your [dashboard]. Let's say that the event token is `adt1ex`. In your button's `onClick` method, add the following lines to track the click:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("adt1ex");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceEvent = new AdTraceEvent("adt1ex");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
</table>

### <a id="et-revenue"></a>Track revenue

If your users can generate revenue by tapping on advertisements or making in-app purchases, you can track those revenues too with events. Let's say a tap is worth one Euro cent. You can track the revenue event like this:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.setRevenue(52000, "IRR");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.setRevenue(52000, "IRR");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
</table>

This can be combined with callback parameters.

If you want to track in-app purchases, please make sure to call `trackEvent` only if the purchase is finished and the item has been purchased. This is important in order avoid tracking revenue that
was not actually generated.

You can read more about revenue and event tracking at AdTrace in the [event tracking](#et-tracking) guide.

### <a id="et-revenue-deduplication"></a>Revenue deduplication

You can also add an optional order ID to avoid tracking duplicate revenues. By doing so, the last ten order IDs will be remembered and revenue events with duplicate order IDs are skipped. This is especially useful for tracking in-app purchases. You can see an example below.

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.setRevenue(52000, "IRR");
adtraceEvent.setOrderId("{OrderId}");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.setRevenue(52000, "IRR");
adtraceEvent.setOrderId("{OrderId}");
AdTrace.trackEvent(event);
```

</td>    
</tr>    
</table>

## Custom parameters

### <a id="cp"></a>Custom parameters overview

In addition to the data points the AdTrace SDK collects by default, you can use the AdTrace SDK to track and add as many custom values as you need (user IDs, product IDs, etc.) to the event or session. Custom parameters are only available as raw data and will **not** appear in your AdTrace dashboard.

You should use **callback parameters** for the values you collect for your own internal use, and **event parameters** for values that you want to attach with an event. If a value (e.g. product ID) is needed for both, you can use both of them.

### <a id="cp-event-parameters"></a>Event parameters

### <a id="cp-event-callback-parameters"></a>Event callback parameters

You can register a callback URL for your events in your [dashboard]. We will send a GET request to that URL whenever the event is tracked. You can add callback parameters to that event by calling `addCallbackParameter` to the event instance before tracking it. We will then append these parameters to your callback URL.

For example, if you've registered the URL `http://www.example.com/callback`, then you would track an event like this:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.addCallbackParameter("key", "value");
adtraceEvent.addCallbackParameter("foo", "bar");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.addCallbackParameter("key", "value");
adtraceEvent.addCallbackParameter("foo", "bar");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
</table>

In this case we would track the event and send a request to:

```
http://www.example.com/callback?key=value&foo=bar
```

AdTrace supports a variety of placeholders, for example `{gps_adid}`, which can be used as parameter values. In the resulting callback, we would replace the placeholder (in this case) with the Google Play Services ID of the current device. Please note that we don't store any of your custom parameters. We **only** append them to your callbacks. If you haven't registered a callback for an event, we will not even read these parameters.

### <a id="cp-event-value-parameters"></a>Event parameters

When you want to send any value with an event you can add **event parameters**.

This works similarly to the callback parameters mentioned above; add them by calling the `addEventParameter` method to your event instance.

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.addEventParameter("key", "value");
adtraceEvent.addEventParameter("foo", "bar");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.addEventParameter("key", "value");
adtraceEvent.addEventParameter("foo", "bar");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
</table>

### <a id="cp-event-callback-id"></a>Event callback identifier

You can add custom string identifiers to each event you want to track. This identifier will later
be reported in your event success and/or event failure callbacks. This lets you keep track of which event was successfully tracked. Set this identifier by calling the `setCallbackId` method on your event instance:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.setCallbackId("Your-Custom-Id");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adtraceEvent = new AdTraceEvent("adt1ex");
adtraceEvent.setCallbackId("Your-Custom-Id");
AdTrace.trackEvent(adtraceEvent);
```

</td>    
</tr>    
</table>

### <a id="cp-session-parameters"></a>Session parameters

Session parameters are saved locally and sent in every **event** and **session** of the AdTrace SDK. When you add any of these parameters, we will save them so you don't need to add them every time. Adding the same parameter twice will have no effect.

These session parameters can be called before the AdTrace SDK is launched (to make sure they are sent on install). If you need to send them with an install, but can only obtain the needed values after launch, it's possible to [delay](#delay-start) the first launch of the AdTrace SDK to allow for this behavior.

### <a id="cp-session-callback-parameters"></a>Session callback parameters

You can save any callback parameters registered for [events](#event-callback-parameters) to be
sent in every event or session of the AdTrace SDK.

The session callback parameters' interface is similar to the one for event callback parameters. Instead of adding the key and its value to an event, add them via a call to `AdTrace.addSessionCallbackParameter(String key, String value)`:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTrace.addSessionCallbackParameter("foo", "bar");
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
AdTrace.addSessionCallbackParameter("foo", "bar");
```

</td>    
</tr>    
</table>

Session callback parameters merge together with the callback parameters you add to an event. Callback parameters added to an event take precedence over session callback parameters, meaning
that if you add a callback parameter to an event with the same key to one added from the session, the value that prevails is the callback parameter added to the event.

It's possible to remove a specific session callback parameter by passing the desired key to the method: `AdTrace.removeSessionCallbackParameter(String key)`.

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTrace.removeSessionCallbackParameter("foo");
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
AdTrace.removeSessionCallbackParameter("foo");
```

</td>    
</tr>    
</table>

If you wish to remove all keys and their corresponding values from the session callback parameters, you can reset with the method `AdTrace.resetSessionCallbackParameters()`.

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTrace.resetSessionCallbackParameters();
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
AdTrace.resetSessionCallbackParameters();
```

</td>    
</tr>    
</table>

### <a id="cp-delay-start"></a>Delay start

Delaying the start of the AdTrace SDK allows your app some time to obtain session parameters (such as unique identifiers) to be sent on install.

Set the initial delay time in seconds with the method `setDelayStart` in the config instance:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
adtraceConfig.setDelayStart(5.5);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
adtraceConfig.setDelayStart(5.5);
```

</td>    
</tr>    
</table>

In this example, this will prevent the AdTrace SDK from sending the initial install session and any event created for 5.5 seconds. After the time expireds (or if you call `AdTrace.sendFirstPackages()` during that time) every session parameter will be added to the delayed install session and events the AdTrace SDK will resume as usual.

**The maximum delay start time of the adtrace SDK is 10 seconds**.

## Additional features

Once you have integrated the AdTrace SDK into your project, you can take advantage of the following features:

### <a id="af-push-token"></a>Push token

Push tokens are used for Audience Builder and client callbacks; they are also required for uninstall and reinstall tracking.

To send us the push notification token, add the following call to AdTrace once you have obtained your token (or whenever its value changes):

<table>    
<tr>    
<td>    
<b>Native SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTrace.setPushToken(pushNotificationsToken, context);
```

This updated signature with `context` added allows the SDK to cover more scenarios to make sure
the push token is sent. It is advised that you use the signature method above.

We do, however, still support the previous signature of the same method without the `context`.

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
AdTrace.setPushToken(pushNotificationsToken);
```

</td>    
</tr>    
</table>

#### <a id="af-uninstall-tracking"></a>Uninstall tracking

in order to measure uninstalls of your app you need to pass Firebase **Server Key** and **Sender ID** to our [dashboard]. after that we can track each user if they uninstalled your app. the process includes sending a silent push notification (background notification without title and body). **MAKE SURE** to handle it properly.

there are multiple ways to handle this but a simple way would be as follows:

```java
@Override
public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    if(remoteMessage.getData().get("title") == null && remoteMessage.getData().get("body") == null){
        // it is silent push
    }else{
        // some code
    }
 }
```

### <a id="af-attribution-callback"></a>Attribution callback

You can register a listener to be notified of tracker attribution changes. Due to the different sources we consider for attribution, we cannot provide this information synchronously.

With the config instance, add the attribution callback before you start the SDK:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
    @Override
    public void onAttributionChanged(AdTraceAttribution attribution) {

    }
});
AdTrace.onCreate(config);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
function attributionCallback(attribution) {}
// ...
let adtraceConfig = new AdTraceConfig(yourAppToken, environment);
adtraceConfig.setAttributionCallback(attributionCallback);
AdTrace.onCreate(adtraceConfig);
```

The listener function is called after the SDK receives the final attribution data. Within the listener function, you'll have access to the `attribution` parameter. Here is a quick summary of its properties:

- `trackerToken` the tracker token string of the current attribution.
- `trackerName` the tracker name string of the current attribution.
- `network` the network grouping level string of the current attribution.
- `campaign` the campaign grouping level string of the current attribution.
- `adgroup` the ad group grouping level string of the current attribution.
- `creative` the creative grouping level string of the current attribution.
- `clickLabel` the click label string of the current attribution.
- `adid` the AdTrace device identifier string.

**Note**: The cost data - `costType`, `costAmount` & `costCurrency` are only available when configured in `AdTraceConfig` by calling `setNeedsCost` method. If not configured or configured, but not being part of the attribution, these fields will have value `null`. This feature is available in SDK v2.+ and above.

### <a id="af-session-event-callbacks"></a>Session and event callbacks

You can register a listener to be notified when events or sessions are tracked. There are four listeners: one for tracking successful events, one for tracking failed events, one for tracking successful sessions, and one for tracking failed sessions. Add as many listeners as you need after creating the config object like so:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);

// Set event success tracking delegate.
config.setOnEventTrackingSucceededListener(new OnEventTrackingSucceededListener() {
    @Override
    public void onFinishedEventTrackingSucceeded(AdTraceEventSuccess eventSuccessResponseData) {
        // ...
    }
});

// Set event failure tracking delegate.
config.setOnEventTrackingFailedListener(new OnEventTrackingFailedListener() {
    @Override
    public void onFinishedEventTrackingFailed(AdTraceEventFailure eventFailureResponseData) {
        // ...
    }
});

// Set session success tracking delegate.
config.setOnSessionTrackingSucceededListener(new OnSessionTrackingSucceededListener() {
    @Override
    public void onFinishedSessionTrackingSucceeded(AdTraceSessionSuccess sessionSuccessResponseData) {
        // ...
    }
});

// Set session failure tracking delegate.
config.setOnSessionTrackingFailedListener(new OnSessionTrackingFailedListener() {
    @Override
    public void onFinishedSessionTrackingFailed(AdTraceSessionFailure sessionFailureResponseData) {
        // ...
    }
});

AdTrace.onCreate(config);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
function eventSuccessCallback(eventSuccessResponseData) {}
function eventFailureCallback(eventFailureResponseData) {}
function sessionSuccessCallback(sessionSuccessResponseData) {}
function sessionFailureCallback(sessionFailureResponseData) {}
// ...
let adtraceConfig = new AdTraceConfig(yourAppToken, environment);
adtraceConfig.setEventSuccessCallback(eventSuccessCallback);
adtraceConfig.setEventFailureCallback(eventFailureCallback);
adtraceConfig.setSessionSuccessCallback(sessionSuccessCallback);
adtraceConfig.setSessionFailureCallback(sessionFailureCallback);
AdTrace.onCreate(adtraceConfig);
```

</td>    
</tr>    
</table>

The listener function is called after the SDK tries to send a package to the server. Within the listener function you have access to a response data object specifically for the listener. Here is a quick summary of the success session response data object fields:

- `message` message string from the server (or the error logged by the SDK).
- `timestamp` timestamp string from the server.
- `adid` a unique string device identifier provided by AdTrace.
- `jsonResponse` the JSON object with the reponse from the server.

Both event response data objects contain:

- `eventToken` the event token string, if the package tracked was an event.
- `callbackId` the custom defined [callback ID](#cp-event-callback-id) string set on the event object.

And both event and session failed objects also contain:

- `willRetry` boolean which indicates whether there will be a later attempt to resend the package.

### <a id="af-user-attribution"></a>User attribution

Like we described in the [attribution callback section](#af-attribution-callback), this callback is triggered whenever the attribution information changes. Access your user's current attribution information whenever you need it by making a call to the following method of the `AdTrace` instance:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTraceAttribution attribution = AdTrace.getAttribution();
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let attribution = AdTrace.getAttribution();
```

</td>    
</tr>    
</table>

**Note**: Current attribution information is only available after our backend tracks the app install and triggers the attribution callback. **It is not possible** to access a user's attribution value before the SDK has been initialized and the attribution callback has been triggered.

### <a id="af-device-ids"></a>Device IDs

The AdTrace SDK offers you the possibility to obtain device identifiers.

### <a id="af-gps-adid"></a>Google Play Services Advertising Identifier

Certain services (such as Google Analytics) require you to coordinate advertising IDs and client IDs in order to prevent duplicate reporting.

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

If you need to obtain the Google Advertising ID, there is a restriction; it can only be read in a background thread. If you call the function `getGoogleAdId` with the context and a `OnDeviceIdsRead` instance, it will work in any situation:

```java
AdTrace.getGoogleAdId(this, new OnDeviceIdsRead() {
    @Override
    public void onGoogleAdIdRead(String googleAdId) {}
});
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

To obtain the device's Google Advertising device identifier, it's necessary to pass a callback function to `AdTrace.getGoogleAdId` which will receive the Google Advertising ID in its argument, like so:

```js
AdTrace.getGoogleAdId(function (googleAdId) {
  // ...
});
```

</td>    
</tr>    
</table>

### <a id="af-adid"></a>AdTrace device identifier

For each device with your app installed on it, our backend generates a unique **AdTrace device identifier** (known as an **adid**). In order to obtain this identifier, call the following method on `AdTrace` instance:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
String adid = AdTrace.getAdid();
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
let adid = AdTrace.getAdid();
```

</td>    
</tr>    
</table>

**Note**: Information about the **adid** is only available after our backend tracks the app instal. **It is not possible** to access the **adid** value before the SDK has been initialized and the installation of your app has been successfully tracked.

### <a id="af-pre-installed-trackers"></a>Pre-installed trackers

If you want to use the AdTrace SDK to recognize users whose devices came with your app pre-installed, follow these steps:

- Create a new tracker in your [dashboard].
- Open your app delegate and set the default tracker of your config:

  <table>
  <tr>
  <td>
  <b>Native App SDK</b>
  </td>
  </tr>
  <tr>
  <td>

  ```java
  adtraceConfig.setDefaultTracker("{TrackerToken}");
  ```
  </td>
  </tr>
  <tr>
  <td>
  <b>Web View SDK</b>
  </td>
  </tr>
  <tr>
  <td>

  ```js
  adtraceConfig.setDefaultTracker('{TrackerToken}');
  ```
  </td>
  </tr>
  </table>

- Replace `{TrackerToken}` with the tracker token you created in step one. Please note that the dashboard displays a tracker URL (including `http://app.adtrace.io/`). In your source code, you should specify only the six or seven-character token and not the entire URL.

- Build and run your app. You should see a line like the following in your LogCat:

  ```
  Default tracker: 'abc123'
  ```

### <a id="af-offline-mode"></a>Offline mode

You can put the AdTrace SDK in offline mode to suspend transmission to our servers (while retaining tracked data to be sent later). While in offline mode, all information is saved in a file. Please be careful not to trigger too many events while in offline mode.

Activate offline mode by calling `setOfflineMode` with the parameter `true`.
	
### <a id="af-offline-mode"></a>Offline mode

You can put the AdTrace SDK in offline mode to suspend transmission to our servers (while retaining tracked data to be sent later). While in offline mode, all information is saved in a file. Please be careful not to trigger too many events while in offline mode.

Activate offline mode by calling `setOfflineMode` with the parameter `true`.

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTrace.setOfflineMode(true);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
AdTrace.setOfflineMode(true);
```

</td>    
</tr>    
</table>

Conversely, you can deactivate offline mode by calling `setOfflineMode` with `false`. When the AdTrace SDK is put back into online mode, all saved information is sent to our servers with the correct time information.

Unlike disabling tracking, this setting is **not remembered** between sessions. This means the SDK is in online mode whenever it starts, even if the app was terminated in offline mode.

### <a id="af-disable-tracking"></a>Disable tracking

You can disable the AdTrace SDK from tracking any activities of the current device by calling `setEnabled` with parameter `false`. **This setting is remembered between sessions**.

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
AdTrace.setEnabled(false);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
AdTrace.setEnabled(false);
```

</td>    
</tr>    
</table>

You can check to see if the AdTrace SDK is currently enabled by calling the function `isEnabled`. It is always possible to activatе the AdTrace SDK by invoking `setEnabled` with the enabled parameter as `true`.

### <a id="af-event-buffering"></a>Event buffering

If your app makes heavy use of event tracking, you might want to delay some network requests in order to send them in one batch every minute. You can enable event buffering with your config instance:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
adtraceConfig.setEventBufferingEnabled(true);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
adtraceConfig.setEventBufferingEnabled(true);
```

</td>    
</tr>    
</table>

### <a id="af-background-tracking"></a>Background tracking

The default behaviour of the AdTrace SDK is to pause sending network requests while the app is in the background. You can change this in your config instance:

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```java
adtraceConfig.setSendInBackground(true);
```

</td>    
</tr>    
<tr>    
<td>    
<b>Web View SDK</b>    
</td>    
</tr>    
<tr>    
<td>

```js
adtraceConfig.setSendInBackground(true);
```

</td>    
</tr>    
</table>


### <a id="af-coppa-compliance"></a>COPPA compliance

By deafult AdTrace SDK doesn't mark app as COPPA compliant. In order to mark your app as COPPA compliant, make sure to call `setCoppaCompliantEnabled` method of `AdTraceConfig` instance with boolean parameter `true`:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
adtraceConfig.setCoppaCompliantEnabled(true);
```
</td>
</tr>
<tr>
<td>
<b>Web View SDK</b>
</td>
</tr>
<tr>
<td>

```js
adtraceConfig.setCoppaCompliantEnabled(true);
```
</td>
</tr>
</table>

**Note:** By enabling this feature, third-party sharing will be automatically disabled for the users. If later during the app lifetime you decide not to mark app as COPPA compliant anymore, third-party sharing **will not be automatically re-enabled**. Instead, next to not marking your app as COPPA compliant anymore, you will need to explicitly re-enable third-party sharing in case you want to do that.

### <a id="af-play-store-kids-apps"></a>Play Store Kids Apps

By default AdTrace SDK doesn't mark app as Play Store Kids App. In order to mark your app as the app which is targetting kids in Play Store, make sure to call `setPlayStoreKidsAppEnabled` method of `AdTraceConfig` instance with boolean parameter `true`:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
adtraceConfig.setPlayStoreKidsAppEnabled(true);
```
</td>
</tr>
<tr>
<td>
<b>Web View SDK</b>
</td>
</tr>
<tr>
<td>

```js
adtraceConfig.setPlayStoreKidsAppEnabled(true);
```
</td>
</tr>
</table>


## Testing and troubleshooting

### <a id="tt-session-failed"></a>I'm seeing the "Session failed (Ignoring too frequent session. ...)" error.

This error typically occurs when testing installs. Uninstalling and reinstalling the app is not enough to trigger a new install. The servers will determine that the SDK has lost its locally aggregated session data and ignore the erroneous message, given the information available on the servers about the device.

This behavior can be cumbersome during testing, but is necessary in order to have the sandbox behavior match production as much as possible.

our [Testing Console](https://panel.adtrace.io) if you have Editor-level access (or higher) to the app.

Once the device has been correctly forgotten, the Testing Console will return `Forgot device`. If the device was already forgotten (or if the values were incorrect) the link will return `Advertising ID not found`.

Forgetting the device will not reverse the GDPR forget call.

### <a id="tt-broadcast-receiver"></a>Is my broadcast receiver capturing the install referrer?

If you followed the instructions in the [guide](#qs-gps-intent), the broadcast receiver should be configured to send the install referrer to our SDK and to our servers.

You can test this by manually triggering a test install referrer. Replace `com.your.appid` with your app ID and run the following command with the [adb](http://developer.android.com/tools/help/adb.html) tool that comes with Android Studio:

```
adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n com.your.appname/io.adtrace.sdk.AdTraceReferrerReceiver --es "referrer" "adtrace_reftag%3Dadt1ex4%26tracking_id%3D123456789%26utm_source%3Dnetwork%26utm_medium%3Dbanner%26utm_campaign%3Dcampaign"
```

If you already use a different broadcast receiver for the `INSTALL_REFERRER` intent and followed this [guide](referrer), replace `io.adtrace.sdk.AdTraceReferrerReceiver` with your broadcast receiver.

You can also remove the `-n com.your.appid/io.adtrace.sdk.AdTraceReferrerReceiver` parameter so that all the apps in the device will receive the `INSTALL_REFERRER` intent.

If you set the log level to `verbose`, you should be able to see the log from reading the referrer:

```
V/AdTrace: Referrer to parse (adtrace_reftag=adt1ex&tracking_id=123456789&utm_source=network&utm_medium=banner&utm_campaign=campaign) from reftag
```

And a click package added to the SDK's package handler:

```
V/AdTrace: Path:      /sdk_click
 ClientSdk: android2.1.0
 Parameters:
	 app_token        adt1exadt1ex
	 click_time       yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z
	 created_at       yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z
	 environment      sandbox
	 gps_adid         12345678-0abc-de12-3456-7890abcdef12
	 needs_attribution_data 1
	 referrer         adtrace_reftag=adt1ex&tracking_id=123456789&utm_source=network&utm_medium=banner&utm_campaign=campaign
	 reftag           adt1ex
	 source           reftag
	 tracking_enabled 1
```

If you perform this test before launching the app, you won't see the package being sent. The package will be sent once the app is launched.

**Important:** We encourage you to **not** use the `adb` tool for testing this particular feature. In order to test your full referrer content (in case you have multiple parameters separated with `&`), with `adb` you will need to encode that content in order to get it into your broadcast receiver. If you don't encode it, `adb` will cut your referrer after the first `&` sign and deliver wrong content to your broadcast receiver.

If you would like to see how your app receives an unencoded referrer value, we would encourage you to try our example app and alter the content being passed so that it fires with intent inside of the `onFireIntentClick` method inside of the `MainActivity.java` file:

```java
public void onFireIntentClick(View v) {
    Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
    intent.setPackage("com.adtrace.examples");
    intent.putExtra("referrer", "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test");
    sendBroadcast(intent);
}
```

Feel free to alter the second parameter of `putExtra` method with content of your choice.

### <a id="tt-event-at-launch"></a>Can I trigger an event at application launch?

Triggering an event at this time might not do what you expect. Here's why:

The `onCreate` method on the global `Application` class is called not only at application launch, but also when a system or application event is captured by the app.

Our SDK is prepared for initialization at this time, but has not actually started. This will only happen when an activity takes place, i.e., when a user actually launches the app.

Triggering an event at this time would start the AdTrace SDK and send the events, even though the app was not launched by the user - at a time that depends on factors external to the app.

Triggering events at application launch will thus result in inaccuracies in the number of installs and sessions tracked.

If you want to trigger an event after the install, use the [attribution callback](#af-attribution-callback).

If you want to trigger an event when the app is launched, use the `onCreate` method for the given activity.

### Special cases

[multi-process apps](multi-process-apps)

[multiple receivers](multiple-receivers)

[native code (hiding `appToken` and `SDK signatures`)](native-code)

[multi-process-apps]: doc/english/multi-process-app.md
[multiple-receivers]: doc/english/multiple-receivers.md
[native-code]: https://github.com/namini40/adtrace-NDK-example
[dashboard]: https://panel.adtrace.io/
[adtrace.io]: https://adtrace.io
[en-readme]: README.md
[fa-readme]: doc/persian/README.md
[example-java]: AdTrace/example-app-java
[example-kotlin]: AdTrace/example-app-kotlin
[example-tv]: AdTrace/example-app-tv
[example-webbridge]: AdTrace/example-app-webbridge
[referrer]: doc/english/multiple-receivers.md
[google-ad-id]: https://support.google.com/googleplay/android-developer/answer/6048248?hl=en
[new-referrer-api]: https://developer.android.com/google/play/installreferrer/library.html
[android-dashboard]: http://developer.android.com/about/dashboards/index.html
[android-launch-modes]: https://developer.android.com/guide/topics/manifest/activity-element.html
[google-play-services]: http://developer.android.com/google/play-services/setup.html
[reattribution-with-deeplinks]: https://adtrace.io
[android-purchase-verification]: https://adtrace.io
