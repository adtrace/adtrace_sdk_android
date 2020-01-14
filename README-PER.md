[English](./README.md) | فارسی

## <div dir="rtl" align='right'>خلاصه</div>

<div dir="rtl" align='right'>
SDK اندروید ادتریس. شما برای اطلاعات بیشتر میتوانید به <a href="adtrace.io">adtrace.io</a>  مراجعه کنید.
</div>

## <div dir="rtl" >پیاده سازی</div>

* [تنظیمات اولیه](#basic-integration)
   * [SDK اضافه کردن](#sdk-add)
   * [Google Play Services اضافه کردن](#sdk-gps)
   * [اضافه کردن دسترسی ها](#sdk-permissions)
   * [Progaurd تنظیمات ](#sdk-proguard)
   * [Install referrer تنظیمات](#install-referrer)
      * [Google Play Referrer API تنظیمات](#gpr-api)
      * [Google Play Store intent تنظیمات](#gps-intent)
   * [SDK پیاده سازی](#basic-setup)
   * [رصد نشست](#session-tracking)
      * [API level 14 and higher](#session-tracking-api14)
      * [API level between 9 and 13](#session-tracking-api9)
   * [Log انواع](#adtrace-logging)
   * [ساختن برنامه](#build-the-app)
   * [موقعیت یابی](#location)
* [ویژگی های دیگر](#additional-features)
   * [رصد رویداد](#event-tracking)
      * [رصد رویداد درآمدی](#revenue-tracking)
      * [ممانعت از رویداد درآمدی تکراری](#revenue-deduplication)
      * [پارامترهای کالبک](#callback-parameters)
      * [پارامترهای شریک](#partner-parameters)
      * [Callback شناسایی](#callback-id)
   * [پارامترهای نشست](#session-parameters)
      * [callback پارامترهای](#session-callback-parameters)
      * [partner پارامترهای](#session-partner-parameters)
      * [شروع با تاخیر](#delay-start)
   * [callback اتریبیوشن](#attribution-callback)
   * [](#session-event-callbacks)
   * [غیرفعال نمودن رصد](#disable-tracking)
   * [حالت آفلاین](#offline-mode)
   * [Event بافرکردن](#event-buffering)
   * [GDPR right to be forgotten](#gdpr-forget-me)
   * [SDK امضا](#sdk-signature)
   * [رصد در پس زمینه](#background-tracking)
   * [شناسنامه دستگاه](#device-ids)
      * [Google Play Services شناسه تبلیغاتی](#di-gps-adid)
      * [Amazon شناسه تبلیغاتی](#di-amz-adid)
      * [AdTrace شناسه](#di-adid)
   * [اتریبیوشن کاربر](#user-attribution)
   * [Push token](#push-token)
   * [Pre-installed trackers](#pre-installed-trackers)
   * [Deep linking](#deeplinking)
      * [Standard deep linking scenario](#deeplinking-standard)
      * [Deferred deep linking scenario](#deeplinking-deferred)
      * [Reattribution via deep links](#deeplinking-reattribution)
* [Troubleshooting](#troubleshooting)
   * [I'm seeing the "Session failed (Ignoring too frequent session. ...)" error](#ts-session-failed)
   * [Is my broadcast receiver capturing the install referrer?](#ts-broadcast-receiver)
   * [Can I trigger an event at application launch?](#ts-event-at-launch)
* [License](#license)

## <a id="basic-integration"></a>Basic integration

These are the minimal steps required to integrate the AdTrace SDK into your Android project. We are going to assume that you use Android Studio for your Android development and target an Android API level 9 (Gingerbread) or later.

### <a id="sdk-add"></a>Add the SDK to your project

If you are using Maven, add the following to your `build.gradle` file:

```
implementation 'io.adtrace:android-sdk:1.0.1'
implementation 'com.android.installreferrer:installreferrer:1.0'
```

### <a id="sdk-gps"></a>Add Google Play Services

Since the 1st of August of 2014, apps in the Google Play Store must use the [Google Advertising ID][google_ad_id] to uniquely identify devices. To allow the AdTrace SDK to use the Google Advertising ID, you must integrate the [Google Play Services][google_play_services]. If you haven't done this yet, follow these steps:

- Open the `build.gradle` file of your app and find the `dependencies` block. Add the following line:

    ```
    implementation 'com.google.android.gms:play-services-analytics:16.0.4'
    ```

    **Note**: The AdTrace SDK is not tied to any specific version of the `play-services-analytics` part of the Google Play Services library, so feel free to always use the latest version of it (or whichever you might need).

- **Skip this step if you are using version 7 or later of Google Play Services**: In the Package Explorer open the `AndroidManifest.xml` of your Android project. Add the following `meta-data` tag inside the `<application>` element.

    ```xml
    <meta-data android:name="com.google.android.gms.version"
               android:value="@integer/google_play_services_version" />
    ```

### <a id="sdk-permissions"></a>Add permissions

Please add the following permissions, which the AdTrace SDK needs, if they are not already present in your `AndroidManifest.xml` file:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> <!--optional-->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> <!--optional-->
```

If you are **not targeting the Google Play Store**, please also add the following permission:

```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
```

### <a id="sdk-proguard"></a>Proguard settings

If you are using Proguard, add these lines to your Proguard file:

```
-keep public class io.adtrace.sdk.** { *; }
-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}
-keep public class com.android.installreferrer.** { *; }
```

If you are **not targeting the Google Play Store**, you can remove the `com.google.android.gms` rules.

### <a id="install-referrer"></a>Install referrer

In order to correctly attribute an install of your app to its source, AdTrace needs information about the **install referrer**. This can be obtained by using the **Google Play Referrer API** or by catching the **Google Play Store intent** with a broadcast receiver.

**Important**: The Google Play Referrer API is newly introduced by Google with the express purpose of providing a more reliable and secure way of obtaining install referrer information and to aid attribution providers in the fight against click injection. It is **strongly advised** that you support this in your application. The Google Play Store intent is a less secure way of obtaining install referrer information. It will continue to exist in parallel with the new Google Play Referrer API temporarily, but it is set to be deprecated in future.

#### <a id="gpr-api"></a>Google Play Referrer API

In order to support this in your app, please make sure that you have followed the [Add the SDK to your project](#sdk-add) chapter properly and that you have following line added to your `build.gradle` file:

```
implementation 'com.android.installreferrer:installreferrer:1.0'
```

Also, make sure that you have paid attention to the [Proguard settings](#sdk-proguard) chapter and that you have added all the rules mentioned in it, especially the one needed for this feature:

```
-keep public class com.android.installreferrer.** { *; }
```

This feature is supported if you are using **AdTrace SDK v4.12.0 or above**.

#### <a id="gps-intent"></a>Google Play Store intent

The Google Play Store `INSTALL_REFERRER` intent should be captured with a broadcast receiver. If you are **not using your own broadcast receiver** to receive the `INSTALL_REFERRER` intent, add the following `receiver` tag inside the `application` tag in your `AndroidManifest.xml`.

```xml
<receiver
    android:name="io.adtrace.sdk.AdTraceReferrerReceiver"
    android:permission="android.permission.INSTALL_PACKAGES"
    android:exported="true" >
    <intent-filter>
        <action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>
```

We use this broadcast receiver to retrieve the install referrer and pass it to our backend.

If you are already using a different broadcast receiver for the `INSTALL_REFERRER` intent, follow [these instructions][referrer] to add the AdTrace broadcast receiver.

### <a id="basic-setup"></a>Basic setup

We recommend using a global android [Application][android_application] class to initialize the SDK. If you don't have one in your app already, follow these steps:

- Create a class that extends `Application`.
- Open the `AndroidManifest.xml` file of your app and locate the `<application>` element.
- Add the attribute `android:name` and set it to the name of your new application class pefixed by a dot.

    In our example app we use an `Application` class named `GlobalApplication`, so the manifest file is configured as:
    ```xml
     <application
       android:name=".GlobalApplication"
       <!-- ...-->
    </application>
    ```

- In your `Application` class find or create the `onCreate` method and add the following code to initialize the AdTrace SDK:

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

Depending on whether you are building your app for testing or for production, you must set `environment` with one of these values:

```java
String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;
String environment = AdTraceConfig.ENVIRONMENT_PRODUCTION;
```

**Important:** This value should be set to `AdTraceConfig.ENVIRONMENT_SANDBOX` if and only if you or someone else is testing your app. Make sure to set the environment to `AdTraceConfig.ENVIRONMENT_PRODUCTION` before you publish the app. Set it back to `AdTraceConfig.ENVIRONMENT_SANDBOX` when you start developing and testing it again.

We use this environment to distinguish between real traffic and test traffic from test devices. It is imperative that you keep this value meaningful at all times.

To increase the accuracy and security in fraud detection, you can enable or disable the sending of installed applications on user's device as follows:

```java
config.enableSendInstalledApps(true);
```
**Note**: This option is disabled by default.
### <a id="session-tracking"></a>Session tracking

**Note**: This step is **really important** and please **make sure that you implement it properly in your app**. By implementing it, you will enable proper session tracking by the AdTrace SDK in your app.

### <a id="session-tracking-api14"></a>API level 14 and higher

- Add a private class that implements the `ActivityLifecycleCallbacks` interface. If you don't have access to this interface, your app is targeting an Android API level inferior to 14. You will have to update manually each Activity by following these [instructions](#session-tracking-api9). If you had `AdTrace.onResume` and `AdTrace.onPause` calls on each Activity of your app before, you should remove them.
- Edit the `onActivityResumed(Activity activity)` method and add a call to `AdTrace.onResume()`. Edit the
`onActivityPaused(Activity activity)` method and add a call to `AdTrace.onPause()`.
- Add on the `onCreate()` method where the AdTrace SDK is configured and add call  `registerActivityLifecycleCallbacks` with an instance of the created `ActivityLifecycleCallbacks` class.

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

            registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());

            //...
        }

         private static final class AdTraceLifecycleCallbacks implements ActivityLifecycleCallbacks {
             @Override
             public void onActivityResumed(Activity activity) {
                 AdTrace.onResume();
             }

             @Override
             public void onActivityPaused(Activity activity) {
                 AdTrace.onPause();
             }

             //...
         }
      }
    ```

### <a id="session-tracking-api9"></a>API level between 9 and 13

If your app `minSdkVersion` in gradle is between `9` and `13`, consider updating it to at least `14` to simplify the integration process in the long term. Consult the official Android [dashboard][android-dashboard] to know the latest market share of the major versions.

To provide proper session tracking it is required to call certain AdTrace SDK methods every time any Activity resumes or pauses. Otherwise the SDK might miss a session start or session end. In order to do so you should **follow these steps for each Activity of your app**:

- Open the source file of your Activity.
- Add the `import` statement at the top of the file.
- In your Activity's `onResume` method call `AdTrace.onResume()`. Create the method if needed.
- In your Activity's `onPause` method call `AdTrace.onPause()`. Create the method if needed.

After these steps your activity should look like this:

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

Repeat these steps for **every** Activity of your app. Don't forget these steps when you create new Activities in the future. Depending on your coding style you might want to implement this in a common superclass of all your Activities.

### <a id="adtrace-logging"></a>AdTrace Logging

You can increase or decrease the amount of logs that you see during testing by calling `setLogLevel` on your `AdTraceConfig` instance with one of the following parameters:

```java
config.setLogLevel(LogLevel.VERBOSE);   // enable all logging
config.setLogLevel(LogLevel.DEBUG);     // enable more logging
config.setLogLevel(LogLevel.INFO);      // the default
config.setLogLevel(LogLevel.WARN);      // disable info logging
config.setLogLevel(LogLevel.ERROR);     // disable warnings as well
config.setLogLevel(LogLevel.ASSERT);    // disable errors as well
config.setLogLevel(LogLevel.SUPRESS);   // disable all log output
```

In case you want all your log output to be disabled, beside setting the log level to suppress, you should also use  constructor for `AdTraceConfig` object which gets boolean parameter indicating whether suppress log level should be supported  or not:

```java
String appToken = "{YourAppToken}";
String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;

AdTraceConfig config = new AdTraceConfig(this, appToken, environment, true);
config.setLogLevel(LogLevel.SUPRESS);

AdTrace.onCreate(config);
```

### <a id="location"></a>Location
Another way to increase the accuracy and security of fraud detection is to check the user's location. You can enable or disable this feature by using the following method.

```java
AdTrace.enableLocation(true);
```
**Note**: This option is enabled by default. But in case the location-based permissions are not granted, location data will not be sent to the server.

### <a id="build-the-app"></a>Build your app

Build and run your Android app. In your `LogCat` viewer you can set the filter `tag:AdTrace` to hide all other logs. After your app has launched you should see the following AdTrace log: `Install tracked`.

## Additional Features

Once you have integrated the AdTrace SDK into your project, you can take advantage of the following features.

### <a id="event-tracking"></a>Event tracking

You can use AdTrace to track any event in your app. Suppose you want to track every tap on a button. You would have to create a new event token in your [dashboard]. Let's say that event token is `abc123`. In your button's `onClick` method you could then add the following lines to track the click:

```java
AdTraceEvent event = new AdTraceEvent("abc123");
AdTrace.trackEvent(event);
```

### <a id="revenue-tracking"></a>Revenue tracking

If your users can generate revenue by tapping on advertisements or making in-app purchases you can track those revenues with events. Lets say a tap is worth one Euro cent. You could then track the revenue event like this:

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.setRevenue(0.01, "EUR");
AdTrace.trackEvent(event);
```

This can be combined with callback parameters of course.

When you set a currency token, AdTrace will automatically convert the incoming revenues into a reporting revenue of your choice. Read more about [currency conversion here][currency-conversion].

You can read more about revenue and event tracking in the [event tracking guide][event-tracking].

### <a id="revenue-deduplication"></a>Revenue deduplication

You can also add an optional order ID to avoid tracking duplicate revenues. The last ten order IDs are remembered, and revenue events with duplicate order IDs are skipped. This is especially useful for in-app purchase tracking. You can see an  example below.

If you want to track in-app purchases, please make sure to call `trackEvent` only if the purchase is finished and item is purchased. That way you can avoid tracking revenue that is not actually being generated.

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.setRevenue(0.01, "EUR");
event.setOrderId("{OrderId}");
AdTrace.trackEvent(event);
```

### <a id="callback-parameters"></a>Callback parameters

You can register a callback URL for your events in your [dashboard]. We will send a GET request to that URL whenever the event is tracked. You can add callback parameters to that event by calling `addCallbackParameter` to the event instance before tracking it. We will then append these parameters to your callback URL.

For example, suppose you have registered the URL `http://www.adtrace.io/callback` then track an event like this:

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.addCallbackParameter("key", "value");
event.addCallbackParameter("foo", "bar");
AdTrace.trackEvent(event);
```

In that case we would track the event and send a request to:

```
http://www.adtrace.io/callback?key=value&foo=bar
```

It should be mentioned that we support a variety of placeholders like `{gps_adid}` that can be used as parameter values. In the resulting callback this placeholder would be replaced with the Google Play Services ID of the current device. Also note that we don't store any of your custom parameters, but only append them to your callbacks. If you haven't registered a callback for an event, these parameters won't even be read.

You can read more about using URL callbacks, including a full list of available values, in our [callbacks guide][callbacks-guide].

### <a id="partner-parameters"></a>Partner parameters

You can also add parameters to be transmitted to network partners, which have been activated in your AdTrace dashboard.

This works similarly to the callback parameters mentioned above, but can be added by calling the `addPartnerParameter` method on your `AdTraceEvent` instance.

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.addPartnerParameter("key", "value");
event.addPartnerParameter("foo", "bar");
AdTrace.trackEvent(event);
```

You can read more about special partners and these integrations in our [guide to special partners][special-partners].

### <a id="callback-id"></a>Callback identifier

You can also add custom string identifier to each event you want to track. This identifier will later be reported in event success and/or event failure callbacks to enable you to keep track on which event was successfully tracked or not. You can set this identifier by calling the `setCallbackId` method on your `AdTraceEvent` instance:

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.setCallbackId("Your-Custom-Id");
AdTrace.trackEvent(event);
```

### <a id="session-parameters"></a>Set up session parameters

Some parameters are saved to be sent in every **event** and **session** of the AdTrace SDK. Once you have added any of these parameters, you don't need to add them every time, since they will be saved locally. If you add the same parameter twice, there will be no effect.

These session parameters can be called before the AdTrace SDK is launched to make sure they are sent even on install. If you need to send them with an install, but can only obtain the needed values after launch, it's possible to [delay](#delay-start) the first launch of the AdTrace SDK to allow this behaviour.

### <a id="session-callback-parameters"></a>Session callback parameters

The same callback parameters that are registered for [events](#callback-parameters) can be also saved to be sent in every  event or session of the AdTrace SDK.

The session callback parameters have a similar interface to the event callback parameters. Instead of adding the key and it's value to an event, it's added through a call to `AdTrace.addSessionCallbackParameter(String key, String value)`:

```java
AdTrace.addSessionCallbackParameter("foo", "bar");
```

The session callback parameters will be merged with the callback parameters added to an event. The callback parameters added to an event have precedence over the session callback parameters. Meaning that, when adding a callback parameter to an event with the same key to one added from the session, the value that prevails is the callback parameter added to the event.

It's possible to remove a specific session callback parameter by passing the desiring key to the method `AdTrace.removeSessionCallbackParameter(String key)`.

```java
AdTrace.removeSessionCallbackParameter("foo");
```

If you wish to remove all keys and their corresponding values from the session callback parameters, you can reset it with the method `AdTrace.resetSessionCallbackParameters()`.

```java
AdTrace.resetSessionCallbackParameters();
```

### <a id="session-partner-parameters"></a>Session partner parameters

In the same way that there are [session callback parameters](#session-callback-parameters) sent in every event or session of the AdTrace SDK, there is also session partner parameters.

These will be transmitted to network partners, for the integrations that have been activated in your AdTrace [dashboard].

The session partner parameters have a similar interface to the event partner parameters. Instead of adding the key and it's value to an event, it's added through a call to `AdTrace.addSessionPartnerParameter(String key, String value)`:

```java
AdTrace.addSessionPartnerParameter("foo", "bar");
```

The session partner parameters will be merged with the partner parameters added to an event. The partner parameters added to an event have precedence over the session partner parameters. Meaning that, when adding a partner parameter to an event with the same key to one added from the session, the value that prevails is the partner parameter added to the event.

It's possible to remove a specific session partner parameter by passing the desiring key to the method `AdTrace.removeSessionPartnerParameter(String key)`.

```java
AdTrace.removeSessionPartnerParameter("foo");
```

If you wish to remove all keys and their corresponding values from the session partner parameters, you can reset it with the method `AdTrace.resetSessionPartnerParameters()`.

```java
AdTrace.resetSessionPartnerParameters();
```

### <a id="delay-start"></a>Delay start

Delaying the start of the AdTrace SDK allows your app some time to obtain session parameters, such as unique identifiers, to be sent on install.

Set the initial delay time in seconds with the method `setDelayStart` in the `AdTraceConfig` instance:

```java
adtraceConfig.setDelayStart(5.5);
```

In this case, this will make the AdTrace SDK not send the initial install session and any event created for 5.5 seconds. After this time is expired or if you call `AdTrace.sendFirstPackages()` in the meanwhile, every session parameter will be added to the delayed install session and events and the AdTrace SDK will resume as usual.

**The maximum delay start time of the AdTrace SDK is 10 seconds**.

### <a id="attribution-callback"></a>Attribution callback

You can register a listener to be notified of tracker attribution changes. Due to the different sources considered for attribution, this information can not be provided synchronously. The simplest way is to create a single anonymous listener:

Please make sure to consider our [applicable attribution data policies][attribution-data].

With the `AdTraceConfig` instance, before starting the SDK, add the anonymous listener:

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);

config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
    @Override
    public void onAttributionChanged(AdTraceAttribution attribution) {
    }
});

AdTrace.onCreate(config);
```

Alternatively, you could implement the `OnAttributionChangedListener` interface in your `Application` class and set it as listener:

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
config.setOnAttributionChangedListener(this);
AdTrace.onCreate(config);
```

The listener function will be called after the SDK receives the final attribution data. Within the listener function you have access to the `attribution` parameter. Here is a quick summary of its properties:

- `String trackerToken` the tracker token of the current attribution.
- `String trackerName` the tracker name of the current attribution.
- `String network` the network grouping level of the current attribution.
- `String campaign` the campaign grouping level of the current attribution.
- `String adgroup` the ad group grouping level of the current attribution.
- `String creative` the creative grouping level of the current attribution.
- `String clickLabel` the click label of the current attribution.
- `String adid` the AdTrace device identifier.

If any value is unavailable, it will default to `null`.

### <a id="session-event-callbacks"></a>Session and event callbacks

You can register a listener to be notified when events or sessions are tracked. There are four listeners: one for tracking successful events, one for tracking failed events, one for tracking successful sessions and one for tracking failed sessions. You can add any number of listeners after creating the `AdTraceConfig` object:

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

The listener function will be called after the SDK tries to send a package to the server. Within the listener function you have access to a response data object specifically for the listener. Here is a quick summary of the success session response data object fields:

- `String message` the message from the server or the error logged by the SDK.
- `String timestamp` timestamp from the server.
- `String adid` a unique device identifier provided by AdTrace.
- `JSONObject jsonResponse` the JSON object with the reponse from the server.

Both event response data objects contain:

- `String eventToken` the event token, if the package tracked was an event.
- `String callbackId` the custom defined callback ID set on event object.

If any value is unavailable, it will default to `null`.

And both event and session failed objects also contain:

- `boolean willRetry` indicates that will be an attempt to resend the package at a later time.

### <a id="disable-tracking"></a>Disable tracking

You can disable the AdTrace SDK from tracking any activities of the current device by calling `setEnabled` with parameter `false`. **This setting is remembered between sessions**.

```java
AdTrace.setEnabled(false);
```

You can check if the AdTrace SDK is currently enabled by calling the function `isEnabled`. It is always possible to activatе the AdTrace SDK by invoking `setEnabled` with the enabled parameter as `true`.

### <a id="offline-mode"></a>Offline mode

You can put the AdTrace SDK in offline mode to suspend transmission to our servers, while retaining tracked data to be sent later. While in offline mode, all information is saved in a file, so be careful not to trigger too many events while in offline mode.

You can activate offline mode by calling `setOfflineMode` with the parameter `true`.

```java
AdTrace.setOfflineMode(true);
```

Conversely, you can deactivate offline mode by calling `setOfflineMode` with `false`. When the AdTrace SDK is put back in online mode, all saved information is sent to our servers with the correct time information.

Unlike disabling tracking, this setting is **not remembered** between sessions. This means that the SDK is in online mode whenever it is started, even if the app was terminated in offline mode.

### <a id="event-buffering"></a>Event buffering

If your app makes heavy use of event tracking, you might want to delay some HTTP requests in order to send them in one batch every minute. You can enable event buffering with your `AdTraceConfig` instance:

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
config.setEventBufferingEnabled(true);
AdTrace.onCreate(config);
```

### <a id="gdpr-forget-me"></a>GDPR right to be forgotten

In accordance with article 17 of the EU's General Data Protection Regulation (GDPR), you can notify AdTrace when a user has exercised their right to be forgotten. Calling the following method will instruct the AdTrace SDK to communicate the user's choice to be forgotten to the AdTrace backend:

```java
AdTrace.gdprForgetMe(context);
```

Upon receiving this information, AdTrace will erase the user's data and the AdTrace SDK will stop tracking the user. No requests from this device will be sent to AdTrace in the future.

### <a id="sdk-signature"></a>SDK signature

An account manager must activate the AdTrace SDK signature. Contact AdTrace support (info@adtrace.io) if you are interested in using this feature.

If the SDK signature has already been enabled on your account and you have access to App Secrets in your AdTrace Dashboard, please use the method below to integrate the SDK signature into your app.

An App Secret is set by calling `setAppSecret` on your `AdTraceConfig` instance:

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
config.setAppSecret(secretId, info1, info2, info3, info4);
AdTrace.onCreate(config);
```

### <a id="background-tracking"></a>Background tracking

The default behaviour of the AdTrace SDK is to pause sending HTTP requests while the app is in the background. You can change this in your `AdTraceConfig` instance:

```java
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
config.setSendInBackground(true);
AdTrace.onCreate(config);
```

### <a id="device-ids"></a>Device IDs

The AdTrace SDK offers you possibility to obtain some of the device identifiers.

### <a id="di-gps-adid"></a>Google Play Services advertising identifier

Certain services (such as Google Analytics) require you to coordinate Device and Client IDs in order to prevent duplicate reporting.

If you need to obtain the Google Advertising ID, there is a restriction that only allows it to be read in a background thread. If you call the function `getGoogleAdId` with the context and a `OnDeviceIdsRead` instance, it will work in any situation:

```java
AdTrace.getGoogleAdId(this, new OnDeviceIdsRead() {
    @Override
    public void onGoogleAdIdRead(String googleAdId) {
        // ...
    }
});
```

Inside the method `onGoogleAdIdRead` of the `OnDeviceIdsRead` instance, you will have access to Google Advertising ID as the variable `googleAdId`.

### <a id="di-amz-adid"></a>Amazon advertising identifier

If you need to obtain the Amazon Advertising ID, you can make a call to following method on `AdTrace` instance:

```java
String amazonAdId = AdTrace.getAmazonAdId(context);
```

### <a id="di-adid"></a>AdTrace device identifier

For each device with your app installed on it, AdTrace backend generates unique **AdTrace device identifier** (**adid**). In order to obtain this identifier, you can make a call to following method on `AdTrace` instance:

```java
String adid = AdTrace.getAdid();
```
**Note**: Information about **adid** is available after app installation has been tracked by the AdTrace backend. From that moment on, AdTrace SDK has information about your device **adid** and you can access it with this method. So, **it is not possible** to access **adid** value before the SDK has been initialised and installation of your app was tracked successfully.

### <a id="user-attribution"></a>User attribution

Like described in [attribution callback section](#attribution-callback), this callback get triggered providing you info about new attribution when ever it changes. In case you want to access info about your user's current attribution when ever you need it, you can make a call to following method of the `AdTrace` instance:

```java
AdTraceAttribution attribution = AdTrace.getAttribution();
```

**Note**: Information about current attribution is available after app installation has been tracked by the AdTrace backend and attribution callback has been initially triggered. From that moment on, AdTrace SDK has information about your user's attribution and you can access it with this method. So, **it is not possible** to access user's attribution value before the SDK has been initialized and attribution callback has been initially triggered.

### <a id="push-token"></a>Push token

Push tokens are used for Audience Builder and client callbacks, and they are required for uninstall and reinstall tracking.

To send us the push notification token, add the following call to AdTrace once you have obtained your token or when ever it's value is changed:

```java
AdTrace.setPushToken(pushNotificationsToken, context);
```

This updated signature with `context` added allows the SDK to cover more scenarios to make sure that the push token is sent, and it is advised that you use the signature method above.

We still support the previous signature of the same method:

```java
AdTrace.setPushToken(pushNotificationsToken);
```

### <a id="pre-installed-trackers"></a>Pre-installed trackers

If you want to use the AdTrace SDK to recognize users whose devices came with your app pre-installed, follow these steps.

- Create a new tracker in your [dashboard].
- Open your app delegate and add set the default tracker of your `AdTraceConfig`:

  ```java
  AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
  config.setDefaultTracker("{TrackerToken}");
  AdTrace.onCreate(config);
  ```

  Replace `{TrackerToken}` with the tracker token you created in step 1. Please note that the Dashboard displays a tracker URL (including `http://app.adtrace.io/`). In your source code, you should specify only the six-character token and not the entire URL.

- Build and run your app. You should see a line like the following in your LogCat:

    ```
    Default tracker: 'abc123'
    ```

### <a id="deeplinking"></a>Deep linking

If you are using an AdTrace tracker URL with the option to deep link into your app, there is the possibility to get information about the deep link URL and its content. Hitting the URL can happen when the user has your app already installed (standard deep linking scenario) or if they don't have the app on their device (deferred deep linking scenario). In the standard deep linking scenario, the Android platform natively offers the possibility for you to get the information about the deep link content. The deferred deep linking scenario is something which the Android platform doesn't support out of the box, and, in this case, the AdTrace SDK will offer you the mechanism you need to get the information about the deep link content.

### <a id="deeplinking-standard"></a>Standard deep linking scenario

If a user has your app installed and you want it to launch after hitting an AdTrace tracker URL with the `deep_link` parameter in it, you need to enable deep linking in your app. This is done by choosing a desired **unique scheme name** and assigning it to the Activity you want to launch once your app opens following a user clicking on the tracker URL. This is set in the `AndroidManifest.xml`. You need to add the `intent-filter` section to your desired Activity definition in the manifest file and assign an `android:scheme` property value with the desired scheme name:

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

With this now set, you need to use the assigned scheme name in the AdTrace tracker URL's `deep_link` parameter if you want your app to launch once the tracker URL is clicked. A tracker URL without any information added to the deep link can be built to look something like this:

```
https://app.adtrace.io/abc123?deep_link=adtraceExample%3A%2F%2F
```

Please, have in mind that the `deep_link` parameter value in the URL **must be URL encoded**.

After clicking this tracker URL, and with the app set as described above, your app will launch along with the `MainActivity` intent. Inside the `MainActivity` class, you will automatically be provided with the information about the `deep_link` parameter content. Once this content is delivered to you, it **will not be encoded**, although it was encoded in the URL.

Depending on the `android:launchMode` setting of your Activity in the `AndroidManifest.xml` file, information about the `deep_link` parameter content will be delivered to the appropriate place in the Activity file. For more information about the possible values of the `android:launchMode` property, check [the official Android documentation][android-launch-modes].

There are two places within your desired Activity where information about the deep link content will be delivered via the `Intent` object - either in the Activity's `onCreate` or `onNewIntent` methods. Once your app has launched and one of these methods has been triggered, you will be able to get the actual deep link passed in the `deep_link` parameter in the click URL. You can then use this information to conduct some additional logic in your app.

You can extract the deep link content from these two methods like this:

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

### <a id="deeplinking-deferred"></a>Deferred deep linking scenario

The deferred deep linking scenario occurs when a user clicks on an AdTrace tracker URL with a `deep_link` parameter in it, but does not have the app installed on the device at click time. After that, the user will be redirected to the Play Store to download and install your app. After opening it for the first time, the `deep_link` parameter content will be delivered to your app.

In order to get information about the `deep_link` parameter content in a deferred deep linking scenario, you should set a listener method on the `AdTraceConfig` object. This will be triggered once the AdTrace SDK gets the information about the deep link content from the AdTrace backend.

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

Once the AdTrace SDK receives the information about the deep link content from the AdTrace backend, it will deliver you the information about its content in this listener and expect the `boolean` return value from you. This return value represents your decision on whether the AdTrace SDK should launch the Activity to which you have assigned the scheme name from the deep link (like in the standard deep linking scenario) or not.

If you return `true`, we will launch it and the exact same scenario which is described in the [Standard deep linking scenario chapter](#deeplinking-standard) will happen. If you do not want the SDK to launch the Activity, you can return `false` from this listener, and, based on the deep link content, decide on your own what to do next in your app.

### <a id="deeplinking-reattribution"></a>Reattribution via deep links

AdTrace enables you to run re-engagement campaigns through deep links. For more information on how to do that, please check our [official docs][reattribution-with-deeplinks].

If you are using this feature, in order for your user to be properly reattributed, you need to make one additional call to the AdTrace SDK in your app.

Once you have received deep link content information in your app, add a call to the `AdTrace.appWillOpenUrl(Uri, Context)` method. By making this call, the AdTrace SDK will try to find if there is any new attribution information inside of the deep link. If there is any, it will be sent to the AdTrace backend. If your user should be reattributed due to a click on the AdTrace tracker URL with deep link content, you will see the [attribution callback](#attribution-callback) in your app being triggered with new attribution info for this user.

The call to `AdTrace.appWillOpenUrl(Uri, Context)` should be done like this:

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

## <a id="troubleshooting"></a>Troubleshooting

### <a id="ts-session-failed"></a>I'm seeing the "Session failed (Ignoring too frequent session. ...)" error.

This error typically occurs when testing installs. Uninstalling and reinstalling the app is not enough to trigger a new install. The servers will determine that the SDK has lost its locally aggregated session data and ignore the erroneous message, given the information available on the servers about the device.

This behaviour can be cumbersome during tests, but is necessary in order to have the sandbox behaviour match production as much as possible.

You can reset the session data of the device in our servers. Check the error message in the logs:

```
Session failed (Ignoring too frequent session. Last session: YYYY-MM-DDTHH:mm:ss, this session: YYYY-MM-DDTHH:mm:ss, interval: XXs, min interval: 20m) (app_token: {yourAppToken}, adid: {adidValue})
```

With the `{yourAppToken}` and `{adidValue}`/`{gps_adidValue}`/`{androidIDValue}` values filled in below, open one of the 
following links:


```
http://app.adtrace.io/forget_device?app_token={yourAppToken}&adid={adidValue}
```

```
http://app.adtrace.io/forget_device?app_token={yourAppToken}&gps_adid={gps_adidValue}
```

```
http://app.adtrace.io/forget_device?app_token={yourAppToken}&android_id={androidIDValue}
```

When the device is forgotten, the link just returns `Forgot device`. If the device was already forgotten or the values were incorrect, the link returns `Device not found`.

### <a id="ts-broadcast-receiver"></a>Is my broadcast receiver capturing the install referrer?

If you followed the instructions in the [guide](#broadcast_receiver), the broadcast receiver should be configured to send the install referrer to our SDK and to our servers.

You can test this by triggering a test install referrer manually. Replace `com.your.appid` with your app ID and run the following command with the [adb](http://developer.android.com/tools/help/adb.html) tool that comes with Android Studio:

```
adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n com.your.appid/io.adtrace.sdk.AdTraceReferrerReceiver --es "referrer" "adtrace_reftag%3Dabc1234%26tracking_id%3D123456789%26utm_source%3Dnetwork%26utm_medium%3Dbanner%26utm_campaign%3Dcampaign"
```

If you are already using a different broadcast receiver for the `INSTALL_REFERRER` intent and followed this [guide][referrer], replace `io.adtrace.sdk.AdTraceReferrerReceiver` with your broadcast receiver.

You can also remove the `-n com.your.appid/io.adtrace.sdk.AdTraceReferrerReceiver` parameter so that all the apps in the device will receive the `INSTALL_REFERRER` intent.

If you set the log level to `verbose`, you should be able to see the log from reading the referrer:

```
V/AdTrace: Referrer to parse (adtrace_reftag=abc1234&tracking_id=123456789&utm_source=network&utm_medium=banner&utm_campaign=campaign) from reftag
```

And a click package added to the SDK's package handler:

```
V/AdTrace: Path:      /sdk_click
    ClientSdk: android4.6.0
    Parameters:
      app_token        abc123abc123
      click_time       yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z
      created_at       yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z
      environment      sandbox
      gps_adid         12345678-0abc-de12-3456-7890abcdef12
      needs_attribution_data 1
      referrer         adtrace_reftag=abc1234&tracking_id=123456789&utm_source=network&utm_medium=banner&utm_campaign=campaign
      reftag           abc1234
      source           reftag
      tracking_enabled 1
```

If you perform this test before launching the app, you won't see the package being sent. The package will be sent once the app is launched.

**Important:** Please be aware that usage of `adb` tool for testing this particular feature is not the best way to go. In order to test your full referrer content (in case you have multiple parameters separated with `&`), with `adb` you actually need to encode that content in order to get it into your broadcast receiver. If you don't encode it, `adb` will cut your referrer after first `&` sign and deliver wrong content to your broadcast receiver.

If you would like to see how an app receives unencoded referrer value, you can try our example app and alter content being passed to be fired with intent inside of the `onFireIntentClick` method inside `MainActivity.java` file:

```java
public void onFireIntentClick(View v) {
    Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
    intent.setPackage("com.adtrace.examples");
    intent.putExtra("referrer", "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test");
    sendBroadcast(intent);
}
```

Feel free to alter second parameter of `putExtra` method with content of your choice.

### <a id="ts-event-at-launch"></a>Can I trigger an event at application launch?

Not how you might intuitively think. The `onCreate` method on the global `Application` class is called not only at application launch, but also when a system or application event is captured by the app.

Our SDK is prepared for initialization at this time, but not actually started. This will only happen when an activity is started, i.e., when a user actually launches the app.

That's why triggering an event at this time will not do what you would expect. Such calls will start the AdTrace SDK and send the events, even when the app was not launched by the user - at a time that depends on external factors of the app.

Triggering events at application launch will thus result in inaccuracies in the number of installs and sessions tracked.

If you want to trigger an event after the install, use the [attribution callback](#attribution-callback).

If you want to trigger an event when the app is launched, use the `onCreate` method of the Activity which is started.

[dashboard]:  http://adtrace.io
[adtrace.io]: http://adtrace.io
[en-readme]:  README.md

[maven]:                          http://maven.org
[releases]:                       https://github.com/moryzaky/adtrace_sdk_android/releases
[referrer]:                       doc/english/referrer.md
[google_ad_id]:                   https://support.google.com/googleplay/android-developer/answer/6048248?hl=en
[event-tracking]:                 https://docs.adtrace.io/en/event-tracking
[callbacks-guide]:                https://docs.adtrace.io/en/callbacks
[new-referrer-api]:               https://developer.android.com/google/play/installreferrer/library.html
[application_name]:               http://developer.android.com/guide/topics/manifest/application-element.html#nm
[special-partners]:               https://docs.adtrace.io/en/special-partners
[attribution-data]:               htttp://adtrace.io
[android-dashboard]:              http://developer.android.com/about/dashboards/index.html
[currency-conversion]:            https://docs.adtrace.io/en/event-tracking/#tracking-purchases-in-different-currencies
[android_application]:            http://developer.android.com/reference/android/app/Application.html
[android-launch-modes]:           https://developer.android.com/guide/topics/manifest/activity-element.html
[google_play_services]:           http://developer.android.com/google/play-services/setup.html
[activity_resume_pause]:          doc/activity_resume_pause.md
[reattribution-with-deeplinks]:   https://docs.adtrace.io/en/deeplinking/#manually-appending-attribution-data-to-a-deep-link
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTkxNTQ4NzEsMTM5NDk4MDEwNywtNTQxMj
U4NDE2LC0xNDg2MzQ5NjI1LC0xMzMyMDIyNjMxLC0xMzUwMjky
Mzk5XX0=
-->