<div dir="rtl" align='right'>فارسی | <a href="../../README.md">English</a></div>

## <div dir="rtl" align='right'>خلاصه</div>

<div dir="rtl" align='right'>
SDK اندروید ادتریس. شما برای اطلاعات بیشتر میتوانید به <a href="adtrace.io">adtrace.io</a>  مراجعه کنید.
</div>

## <div dir="rtl" align='right'>فهرست محتوا</div>

### <div dir="rtl" align='right'>پیاده سازی فوری</div>

<div dir="rtl" align='right'>
<ul>
  <li><a href="#qs-getting-started">شروع پیاده سازی</a>
  <ul>
    <li><a href="#qs-add-sdk">افزودن SDK به پروژه</a></li>
    <li><a href="#qs-add-gps">افزودن سرویس های گوگل پلی</a></li>
    <li><a href="#qs-add-permissions">افزودن مجوزها</a>
    <ul>
      <li><a href="#qs-proguard-settings">تنظیمات Proguard</a></li>
      <li><a href="#qs-ir">تنظیمات Install referrer</a>
          <ul>
            <li><a href="#qs-ir-gpr-api">Google Play Referrer API</a></li>
            <li><a href="#qs-ir-gps-intent">Google Play Store intent</a></li>
        </ul>
      </li>
    </ul>
    </li>
  </ul>
  <li><a href="#qs-integ-sdk">پیاده سازی SDK داخل برنامه</a>
    <ul>
      <li><a href="#qs-integ-basic-setup">راه اندازی اولیه</a></li>
      <ul>
        <li><a href="#qs-integ-basic-setup-native">Native App SDK</a></li>
        <li><a href="#qs-integ-basic-setup-web">Web View SDK</a></li>
      </ul>
      <li><a href="#qs-integ-session-tracking">ردیابی نشست </a></li>
      <ul>
        <li><a href="#qs-integ-session-tracking-api14"> API بالاتر از 14</a></li>
        <li><a href="#qs-integ-session-tracking-api9">API بین 9 و 13</a></li>
      </ul>
      <li><a href="#qs-sdk-signature">امضا SDK</a></li>                 
      <li><a href="#qs-adtrace-logging">لاگ ادتریس</a></li>
      <li><a href="#qs-build-the-app">ساخت برنامه</a></li>
    </ul>
    </li>
  </ul>
  </li>
</ul>
</div>

### <div dir="rtl" align='right'>لینک دهی عمیق</div>

<div dir="rtl" align='right'>
<ul>
  <li><a href="#dl-overview">نمای کلی لینک دهی عمیق</a></li>                  
  <li><a href="#dl-standard">سناریو لینک دهی عمیق استاندار</a></li>
  <li><a href="#dl-deferred">سناریو لینک دهی عمیق به تعویق افتاده</a></li>
  <li><a href="#dl-reattribution">اتریبیوت مجدد از طریق لینک عمیق</a></li>
</ul>
</div>

### <div dir="rtl" align='right'>ردیابی رویداد</div>

<div dir="rtl" align='right'>
<ul>
  <li><a href="#et-track-event">ردیابی رویداد معمولی</a></li>                 
  <li><a href="#et-track-revenue">ردیابی رویداد درآمدی</a></li>
  <li><a href="#et-revenue-deduplication">جلوگیری از تکرار رویداد درآمدی</a></li>
</ul>
</div>

### <div dir="rtl" align='right'>پارامترهای سفارشی</div>

<div dir="rtl" align='right'>
<ul>
  <li><a href="#cp-overview">نمای کلی پارامترهای سفارشی</a></li>
  <li><a href="#cp-ep">پارامترهای رویداد</a>
    <ul>
      <li><a href="#cp-ep-callback">پارامترهای callback رویداد</a></li>                 
      <li><a href="#cp-ep-partner">پارامترهای partner رویداد</a></li>
      <li><a href="#cp-ep-id">شناسه callback رویداد</a></li>
      <li><a href="#cp-ep-value">مقدار رویداد</a></li>
    </ul>
  </li>                 
  <li><a href="#cp-sp" >پارامترهای نشست</a>
    <ul>
      <li><a href="#cp-sp-callback">پارامترهای callback نشست</a></li>                 
      <li><a href="#cp-sp-partner">پارامترهای partner نشست</a></li>
      <li><a href="#cp-sp-delay-start">شروع با تاخیر</a></li>
    </ul>
  </li>
</ul>
</div>

### <div dir="rtl" align='right'>ویژگی های بیشتر</div>

<div dir="rtl" align='right'>
<ul>
  <li><a href="#af-push-token">توکن push (ردیابی تعداد حذف برنامه)</a></li> 
  <li><a href="#af-attribution-callback">callback اتریبیوشن</a></li>
  <li><a href="#af-session-event-callbacks">callback های رویداد و نشست</a></li>
  <li><a href="#af-user-attribution">اتریبیوشن کاربر</a></li>                 
  <li><a href="#af-send-installed-apps">ارسال برنامه های نصب شده دستگاه</a></li>                  
  <li><a href="#af-di">شناسه های دستگاه</a>
    <ul>
      <li><a href="#af-di-gps-adid">شناسه تبلیغات سرویس های گوگل پلی</a></li>                 
      <li><a href="#af-di-amz-adid">شناسه تبلیغات آمازون</a></li>
      <li><a href="#af-di-adid">شناسه دستگاه ادتریس</a></li>
    </ul>
  </li>                 
  <li><a href="#af-pre-installed-trackers">ردیابی قبل از نصب</a></li>                 
  <li><a href="#af-offline-mode">حالت آفلاین</a></li>                 
  <li><a href="#af-disable-tracking">غیرفعال کردن ردیابی</a></li>                 
  <li><a href="#af-event-buffering">بافرکردن رویدادها</a></li>                  
  <li><a href="#af-background-tracking">ردیابی در پس زمینه</a></li>                 
  <li><a href="#af-location">موقعیت مکانی</a></li>                  
  <li><a href="#af-gdpr-forget-me">GPDR</a></li>                  
</ul>
</div>

### <div dir="rtl" align='right'>تست و عیب یابی</div>

<div dir="rtl" align='right'>
<ul>
  <li><a href="#ts-reset-gps-ad-id">چگونه میتوانم شناسه تبلیغات سرویس گوگل را ریست کنم؟</a></li>                  
  <li><a href="#ts-session-failed">من ارور "Session failed (Ignoring too frequent session. ...)" را مشاهده میکنم.</a></li>
  <li><a href="#ts-broadcast-receiver">آیا broadcast receiver من اطلاعات install referrer را دریافت میکند؟</a></li>
  <li><a href="#ts-event-at-launch">آیا می توانم هنگام راه اندازی برنامه اقدام به ایجاد یک رویداد کنم؟</a></li>
</ul>
</div>

## <div id="qs-getting-started" dir="rtl" align='right'>شروع پیاده سازی</div>

These are the minimal steps required to integrate the AdTrace SDK into your Android project. We are going to assume that you use Android Studio for your Android development and target an Android API level **9 (Gingerbread)** or later.

### <div id="qs-add-sdk" dir="rtl" align='right'>افزودن SDK به پروژه</div>

If you are using Maven, add the following to your `build.gradle` file:

```gradle
implementation 'io.adtrace:android-sdk:1.0.2'
implementation 'com.android.installreferrer:installreferrer:1.0'
```

If you would prefer to use the AdTrace SDK inside web views in your app, please include this additional dependency as well:

```gradle
implementation 'io.adtrace:android-sdk-plugin-webbridge:1.0.2'
```

### <div id="qs-add-gps" dir="rtl" align='right'>افزودن سرویس های گوگل پلی</div>

Since the 1st of August of 2014, apps in the Google Play Store must use the [Google Advertising ID][google_ad_id] to uniquely identify devices. To enable the Google Advertising ID for our SDK, you must integrate [Google Play Services][google_play_services]. If you haven't done this yet, please add dependency to the Google Play Services library by adding the following dependecy to your `dependencies` block of app's `build.gradle` file:

```gradle
implementation 'com.google.android.gms:play-services-analytics:16.0.4'
```

**Note**: The AdTrace SDK is not tied to any specific version of the `play-services-analytics` part of the Google Play Services library, so feel free to always use the latest version of it (or whichever you might need).

### <div id="qs-add-permissions" dir="rtl" align='right'>افزودن مجوزها</div>

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

### <div id="qs-proguard-settings" dir="rtl" align='right'>تنظیمات Proguard</div>

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

### <div id="qs-ir" dir="rtl" align='right'>تنظیمات Install referrer</div>

In order to correctly attribute an install of your app to its source, AdTrace needs information about the **install referrer**. This can be obtained by using the **Google Play Referrer API** or by catching the **Google Play Store intent** with a broadcast receiver.

**Important**: The Google Play Referrer API is newly introduced by Google with the express purpose of providing a more reliable and secure way of obtaining install referrer information and to aid attribution providers in the fight against click injection. It is **strongly advised** that you support this in your application. The Google Play Store intent is a less secure way of obtaining install referrer information. It will continue to exist in parallel with the new Google Play Referrer API temporarily, but it is set to be deprecated in future.

#### <div id="qs-ir-gpr-api" dir="rtl" align='right'>Google Play Referrer API</div>

In order to support this in your app, please make sure that you have followed the [Add the SDK to your project](#sdk-add) chapter properly and that you have following line added to your `build.gradle` file:

```gradle
implementation 'com.android.installreferrer:installreferrer:1.0'
```

Also, make sure that you have paid attention to the [Proguard settings](#sdk-proguard) chapter and that you have added all the rules mentioned in it, especially the one needed for this feature:

```
-keep public class com.android.installreferrer.** { *; }
```

#### <div id="qs-ir-gps-intent" dir="rtl" align='right'>Google Play Store intent</div>

**Note**: Google has [announced](https://android-developers.googleblog.com/2019/11/still-using-installbroadcast-switch-to.html) deprecation of `INSTALL_REFERRER` intent usage to deliver referrer information as of March 1st 2020. If you are using this way of accessing referrer information, please migrate to [Google Play Referrer API](#gpr-api) approach.

You should capture the Google Play Store `INSTALL_REFERRER` intent with a broadcast receiver. If you are **not using your own broadcast receiver** to receive the `INSTALL_REFERRER` intent, add the following `receiver` tag inside the `application` tag in your `AndroidManifest.xml`.

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

### <div id="qs-integ-sdk" dir="rtl" align='right'>پیاده سازی SDK داخل برنامه</div>

First, we'll set up basic session tracking.

### <div id="qs-integ-basic-setup" dir="rtl" align='right'>راه اندازی اولیه</div>

If you are integrating the SDK into a native app, follow the directions for a [Native App SDK](#basic-setup-native).
If you are integrating the SDK for usage inside web views, please follow the directions for a [Web View SDK](#basic-setup-web) below.

#### <div id="qs-integ-basic-setup-native" dir="rtl" align='right'>Native App SDK</div>

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

#### <div id="qs-integ-basic-setup-web" dir="rtl" align='right'>Web View SDK</div>

After you have obtained the reference to your `WebView` object:

- Call `webView.getSettings().setJavaScriptEnabled(true)`, to enable Javascript in the web view
- Start the default instance of `AdTraceBridgeInstance` by calling `AdTraceBridge.registerAndGetInstance(getApplication(), webview)`
- This will also register the AdTrace bridge as a Javascript Interface to the web view

After these steps, your activity should look like this:

```java
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        AdTraceBridge.registerAndGetInstance(getApplication(), webview);
        try {
            webView.loadUrl("file:///android_asset/AdTraceExample-WebView.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

After you complete this step, you will have successfully added the AdTrace bridge to your app. The Javascript bridge is now enabled to communicate between AdTrace's native Android SDK and your page, which will be loaded in the web view.

In your HTML file, import the AdTrace Javascript files which are located in the root of the [assets folder][android-webbridge-asset]. If your HTML file is there as well, import them like this:

```html
<script type="text/javascript" src="adtrace.js"></script>
<script type="text/javascript" src="adtrace_event.js"></script>
<script type="text/javascript" src="adtrace_config.js"></script>
```

Once you add your references to the Javascript files, use them in your HTML file to initialise the AdTrace SDK:

```js
let yourAppToken = '{YourAppToken}';
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

**Important:** This value should be set to `AdTraceConfig.ENVIRONMENT_SANDBOX` if and only if you or someone else is testing your app. Make sure to set the environment to `AdTraceConfig.ENVIRONMENT_PRODUCTION` before you publish the app. Set it back to `AdTraceConfig.ENVIRONMENT_SANDBOX` when you start developing and testing it again.

We use this environment to distinguish between real traffic and test traffic from test devices. It is imperative that you keep this value meaningful at all times.

To increase the accuracy and security in fraud detection, you can enable or disable the sending of installed applications on user's device as follows:

### <div id="qs-integ-session-tracking" dir="rtl" align='right'>ردیابی نشست</div>

**Note**: This step is **really important** and please **make sure that you implement it properly in your app**. By implementing it, you will enable proper session tracking by the AdTrace SDK in your app.

### <div id="qs-integ-session-tracking-api14" dir="rtl" align='right'>API بالاتر از 14</div>

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

### <div id="qs-integ-session-tracking-api9" dir="rtl" align='right'>API بین 9 و 13</div>

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

Repeat these steps for **every Activity** of your app. Don't forget these steps when you create new Activities in the future. Depending on your coding style you might want to implement this in a common superclass of all your Activities.

### <div id="qs-sdk-signature" dir="rtl" align='right'>امضا SDK</div>

An account manager must activate the AdTrace SDK signature. Contact AdTrace support (info@adtrace.io) if you are interested in using this feature.

If the SDK signature has already been enabled on your account and you have access to App Secrets in your AdTrace Dashboard, please use the method below to integrate the SDK signature into your app.

An App Secret is set by calling `setAppSecret` on your `AdTraceConfig` instance:

<br/>
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

### <div id="qs-adtrace-logging" dir="rtl" align='right'>لاگ ادتریس</div>

You can increase or decrease the amount of logs that you see during testing by calling `setLogLevel` on your `AdTraceConfig` instance with one of the following parameters:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
adtraceConfig.setLogLevel(LogLevel.VERBOSE);   // enable all logging
adtraceConfig.setLogLevel(LogLevel.DEBUG);     // enable more logging
adtraceConfig.setLogLevel(LogLevel.INFO);      // the default
adtraceConfig.setLogLevel(LogLevel.WARN);      // disable info logging
adtraceConfig.setLogLevel(LogLevel.ERROR);     // disable warnings as well
adtraceConfig.setLogLevel(LogLevel.ASSERT);    // disable errors as well
adtraceConfig.setLogLevel(LogLevel.SUPRESS);   // disable all log output
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

In case you want all your log output to be disabled, beside setting the log level to suppress, you should also use  constructor for `AdTraceConfig` object which gets boolean parameter indicating whether suppress log level should be supported  or not:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>


```java
AdTraceConfig adtraceConfig = new AdTraceConfig(this, appToken, environment, true);
adtraceConfig.setLogLevel(LogLevel.SUPRESS);
AdTrace.onCreate(adtraceConfig);
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
let adtraceConfig = new AdTraceConfig(appToken, environment, true);
adtraceConfig.setLogLevel(AdTraceConfig.LogLevelSuppress);
AdTrace.onCreate(adtraceConfig);
```
</td>
</tr>
</table>

### <div id="qs-build-the-app" dir="rtl" align='right'>ساخت برنامه</div>

Build and run your Android app. In your `LogCat` viewer you can set the filter `tag:AdTrace` to hide all other logs. After your app has launched you should see the following AdTrace log: `Install tracked`.

## <div dir="rtl" align='right'>Deep linking</div>

### <div id="dl-overview" dir="rtl" align='right'>نمای کلی لینک دهی عمیق</div>

If you are using an AdTrace tracker URL with the option to deep link into your app, there is the possibility to get information about the deep link URL and its content. Hitting the URL can happen when the user has your app already installed (standard deep linking scenario) or if they don't have the app on their device (deferred deep linking scenario). In the standard deep linking scenario, the Android platform natively offers the possibility for you to get the information about the deep link content. The deferred deep linking scenario is something which the Android platform doesn't support out of the box, and, in this case, the AdTrace SDK will offer you the mechanism you need to get the information about the deep link content.

### <div id="dl-standard" dir="rtl" align='right'>سناریو لینک دهی عمیق استاندار</div>

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

### <div id="dl-deferred" dir="rtl" align='right'>سناریو لینک دهی عمیق به تعویق افتاده</div>

Deferred deeplinking scenario occurs when a user clicks on an AdTrace tracker URL with a `deep_link` parameter contained in it, but does not have the app installed on the device at click time. When the user clicks the URL, they will be redirected to the Play Store to download and install your app. After opening it for the first time, `deep_link` parameter content will be delivered to your app.

The AdTrace SDK opens the deferred deep link by default. There is no extra configuration needed.

#### <div dir="rtl" align='right'>Deferred deep linking callback</div>

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
let adtraceConfig = new AdTraceConfig(appToken, environment);
adtraceConfig.setDeferredDeeplinkCallback(function (deeplink) {});

AdTrace.onCreate(adtraceConfig);
```

In this deferred deep linking scenario, there is one additional setting you can set on the config object. Once the AdTrace SDK gets the deferred deep link information, you have the possibility to choose whether our SDK opens the URL or not. Set this option by calling the `setOpenDeferredDeeplink` method on the config object:

```js
// ...

function deferredDeeplinkCallback(deeplink) {}

let adtraceConfig = new AdTraceConfig(appToken, environment);
adtraceConfig.setOpenDeferredDeeplink(true);
adtraceConfig.setDeferredDeeplinkCallback(deferredDeeplinkCallback);

AdTrace.start(adtraceConfig);

```

Remember that if you do not set the callback, **the AdTrace SDK will always attempt to launch the URL by default**.
</td>
</tr>
</table>

### <div id="dl-reattribution" dir="rtl" align='right'>اتریبیوت مجدد از طریق لینک عمیق</div>

AdTrace enables you to run re-engagement campaigns through deep links.

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

**Note for web view**: This call can also be made from the web view with the function `AdTrace.appWillOpenUrl` in Javascript like so:

```js
AdTrace.appWillOpenUrl(deeplinkUrl);
```

## <div dir="rtl" align='right'>Event tracking</div>

### <div id="et-track-event" dir="rtl" align='right'>ردیابی رویداد معمولی</div>

You can use AdTrace to track any event in your app. Suppose you want to track every tap on a button. You would have to create a new event token in your [dashboard]. Let's say that event token is `abc123`. In your button's `onClick` method you could then add the following lines to track the click:

```java
AdTraceEvent event = new AdTraceEvent("abc123");
AdTrace.trackEvent(event);
```

### <div id="et-track-revenue" dir="rtl" align='right'>ردیابی رویداد درآمدی</div>

If your users can generate revenue by tapping on advertisements or making in-app purchases you can track those revenues with events. Lets say a tap is worth one Euro cent. You could then track the revenue event like this:

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.setRevenue(0.01, "EUR");
AdTrace.trackEvent(event);
```

This can be combined with callback parameters of course.

When you set a currency token, AdTrace will automatically convert the incoming revenues into a reporting revenue of your choice.

### <div id="et-revenue-deduplication" dir="rtl" align='right'>جلوگیری از تکرار رویداد درآمدی</div>

You can also add an optional order ID to avoid tracking duplicate revenues. The last ten order IDs are remembered, and revenue events with duplicate order IDs are skipped. This is especially useful for in-app purchase tracking. You can see an  example below.

If you want to track in-app purchases, please make sure to call `trackEvent` only if the purchase is finished and item is purchased. That way you can avoid tracking revenue that is not actually being generated.

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.setRevenue(0.01, "EUR");
event.setOrderId("{OrderId}");
AdTrace.trackEvent(event);
```

## <div dir="rtl" align='right'>Custom parameters</div>

### <div id="cp-overview" dir="rtl" align='right'>نمای کلی پارامترهای سفارشی</div>

In addition to the data points the AdTrace SDK collects by default, you can use the AdTrace SDK to track and add as many custom values as you need (user IDs, product IDs, etc.) to the event or session. Custom parameters are only available as raw data and will **not** appear in your AdTrace dashboard.

You should use **callback parameters** for the values you collect for your own internal use, and **partner parameters** for those you share with external partners. If a value (e.g. product ID) is tracked both for internal use and external partner use, we recommend you track it with both callback and partner parameters.

### <div id="cp-ep" dir="rtl" align='right'>پارامترهای رویداد</div>

### <div id="cp-ep-callback" dir="rtl" align='right'>پارامترهای callback رویداد</div>

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
AdTraceEvent adtraceEvent = new AdTraceEvent("abc123");
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
let adtraceEvent = new AdTraceEvent('abc123');
adtraceEvent.addCallbackParameter('key', 'value');
adtraceEvent.addCallbackParameter('foo', 'bar');
AdTrace.trackEvent(adtraceEvent);
```
</td>
</tr>
</table>

In this case we would track the event and send a request to:

```
http://www.example.com/callback?key=value&foo=bar
```

### <div id="cp-ep-partner" dir="rtl" align='right'>پارامترهای partner رویداد</div>

You can also add parameters to be transmitted to network partners, which have been activated in your AdTrace dashboard.

This works similarly to the callback parameters mentioned above, but can be added by calling the `addPartnerParameter` method on your `AdTraceEvent` instance.

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("abc123");
adtraceEvent.addPartnerParameter("key", "value");
adtraceEvent.addPartnerParameter("foo", "bar");
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
let adtraceEvent = new AdTraceEvent('abc123');
adtraceEvent.addPartnerParameter('key', 'value');
adtraceEvent.addPartnerParameter('foo', 'bar');
AdTrace.trackEvent(adtraceEvent);
```
</td>
</tr>
</table>

### <div id="cp-ep-id" dir="rtl" align='right'>شناسه callback رویداد</div>

You can also add custom string identifier to each event you want to track. This identifier will later be reported in event success and/or event failure callbacks to enable you to keep track on which event was successfully tracked or not. You can set this identifier by calling the `setCallbackId` method on your `AdTraceEvent` instance:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("abc123");
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
let adtraceEvent = new AdTraceEvent('abc123');
adtraceEvent.setCallbackId('Your-Custom-Id');
AdTrace.trackEvent(adtraceEvent);
```
</td>
</tr>
</table>

### <div id="cp-ep-value" dir="rtl" align='right'>مقدار رویداد</div>

You can also add custom string value to event. You can set this value by calling the `setEventValue` method on your `AdTraceEvent` instance:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
AdTraceEvent adtraceEvent = new AdTraceEvent("abc123");
adtraceEvent.setEventValue("Your-Value");
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
let adtraceEvent = new AdTraceEvent('abc123');
adtraceEvent.setEventValue('Your-Value');
AdTrace.trackEvent(adtraceEvent);
```
</td>
</tr>
</table>

### <div id="cp-sp" dir="rtl" align='right'>پارامترهای نشست</div>

Session parameters are saved locally and sent in every **event** and **session** of the AdTrace SDK. When you add any of these parameters, we will save them so you don't need to add them every time. Adding the same parameter twice will have no effect.

These session parameters can be called before the AdTrace SDK is launched (to make sure they are sent on install). If you need to send them with an install, but can only obtain the needed values after launch, it's possible to [delay](#cp-sp-delay-start) the first launch of the AdTrace SDK to allow for this behavior.

### <div id="cp-sp-callback" dir="rtl" align='right'>پارامترهای callback نشست</div>

You can save any callback parameters registered for [events](#cp-ep-callback) to be sent in every event or session of the AdTrace SDK.

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
AdTrace.addSessionCallbackParameter('foo', 'bar');
```
</td>
</tr>
</table>

Session callback parameters merge together with the callback parameters you add to an event. Callback parameters added to an event take precedence over session callback parameters, meaning that if you add a callback parameter to an event with the same key to one added from the session, the value that prevails is the callback parameter added to the event.

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
AdTrace.removeSessionCallbackParameter('foo');
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

### <div id="cp-sp-partner" dir="rtl" align='right'>پارامترهای partner نشست</div>

In the same way that [session callback parameters](#cp-sp-callback) are sent in every event or session of the AdTrace SDK, there are also session partner parameters.

These are transmitted to network partners for all of the integrations activated in your AdTrace [dashboard].

The session partner parameters interface is similar to the event partner parameters interface. Instead of adding the key and its value to an event, add it by calling `AdTrace.addSessionPartnerParameter(String key, String value)`:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
AdTrace.addSessionPartnerParameter("foo", "bar");
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
AdTrace.addSessionPartnerParameter('foo', 'bar');
```
</td>
</tr>
</table>

The session partner parameters will be merged with the partner parameters added to an event. The partner parameters added to an event take precedence over the session partner parameters. This means that when adding a partner parameter to an event with the same key to one added from the session, the value that prevails is the partner parameter added to the event.

It's possible to remove a specific session partner parameter by passing the desiring key to the method `AdTrace.removeSessionPartnerParameter(String key)`.

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
AdTrace.removeSessionPartnerParameter("foo");
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
AdTrace.removeSessionPartnerParameter('foo');
```
</td>
</tr>
</table>

If you wish to remove all keys and their corresponding values from the session partner parameters, reset it with the method `AdTrace.resetSessionPartnerParameters()`.

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
AdTrace.resetSessionPartnerParameters();
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
AdTrace.resetSessionPartnerParameters();
```
</td>
</tr>
</table>

### <div id="cp-sp-delay-start" dir="rtl" align='right'>شروع با تاخیر</div>

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

```js
adtraceConfig.setDelayStart(5.5);
```
</td>
</tr>
</table>

In this example, this will prevent the AdTrace SDK from sending the initial install session and any event created for 5.5 seconds. After the time expireds (or if you call `AdTrace.sendFirstPackages()` during that time) every session parameter will be added to the delayed install session and events; the AdTrace SDK will resume as usual.

**The maximum delay start time of the AdTrace SDK is 10 seconds**.

## <div dir="rtl" align='right'>ویژگی های بیشتر</div>


Once you have integrated the AdTrace SDK into your project, you can take advantage of the following features:

### <div id="af-push-token" dir="rtl" align='right'>توکن push (ردیابی تعداد حذف برنامه)</div>

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

**Note:** It is advised that you use the signature method above.

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

### <div id="af-attribution-callback" dir="rtl" align='right'>callback اتریبیوشن</div>

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
    public void onAttributionChanged(AdTraceAttribution attribution) {}
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

let adtraceConfig = new AdTraceConfig(appToken, environment);
adtraceConfig.setAttributionCallback(attributionCallback);
AdTrace.onCreate(adtraceConfig);
```
</td>
</tr>
</table>

The listener function is called after the SDK receives the final attribution data. Within the listener function, you'll have access to the `attribution` parameter. Here is a quick summary of its properties:

- `trackerToken` the tracker token string of the current attribution.
- `trackerName` the tracker name string of the current attribution.
- `network` the network grouping level string of the current attribution.
- `campaign` the campaign grouping level string of the current attribution.
- `adgroup` the ad group grouping level string of the current attribution.
- `creative` the creative grouping level string of the current attribution.
- `clickLabel` the click label string of the current attribution.
- `adid` the AdTrace device identifier string.

### <div id="af-session-event-callbacks" dir="rtl" align='right'>callback های رویداد و نشست</div>

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

let adtraceConfig = new AdTraceConfig(appToken, environment);
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
- `callbackId` the custom defined [callback ID](#cp-ep-id) string set on the event object.

And both event and session failed objects also contain:

- `willRetry` boolean which indicates whether there will be a later attempt to resend the package.

### <div id="af-user-attribution" dir="rtl" align='right'>اتریبیوشن کاربر</div>

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

### <div id="af-send-installed-apps" dir="rtl" align='right'>ارسال برنامه های نصب شده دستگاه</div>

To increase the accuracy and security in fraud detection, you can enable or disable the sending of installed applications on user's device as follows:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
adtraceConfig.enableSendInstalledApps(true);
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
adtraceConfig.setEnableSendInstalledApps(true);
```

</td>
</tr>
</table>

**Note**: This option is **disabled** by default.

### <div id="af-di" dir="rtl" align='right'>شناسه های دستگاه</div>

The AdTrace SDK offers you the possibility to obtain device identifiers.

### <div id="af-di-gps-adid" dir="rtl" align='right'>شناسه تبلیغات سرویس های گوگل پلی</div>

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
AdTrace.getGoogleAdId(function(googleAdId) {
    // ...
});
```
</td>
</tr>
</table>

### <div id="af-di-amz-adid" dir="rtl" align='right'>شناسه تبلیغات آمازون</div>

If you need to obtain the Amazon Advertising ID, call the following method on `AdTrace` instance:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
String amazonAdId = AdTrace.getAmazonAdId(context);
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
let amazonAdId = AdTrace.getAmazonAdId();
```
</td>
</tr>
</table>

### <div id="af-di-adid" dir="rtl" align='right'>شناسه دستگاه ادتریس</div>

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

### <div id="af-pre-installed-trackers" dir="rtl" align='right'>ردیابی قبل از نصب</div>

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

### <div id="af-offline-mode" dir="rtl" align='right'>حالت آفلاین</div>

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

### <div id="af-disable-tracking" dir="rtl" align='right'>غیرفعال کردن ردیابی</div>

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

### <div id="af-event-buffering" dir="rtl" align='right'>بافرکردن رویدادها</div>

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

### <div id="af-background-tracking" dir="rtl" align='right'>ردیابی در پس زمینه</div>

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

### <div id="af-location" dir="rtl" align='right'>موقعیت مکانی</div>

Another way to increase the accuracy and security of fraud detection is to check the user's location. You can enable or disable this feature by using the following method.

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
AdTrace.enableLocation(true);
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
AdTrace.enableLocation(true);
```

</td>
</tr>
</table>

**Note**: This option is **enabled** by default. But in case the location-based permissions are not granted, location data will not be sent to the server.

### <div id="af-gdpr-forget-me" dir="rtl" align='right'>GPDR</div>

In accordance with article 17 of the EU's General Data Protection Regulation (GDPR), you can notify AdTrace when a user has exercised their right to be forgotten. Calling the following method will instruct the AdTrace SDK to communicate the user's choice to be forgotten to the AdTrace backend:

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

```java
AdTrace.gdprForgetMe(context);
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
AdTrace.gdprForgetMe();
```
</td>
</tr>
</table>

Upon receiving this information, AdTrace will erase the user's data and the AdTrace SDK will stop tracking the user. No requests from this device will be sent to AdTrace in the future.

Please note that even when testing, this decision is permanent. It **is not** reversible.

## <div dir="rtl" align='right'>Testing and troubleshooting</div>

### <div id="ts-reset-gps-ad-id" dir="rtl" align='right'>چگونه میتوانم شناسه تبلیغات سرویس گوگل را ریست کنم؟</div>

Please follow [this instruction][reset-google-ad-id] to reset Google Play Store advertising identifier. 

### <div id="ts-session-failed" dir="rtl" align='right'>من ارور "Session failed (Ignoring too frequent session. ...)" را مشاهده میکنم.</div>

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

### <div id="ts-broadcast-receiver" dir="rtl" align='right'>آیا broadcast receiver من اطلاعات install referrer را دریافت میکند؟</div>

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

### <div id="ts-event-at-launch" dir="rtl" align='right'>آیا می توانم هنگام راه اندازی برنامه اقدام به ایجاد یک رویداد کنم؟</div>

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
[releases]:                       https://github.com/adtrace/adtrace_sdk_android/releases
[referrer]:                       doc/english/multiple-receivers.md
[reset-google-ad-id]:             doc/english/reset-google-ad-id.md
[google_ad_id]:                   https://support.google.com/googleplay/android-developer/answer/6048248?hl=en
[new-referrer-api]:               https://developer.android.com/google/play/installreferrer/library.html
[application_name]:               http://developer.android.com/guide/topics/manifest/application-element.html#nm
[attribution-data]:               htttp://adtrace.io
[android-dashboard]:              http://developer.android.com/about/dashboards/index.html
[android_application]:            http://developer.android.com/reference/android/app/Application.html
[android-launch-modes]:           https://developer.android.com/guide/topics/manifest/activity-element.html
[google_play_services]:           http://developer.android.com/google/play-services/setup.html
[android-purchase-verification]:  http://adtrace.io
[android-webbridge-asset]:        https://github.com/adtrace/adtrace_sdk_android/tree/master/android-sdk-plugin-webbridge/src/main/assets
