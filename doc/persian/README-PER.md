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

<div dir="rtl" align='right'>
موارد زیر حداقل موارد لازم برای تعامل SDK ادتریس درون پروژه اندروید میباشد. در نظر ما شما برای توسعه اندروید از Android Studio و API حداقل <strong>9 (Gingerbread)</strong> یا به بالا را استفاده میکنید.
</div>

### <div id="qs-add-sdk" dir="rtl" align='right'>افزودن SDK به پروژه</div>

<div dir="rtl" align='right'>
موارد زیر را به فایل <code>build.gradle</code> داخل قسمت  <code>dependencies</code> اضافه کنید:
</div>

```gradle
implementation 'io.adtrace:android-sdk:1.0.2'
implementation 'com.android.installreferrer:installreferrer:1.0'
```

<div dir="rtl" align='right'>
اگر مایل هستید که SDK ادتریس را داخل web view اپ خود استفاده کنید، این قسمت را همانند بالا داخل <code>dependencies</code> اضافه کنید:
</div>

```gradle
implementation 'io.adtrace:android-sdk-plugin-webbridge:1.0.2'
```

### <div id="qs-add-gps" dir="rtl" align='right'>افزودن سرویس های گوگل پلی</div>

<div dir="rtl" align='right'>
از تاریخ 1 آگوست 2014، برنامه های داخل گوگل پلی بایستی از <a href="https://support.google.com/googleplay/android-developer/answer/6048248?hl=en">شناسه تبلیغاتی گوگل</a> برای شناسایی یکتابودن دستگاه استفاده کنند. برای فعالسازی امکان استفاده از این شناسه خط زیر را به <code>dependencies</code> فایل <code>build.gradle</code> خود اضافه کنید:
</div>

```gradle
implementation 'com.google.android.gms:play-services-analytics:16.0.4'
```

<div dir="rtl" align='right'>
<strong>نکته: </strong> SDK ادتریس محصور به استفاده از ورژن خاصی از <code>play-services-analytics</code> گوگل پلی نیست. بنابراین استفاده از آخرین نسخه این کتابخانه برای ادتریس مشکلی ایجاد نمیکند.
</div>

### <div id="qs-add-permissions" dir="rtl" align='right'>افزودن مجوزها</div>

<div dir="rtl" align='right'>
در ادامه دسترسی های زیر را در فایل <code>AndroidManifest.xml</code> خود اضافه کنید. توجه کنید که دسترسی موقعیت مکانی در حالت معمولی بسته است و باید این مجوز مستقیما از کاربر گرفته شود.
</div>

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> <!--optional-->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> <!--optional-->
```

<div dir="rtl" align='right'>
اگر استور مدنظر شما <strong>به جز گوگل پلی</strong> باشد، دسترسی زیر را نیز اضافه کنید:
</div>

```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
```

### <div id="qs-proguard-settings" dir="rtl" align='right'>تنظیمات Proguard</div>

<div dir="rtl" align='right'>
اگر از Progaurd استفاده میکنید، دستورهای زیر را در فایل Progaurd خود اضافه کنید:
</div>

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

<div dir="rtl" align='right'>
اگر هدف شما استوری به <strong>جز گوگل پلی</strong> میباشد، دستور <code>com.google.android.gms</code> را میتوانید پاک کنید.
</div>

### <div id="qs-ir" dir="rtl" align='right'>تنظیمات Install referrer</div>

<div dir="rtl" align='right'>
برای آنکه به درستی نصب یک برنامه به سورس خودش اتریبیوت شود، ادتریس به اطلاعاتی درمورد <strong>install referrer</strong> نیاز دارد. این مورد با استفاده از <strong>Google Play Referrer API</strong> یا توسط <strong>Google Play Store intent</strong> بواسطه یک broadcast receiver دریافت میشود.
</div>
<br/>
<div dir="rtl" align='right'>
<strong>نکته مهم:</strong> Google Play Referrer API جدیدا راه حلی قابل اعتمادتر و با امنیت بیشتر برای جلو گیری از تقلب click injection  توسط گوگل  جدیدا معرفی شده است. <strong>به صورت اکید</strong> توصیه میشود که از این مورد در برنامه های خود استفاده کنید. Google Play Store intent امنیت کمتری در این مورد دارد و در آینده deprecate خواهد شد.
</div>

#### <div id="qs-ir-gpr-api" dir="rtl" align='right'>Google Play Referrer API</div>

<div dir="rtl" align='right'>
به منظور استفاده از این کتابخانه مطمئن شوید که <a href="#qs-add-sdk">افزودن SDK به پروژه</a> را به درستی پیاده سازی کردید و خط زیر به <code>build.gradle</code> اضافه شده است:
</div>

```gradle
implementation 'com.android.installreferrer:installreferrer:1.0'
```

<div dir="rtl" align='right'>
همچنین مطمئن شوید که درصورت داشتن Progaurd، بخش <a href="qs-proguard-settings">تنظیمات Progaurd</a> به صورت کامل اضافه شده است، مخصوصا دستور زیر:
</div>

```
-keep public class com.android.installreferrer.** { *; }
```

#### <div id="qs-ir-gps-intent" dir="rtl" align='right'>Google Play Store intent</div>

<div dir="rtl" align='right'>
گوگل طی <a href="https://android-developers.googleblog.com/2019/11/still-using-installbroadcast-switch-to.html">بیانیه ای</a> اعلام کرد که از 1 مارچ 2020 دیگر اطلاعات <code>INSTALL_REFERRER</code> را به صورت broadcast ارسال نمیکند، برای همین به رویکرد <a href="#qs-ir-gpr-api">Google Play Referrer API</a> مراجعه کنید.
</div>
<br/>
<div dir="rtl" align='right'>
شما بایستی اطلاعات <code>INSTALL_REFERRER</code> گوگل پلی را توسط یک broadcast receiver دریافت کنید. اگر از <strong>broadcast receiver خود</strong> استفاده نمیکنید، تگ <code>receiver</code> را داخل تگ <code>application</code> درون فایل <code>AndroidManifest.xml</code> خود اضافه کنید:
</div>

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

<div dir="rtl" align='right'>
اگر قبلا از یک broadcast receiver برای دریافت اطلاعات <code>INSTALL_REFERRER</code> استفاده میکرده اید، از <a href="../english/multiple-receivers.md">این دستورالعمل</a>  برای اضافه نمودن broadcast receiver ادتریس استفاده کنید.
</div>

### <div id="qs-integ-sdk" dir="rtl" align='right'>پیاده سازی SDK داخل برنامه</div>

<div dir="rtl" align='right'>
ابتدا ردیابی یک نشست ساده را پیاده سازی میکنیم.
</div>

### <div id="qs-integ-basic-setup" dir="rtl" align='right'>راه اندازی اولیه</div>

<div dir="rtl" align='right'>
اگر قصد پیاده سازی SDK به صورت native را دارید، به قسمت <a href="#qs-integ-basic-setup-native">Native App SDK</a> مراجعه کنید.
</div>
<br/>
<div dir="rtl" align='right'>و اگر قصد پیاده سازی SDK را به صورت web view دارید، به قسمت <a href="#qs-integ-basic-setup-web">Web View SDK</a> مراجعه کنید.
</div>

#### <div id="qs-integ-basic-setup-native" dir="rtl" align='right'>Native App SDK</div>

<div dir="rtl" align='right'>
ما توصیه میکنیم یک کلاس <a href="http://developer.android.com/reference/android/app/Application.html">Application</a> برای راه اندازی SDK درون برنامه خود ایجاد کنید، اگر ایجاد نکردید مراحل زیر را طی کنید:
<ul>
<li>یک کلاس که از <code>Application</code> استفاده میشود.</li>
<li>فایل <code>AndroidManifest.xml</code> را باز کنید و وارد تگ <code> application</code></li> شوید.
<li>اتریبیوت <code>android.name</code> را وارد نمایید.</li>
<li>در برنامه نمونه از یک کلاس به اسم <code>GlobalApplication</code> استفاده میشود، بنابراین فایل manifest ما به صورت زیر خواهد بود:</li>
</ul>
</div>

```xml
<application
    android:name=".GlobalApplication"
    <!-- ...-->
</application>
```

<div dir="rtl" align='right'>
<ul>
<li>داخل کلاس <code>Application</code> متد <code>onCreate</code> را پیدا کنید تا بسازید و کد زیر را برای راه اندازی  SDK ادتریس به آن اضافه کنید:</li>
</ul>
</div>

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

<div dir="rtl" align='right'>
مقدار <code>{YourAppToken}</code> را با توکن برنامه خود که از پنل دریافت کرده اید، عوض کنید.
</div>
<br/>
<div dir="rtl" align='right'>
با توجه به نوع ساخت برنامه شما برای تست یا رلیز، بایستی  <code>environment</code> را یکی از مقادیر زیر قرار دهید:
</div>

```java
String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;
String environment = AdTraceConfig.ENVIRONMENT_PRODUCTION;
```

<div dir="rtl" align='right'>
<strong>نکته:</strong> این مقدار تنها در زمان تست برنامه شما بایستی مقدار <code> AdTraceConfig.ENVIROMENT_SANDBOX</code> قرار بگیرد. این پارامتر را به <code>AdTraceConfig.ENVIROMENT_PRODUCTION</code> قبل از انتشار برنامه خود تغییر دهید.
</div>
<br/>
<div dir="rtl" align='right'>
ادتریس enviroment را برای تفکیک ترافیک داده واقعی و آزمایشی بکار میبرد.
</div>

#### <div id="qs-integ-basic-setup-web" dir="rtl" align='right'>Web View SDK</div>

<div dir="rtl" align='right'>
بعد از ساختن آبجکت <code>WebView</code> به موارد زیر میپردازیم:
<ul>
<li>برای فعال سازی جاوااسکریپت داخل web view دستور <code>webView.getSettings().setJavaScriptEnabled(true)</code> را اضافه کنید.</li> 
<li>یک شی از <code>AdTraceBridgeInstance</code> را به صورت <code>AdtraceBridge.registerAndGetInstance(getApplication(), webView)</code> بسازید.</li>
<li>این دستور همچنین یک پل ارتباطی بین جاوااسکریپت در web view عمل خواهد کرد.</li>
</ul>
</div>

<div dir="rtl" align='right'>
بعد از پیاده سازی زیر، activity شما به صورت زیر خواهد شد:
</div>

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

        AdTraceBridge.registerAndGetInstance(getApplication(), webView);
        try {
            webView.loadUrl("file:///android_asset/AdTraceExample-WebView.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

<div dir="rtl" align='right'>
بعد از مراحل بالا پل ارتباطی میان جاوااسکریپت و اندروید ادتریس به صورت موفقیت آمیز برقرار خواهد شد.
</div>

<div dir="rtl" align='right'>
داخل فایل HTML خود فایل های جاوا اسکریپتی را در در پوشه <a href="https://github.com/adtrace/adtrace_sdk_android/tree/master/android-sdk-plugin-webbridge/src/main/assets
">assets</a> قرار دارند را وارد کنید:
</div>

```html
<script type="text/javascript" src="adtrace.js"></script>
<script type="text/javascript" src="adtrace_event.js"></script>
<script type="text/javascript" src="adtrace_config.js"></script>
```

<div dir="rtl" align='right'>
بعد از وارد کردن فایلهای بالا، دستور زیر را داخل جاوااسکریپت  خود برای راه اندازی SDK کدهای زیر را  اضافه کنید:
</div>

```js
let yourAppToken = '{YourAppToken}';
let environment = AdTraceConfig.EnvironmentSandbox;
let adtraceConfig = new AdTraceConfig(yourAppToken, environment);

AdTrace.onCreate(adtraceConfig);
```

<div dir="rtl" align='right'>
مقدار <code>{YourAppToken}</code> را با توکن برنامه خود که از پنل دریافت کرده اید، عوض کنید.
</div>
<br/>
<div dir="rtl" align='right'>
با توجه به نوع ساخت برنامه شما برای تست یا رلیز، بایستی  <code>environment</code> را یکی از مقادیر زیر قرار دهید:
</div>


```js
let environment = AdTraceConfig.EnvironmentSandbox;
let environment = AdTraceConfig.EnvironmentProduction;
```


<div dir="rtl" align='right'>
<strong>نکته:</strong> این مقدار تنها در زمان تست برنامه شما بایستی مقدار <code> AdTraceConfig.ENVIROMENT_SANDBOX</code> قرار بگیرد. این پارامتر را به <code>AdTraceConfig.ENVIROMENT_PRODUCTION</code> قبل از انتشار برنامه خود تغییر دهید.
</div>
<br/>
<div dir="rtl" align='right'>
ادتریس enviroment را برای تفکیک ترافیک داده واقعی و آزمایشی بکار میبرد.
</div>

### <div id="qs-integ-session-tracking" dir="rtl" align='right'>ردیابی نشست</div>

<div dir="rtl" align='right'>
<strong>نکته مهم:</strong> این مرحله <strong>اهمیت بالایی</strong> دارد و از <strong>پیاده سازی آن  مطمئن شوید.</strong>
</div>

### <div id="qs-integ-session-tracking-api14" dir="rtl" align='right'>API بالاتر از 14</div>

<div dir="rtl" align='right'>
<ul>
<li>یک کلاس private به طوری که در از <code>ActivityLifecycleCallbacks</code> استفاده میکند در کلاس <code>Application</code>  خود بسازید. اگر با ارور دسترسی مواجه شدید، بدین معناست که API برنامه شما زیر 14 است و باید از <a href="#qs-integ-session-tracking-api9">این دستورالعمل</a> استفاده کنید.</li>
<br/>
<li>داخل متد <code>onActivityResumed(Activity activity)</code> دستور <code>()AdTrace.onResume</code> را اضافه کنید. و داخل متد <code>onActivityPaused(Activity activity)</code> دستور <code>()AdTrace.onPause</code> را قرار دهید.</li>
<li>درون متد <code>()onCreate</code> درمحلی که ادتریس پیاده سازی شده است، یک آبجکت از این کلاس را به متد <code>registerActivityLifecycleCallbacks</code> بدهید.</li>
</ul>
</div>

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

<div dir="rtl" align='right'>
اگر <code>minSdkVersion</code> برنامه شما داخل gradle بین 9 و 13 باشد، درنظر داشته باشید که برای طولانی مدت بایستی به 14 ارتقا پیدا کند تا تعامل بهتری با ادتریس داشته باشد.
</div>
<br/>
<div dir="rtl" align='right'>
برای ردیابی نشست بایستی دو متد از ادتریس را در هنگامی که Activity متوقف میشود و یا ادامه پیدا میکند فراخوانی شود. در غیراین صورت SDK ممکن است ابتدا یا پایان نشست را از دست بدهد. برای این کار شما بایستی <strong>موارد زیر را برای هر Activity برنامه اجرا کنید:</strong>
</div>

<div dir="rtl" align='right'>
<ul>
<li>فایل activity تان را باز کنید</li>
<li>در متد <code>onResume</code> دستور <code>()AdTrace.onResume</code> را اضافه کنید. در صورت نیاز متد را ایجاد کنید. </li>
<li>در متد <code>onPause</code> دستور <code>()AdTrace.onPause</code> را اضافه کنید. در صورت نیاز متد را ایجاد کنید. </li>
<ul>
</div>

<div dir="rtl" align='right'>
بعد از موارد بالا، Activity مورد نظر باید شبیه همچینن چیزی شود:
</div>

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

<div dir="rtl" align='right'>
فراموش نکنید که این مراحل را برای <strong>هر Activity</strong> اجرا کنید.
</div>

### <div id="qs-sdk-signature" dir="rtl" align='right'>امضا SDK</div>

<div dir="rtl" align='right'>
برای استفاده از این ویژگی بایستی مدیر اکانت در داخل پنل فعال کند. برای اطلاعات بیشتر میتوانید از طریق <a href="info@adtrace.io">info@adtrace.io</a> در تماس باشید.
</div>
<br/>
<div dir="rtl" align='right'>
اگر امضا SDK فعال شده است، از متد زیر برای پیاده سازی استفاده کنید:
</div>
<br/>
<div dir="rtl" align='right'>
یک App Secret توسط متد <code>setAppSecret</code> داخل <code>AdTraceConfig</code> فراخوانی میشود:
</div>

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

<div dir="rtl" align='right'>
شما میتوانید در حین تست لاگ ادتریس را از طریق <code>setLogLevel</code> که در <code>AdTraceConfig</code> قرار دارد کنترل کنید:
</div>
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

<div dir="rtl" align='right'>
در صورتی که میخواهید همه لاگ های ادتریس غیر فعال شود، علاوه بر مقدار <code>AdTraceConfig.LogLevelSuppress</code> بایستی در تنظیمات ادتریس یک پارامتر boolean قرار دهید که نشان دهنده پشتیبانی از این نوع لاگ (suppress) میباشد یا خیر:
</div>
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

<div dir="rtl" align='right'>
حال برنامه خود را کامپایل کنید و اجرا کنید. در قسمت <code>LogCat</code> میتوانید فیلتر <code>tag:AdTrace</code> را  اضافه کنید تا فقط لاگ های مربوط به ادتریس را مشاهده نمایید.
</div>

## <div dir="rtl" align='right'>Deep linking</div>

### <div id="dl-overview" dir="rtl" align='right'>نمای کلی لینک دهی عمیق</div>

<div dir="rtl" align='right'>
اگر از url ادتریس با تنظیمات deep link برای ترک کردن استفاده میکنید، امکان دریافت اطلاعات و محتوا دیپ لینک از طریق ادتریس فراهم میباشد. با کلیک کردن لینک کاربر ممکن است که قبلا برنامه را داشته باشد(سناریو لینک دهی عمیق استاندارد) یا اگر برنامه را نصب نداشته باشد(سناریو لینک دهی عمیق به تعویق افتاده) به کار برده شود. در سناریو لینک دهی عمیق استاندارد ، دستگاه اندروید محتوا دیپ لینک را میتواند در اختیار شما قرار دهد. در سناریو لینک دهی عمیق به تعویق افتاده خود پلتفرم اندروید توانایی پشتایبانی به خودی خود را ندارد، در این صورت SDK ادتریس یک مکانیزمی را برای دریافت این اطلاعات به شما ارائه میدهد.</div>

### <div id="dl-standard" dir="rtl" align='right'>سناریو لینک دهی عمیق استاندار</div>

<div dir="rtl" align='right'>
اگر کاربر برنامه شما را نصب داشته باشد و شما بخواهید از طریق کلیک ترکر ادتریس به برنامه منتقل شود، بایستی دیپ لینک را درون برنامه خود فعالسازی کنید. این راه از طریق یک <strong>scheme یکتا</strong> درون Activity مورد نظر فعال میشود. برای این کار درون <code>AndroidManifest.xml</code> یک تگ <code>intent-filter</code> به Activity مورد نظر تعریف کنید که مقدار <code>android:scheme</code> آن با یک مقدار مناسب پر شده باشد:
</div>

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

<div dir="rtl" align='right'>
با توجه با مقدار <code>android:launchMode</code> داخل Activity  درون فایل <code>AndroidManifest.xml</code> اطلاعات درباره دیپ لینک به آن Activity رسانده خواهد شد، برای اطلاعات بیشتر از مقدارهای ممکن <code>android:launchMode</code> به <a href="https://developer.android.com/guide/topics/manifest/activity-element.html">دایکومنت رسمی اندروید</a> مراجعه کنید.
</div>
<br/>
<div dir="rtl" align='right'>
در دو قسمت از Activity اطلاعات دیپ لینک از طریق یک شی <code>Intent</code> قابل استفاده است، از طریق <code>onCreate</code> یا متد <code>onNewIntent</code> این امکان فراهم میباشد که به صورت زیر آموزش داده شده است:
</div>

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

<div dir="rtl" align='right'>
این سناریو هنگامی رخ میدهد که بعد از کلیک کردن ترکر ادتریس اپ مورد نظر درون دستگاه کاربر نصب نباشد، و به گوگل پلی برای دانلود و نصب این برنامه دایرکت میشود. بعد از باز کردن اولین بار برنامه پارامترهای دیپ لینک به برنامه منتقل میشود. برای این کار SDK ادتریس به صورت خودکار دیپ لینک را باز میکند و نیازی به تنظیمات اضافه نیست.
</div>

#### <div dir="rtl" align='right'>Deferred deep linking callback</div>

<div dir="rtl" align='right'>
برای شناسایی دیپ لینک میتوانید از طریق یک متد کالبک داخل تنظیمات ادتریس استفاده کنید:
</div>
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

<div dir="rtl" align='right'>
بعد از دریافت اطلاعات دیپ لینک از طریق SDK ادتریس، محتوا این اطلاعات با استفاده از یک listener و مقدار boolean به شما بازمیگرداند. مقدار بازگشتی با توجه به تصمیم شما مبنی بر اینکه میخواهید Activity موردنظر با آن scheme مربوطه را دارید یا خیر.
</div>
<br/>
<div dir="rtl" align='right'>
اگر مقدار بازگشتی شما <code>true</code> باشد، همانند <a href="#dl-standard">سناریو لینک دهی استاندارد</a> انجام میشود. اگر میخواهید که SDK آن Activity مورد نظر را باز نکند بایستی <code>false</code> را برگردانید (با توجه به محتوا دیپ لینک).
</div>
<br/>

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

<div dir="rtl" align='right'>
در این سناریو به تعویق افتاده، یک مورد اضافی بایستی به تنظیمات اضافه شود. هنگامی که SDK ادتریس اطاعات دیپ لینک را دریافت کرد، شما امکان این را دارید که SDK، با استفاده از این اطلاعات باز شود یا خیر که از طریق  متد <code>setOpenDeferredDeeplink</code> قابل استفاده است:
</div>

```js
// ...

function deferredDeeplinkCallback(deeplink) {}

let adtraceConfig = new AdTraceConfig(appToken, environment);
adtraceConfig.setOpenDeferredDeeplink(true);
adtraceConfig.setDeferredDeeplinkCallback(deferredDeeplinkCallback);

AdTrace.start(adtraceConfig);

```

<div dir="rtl" align='right'>
توجه فرمایید که اگر کالبکی تنظیم نشود، <strong>SDK ادتدریس در حالت پیشفرض تلاش میکند تا URL را اجرا کند</strong>.
</div>

</td>
</tr>
</table>

### <div id="dl-reattribution" dir="rtl" align='right'>اتریبیوت مجدد از طریق لینک عمیق</div>

<div dir="rtl" align='right'>
ادتریس این امکان را دارد تا از طریق دیپ لینک کمپین ها را مجددا اطلاعات گذاری کند.
</div>
<br/>
<div dir="rtl" align='right'>
اگر شما از این ویژگی استفاده میکنید، برای اینکه کاربر به درستی مجددا اتریبیوت شود، نیاز دارید یک دستور اضافی به برنامه خود اضافه کنید.
</div>
<br/>
<div dir="rtl" align='right'>
هنگامی که اطلاعات دیپ لینک را دریافت میکنید، متد <code>AdTrace.appWillOpenUrl(Uri, Context)</code>  را فراخوانی کنید. از طریق این SDK تلاش میکند تا ببیند اطلاعات جدیدی درون دیپ لینک برای اتریبیوت کردن قرار دارد یا خیر. اگر وجود داشت، این اطلاعات به سرور ارسال میشود.  اگر کاربر از طریق کلیک بر ترکر ادتریس مجددا اتریبیوت شود، میتوانید از قسمت <a href="#af-attribution-callback">اتریبیوشن کالبک</a> اطلاعات جدید را برای این کاربر دریافت کنید.
</div>
<br/>
<div dir="rtl" align='right'>
فراخوانی متد <code>AdTrace.appWillOpenUrl(Uri, Context)</code> بایستی مثل زیر باشد:
</div>

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

<div dir="rtl" align='right'>
<strong>نکته ای برای web view:</strong> این فراخوانی در جاوااسکریپت با متد <code>AdTrace.appWillOpenUrl</code> به شکل زیر میباشد:
</div>

```js
AdTrace.appWillOpenUrl(deeplinkUrl);
```

## <div dir="rtl" align='right'>ردیابی رویداد</div>

### <div id="et-track-event" dir="rtl" align='right'>ردیابی رویداد معمولی</div>

<div dir="rtl" align='right'>
شما برای یک رویداد میتوانید از انواع رویدادها درون برنامه خود استفاده کنید. فرض کنید که میخواهید لمس یک دکمه را رصد کنید. بایستی ابتدا یک رویداد درون پنل خود ایجاد کنید. اگر فرض کنیم که توکن رویداد شما <code>abc123</code> باشد، سپس در متد <code>onClick</code> دکمه مربوطه کد زیر را برای ردیابی لمس دکمه اضافه کنید:
</div>

```java
AdTraceEvent event = new AdTraceEvent("abc123");
AdTrace.trackEvent(event);
```

### <div id="et-track-revenue" dir="rtl" align='right'>ردیابی رویداد درآمدی</div>

<div dir="rtl" align='right'>
اگر کاربران شما از طریق کلیک بر روی تبلیغات یا پرداخت درون برنامه ای، رویدادی میتوانند ایجاد کنند، شما میتوانید آن درآمد را از طریق رویدادی مشخص رصد کنید. اگر فرض کنیم که یک ضربه به ارزش یک سنت از واحد یورو باشد، کد شما برای ردیابی این رویداد به صورت زیر میابشد:
</div>

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.setRevenue(0.01, "EUR");
AdTrace.trackEvent(event);
```

<div dir="rtl" align='right'>
این ویژگی میتواند با پارامترهای callback نیز ترکیب شود.
</div>
<br/>
<div dir="rtl" align='right'>
هنگامی که واحد پول را تنظیم کردید، ادتریس به صورت خودکار درآمدهای ورودی را به صورت خودکار به انتخاب شما تبدیل میکند.
</div>

### <div id="et-revenue-deduplication" dir="rtl" align='right'>جلوگیری از تکرار رویداد درآمدی</div>

<div dir="rtl" align='right'>
شما میتوانید یک شناسه خرید مخصوص برای جلوگیری از تکرار رویداد درآمدی استفاده کنید. 10 شناسه آخر ذخیره میشود و درآمدهای رویدادهایی که شناسه خرید تکراری دارند درنظر گرفته نمیشوند. این برای موارد خریددرون برنامه ای بسیار کاربرد دارد. به مثال زیر توجه کنید.
</div>
<br/>
<div dir="rtl" align='right'>
اگر میخواهید پرداخت درون برنامه ای ها را رصد کنید، فراخوانی متد <code>trackEvent</code> را زمانی انجام دهید که خرید انجام شده است و محصول خریداری شده است. بدین صورت شما از تکرار رویداد درآمدی جلوگیری کرده اید.
</div>

```java
AdTraceEvent event = new AdTraceEvent("abc123");
event.setRevenue(0.01, "EUR");
event.setOrderId("{OrderId}");
AdTrace.trackEvent(event);
```

## <div dir="rtl" align='right'>پارامترهای سفارشی</div>

### <div id="cp-overview" dir="rtl" align='right'>نمای کلی پارامترهای سفارشی</div>

<div dir="rtl" align='right'>
علاوه بر داده هایی که SDK ادتریس به صورت خودکار جمع آوری میکند، شما از ادتریس میتوانید مقدارهای سفارشی زیادی را با توجه به نیاز خود (شناسه کاربر، شناسه محصول و ...) به رویداد یا نشست خود اضافه کنید. پارامترهای سفارشی تنها به صورت خام و export شده قابل دسترسی میباشد و در پنل ادتریس قابل نمایش <strong>نمیباشد</strong>.</div> 
<br/>
<div dir="rtl" align='right'>
شما از <strong>پارامترهای callback</strong> برای استفاده داخلی خود بکار میبرید و از <strong>پارامترهای partner</strong> برای به اشتراک گذاری به شریکان خارج از برنامه استفاده میکنید. اگر یک مقدار (مثل شناسه محصول) برای خود و شریکان خارجی استفاده میشود، ما پیشنهاد میکنیم که از هر دو پارامتر partner و callback استفاده کنید.
</div>

### <div id="cp-ep" dir="rtl" align='right'>پارامترهای رویداد</div>

### <div id="cp-ep-callback" dir="rtl" align='right'>پارامترهای callback رویداد</div>

<div dir="rtl" align='right'>
شما میتوانید یک آدرس callback برای رویداد خود داخل پنل اضافه کنید. ادرتیس یک درخواست GET به آن آدرسی که اضافه نموده اید، ارسال خواهد کرد. همچنین پارامترهای callback برای آن رویداد را از طریق متد <code>addCallbackParameter</code> برای آن رویداد قبل از ترک آن استفاده کنید. ما این پارامترها را به آخر آدرس callback شما اضافه خواهیم کرد.
</div>
<br/>
<div dir="rtl" align='right'>
به عنوان مثال اگر شما آدرس <code>http://www.example.com/callback</code> را به رویداد خود اضافه نموده اید، ردیابی رویداد به صورت زیر خواهد بود:
</div>
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

<div dir="rtl" align='right'>
در اینصورت ما رویداد شما را رصد خواهیم کرد و یک درخواست به صورت زیر ارسال خواهیم کرد:
</div>

```
http://www.example.com/callback?key=value&foo=bar
```

### <div id="cp-ep-partner" dir="rtl" align='right'>پارامترهای partner رویداد</div>

<div dir="rtl" align='right'>
شما همچنین پارامترهایی را برای شریکان خود تنظیم کنید که درون پنل ادتریس فعالسازی میشود.
</div>
<br/>
<div dir="rtl" align='right'>
این پارامترها به صورت callback که در بالا مشاهده میکنید استفاده میشود، فقط از طریق متد <code>addPartnerParameter</code> درون یک شی از <code>AdTraceEvent</code> فراخوانی میشود.
</div>

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

<div dir="rtl" align='right'>
شما همچنین میتوانید یک شناسه به صورت رشته برای هریک از رویدادهایی که رصد کردید اضافه کنید. این شناسه بعدا در callback موفق یا رد شدن آن رویداد به دست شما خواهد رسید که متوجه شوید این ردیابی به صورت موفق انجام شده است یا خیر. این مقدار از طریق متد <code>setCallbackId</code> درون شی  از <code>AdTraceEvent</code> قابل تنظیم است.
</div>
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

<div dir="rtl" align='right'>
شما همچنین یک رشته دلخواه به رویداد خود میتوانید اضافه کنید. این مقدار از طریق <code>setEventValue</code> قابل استفاده است:
</div>
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

<div dir="rtl" align='right'>
پارامترهای نشست به صورت محلی ذخیره میشوند و به همراه هر <strong>رویداد</strong> یا <strong>نشست</strong> ادتریس ارسال خواهند شد. هنگامی که هرکدام از این پارامترها  اضافه شدند، ما آنها را ذخیره خواهیم کرد پس نیازی به اضافه مجدد آنها نیست. افزودن مجدد پارامترهای مشابه تاثیری نخواهد داشت.
</div>
<br/>
<div dir="rtl" align='right'>
این پارامترها میتوانند قبل از شروع SDK ادتریس تنظیم شوند. اگر میخواهید هنگام نصب آنها را ارسال کنید، ولی پارامترهای آن بعد از نصب دراختیار شما قرار میگیرد، برای اینکار میتوانید از <a href="#cp-sp-delay-start">تاخیر</a> در شروع اولیه استفاده کنید.
</div>

### <div id="cp-sp-callback" dir="rtl" align='right'>پارامترهای callback نشست</div>

<div dir="rtl" align='right'>
شما میتوانید هرپارامتر callback ای که برای <a href="#cp-ep-callback">رویدادها</a> ارسال شده را در هر رویداد یا نشست ادتریس ذخیره کنید.
</div>
<br/>
<div dir="rtl" align='right'>
این پارامترهای callback نشست مشابه رویداد میباشد. برخلاف اضافه کردن key و value به یک رویداد، آنها را از طریق متد <code>AdTrace.addSessionCallbackParameter(String key, String value)</code> استفاده کنید:
</div>
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

<br/>
<div dir="rtl" align='right'>
پارامترهای callback نشست با پارامترهای callback به یک رویداد افزوده اید ادغام خواهد شد. پارامترهای رویداد بر نشست تقدم و برتری دارند، بدین معنی که اگر شما پارامتر callback یک ایونت را با یک key مشابه که به نشست افزوده شده است، این مقدار نسبت داده شده به این key از رویداد استفاده خواهد کرد.
</div>
<br/>
<div dir="rtl" align='right'>
این امکان فراهم هست که مقدار پارامترهای callback نشست از طریق key مورد نظربا متد <code>AdTrace.removeSessionCallbackParameter(String key)</code> حذف شود:
</div>
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

<br/>
<div dir="rtl" align='right'>
اگر شما مایل هستید که تمام مقدایر پارامترهای callback نشست را پاک کنید، بایستی از متد <code>()AdTrace.resetSessionCallbackParameters</code> استفاده کنید:
</div>
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

<div dir="rtl" align='right'>
به همین صورت پارامترهای partner مثل <a href="#cp-sp-callback">پارامترهای callback نشست</a> در هر رویداد یا نشست ارسال خواهند شد.
</div>
<br/>
<div dir="rtl" align='right'>
این مقادیر برای تمامی شریکان که در پنل خود فعالسازی کردید ارسال میشود.
</div>
<br/>
<div dir="rtl" align='right'>
پارامترهای partner نشست همچون رویداد میباشد. بایستی از متد <code>AdTrace.addSessionPartnerParameter(String key, String value)</code> استفاده شود:
</div>
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

<br/>
<div dir="rtl" align='right'>
پارامترهای partner نشست با پارامترهای partner به یک رویداد افزوده اید ادغام خواهد شد. پارامترهای رویداد بر نشست تقدم و برتری دارند، بدین معنی که اگر شما پارامتر partner یک ایونت را با یک key مشابه که به نشست افزوده شده است، این مقدار نسبت داده شده به این key از رویداد استفاده خواهد کرد.
</div>
<br/>
<div dir="rtl" align='right'>
این امکان فراهم هست که مقدار پارامترهای partner نشست از طریق key مورد نظربا متد <code>AdTrace.removeSessionPartnerParameter(String key)</code> حذف شود:
</div>
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

<br/>
<div dir="rtl" align='right'>
اگر شما مایل هستید که تمام مقدایر پارامترهای partner نشست را پاک کنید، بایستی از متد <code>()AdTrace.resetSessionPartnerParameters</code> استفاده کنید:
</div>
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

<div dir="rtl" align='right'>
شروع با تاخیر SDK ادتریس این امکان را به برنامه شما میدهد تا پارامترهای نشست شما در زمان نصب ارسال شوند.
</div>
<br/>
<div dir="rtl" align='right'>
با استفاده از متد <code>setDelayStart</code> که مقداری به ثانیه میگیرد باعث تاخیر در شروع اولیه ادتریس خواهد شد:
</div>
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

<br/>
<div dir="rtl" align='right'>
در این مثال SDK ادتریس مانع از ارسال نشست نصب اولیه و هر رویدادی با تاخیر 5.5 ثانیه خواهد شد. بعد از اتمام این زمان (یا فراخوانی متد <code>()AdTrace.sendFirstPackages</code> در طی این زمان) هر پارامتر نشستی با تاخیر آن زمان افزوده خواهد شد و بعد آن ادتریس به حالت عادی به کار خود ادامه میدهد.
</div>
<br/>
<div dir="rtl" align='right'>
<strong>بیشترین زمان ممکن برای تاخیر در شروع SDK ادتریس 10 ثانیه خواهد بود.</strong>
</div>

## <div dir="rtl" align='right'>ویژگی های بیشتر</div>

<div dir="rtl" align='right'>
هنگامی که شما SDK ادتریس را پیاده سازی کردید، میتوانید از ویژگی های زیر بهره ببرید:
</div>

### <div id="af-push-token" dir="rtl" align='right'>توکن push (ردیابی تعداد حذف برنامه)</div>

<div dir="rtl" align='right'>
توکن پوش برای برقراری ارتباط با کاربران استفاده میشود، همچنین برای ردیابی تعداد حذف یا نصب مجدد برنامه از این توکن استفاده میشود.
</div>
<br/>
<div dir="rtl" align='right'>
برای ارسال توکن پوش نوتیفیکشین خط زیر را در قسمتی که کد را دریافت کرده اید (یا هنگامی که مقدار آن تغییر میکند) اضافه نمایید:
</div>
<br/>

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

<div dir="rtl" align='right'>
شما میتوانید یک listener هنگامی که اتریبیشون ترکر تغییر کند، داشته باشید. ما امکان فراهم سازی این اطلاعات را به صورت همزمان به دلیل تنوع منبع اتریبیوشن را نداریم.
</div>
<br/>
<div dir="rtl" align='right'>
برای callback اتریبیشون  قبل از شروع SDK موارد زیر را اضافه کنید:
</div>
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

<br/>
<div dir="rtl" align='right'>
این تابع بعد از دریافت آخرین اطلاعات اتریبیوشن صدا زده خواهد شد. با این تابع، به پارامتر <code>attribution</code> دسترسی پیدا خواهید کرد. موارد زیر یک خلاصه ای از امکانات گفته شده است:
</div>
<div dir="rtl" align='right'>
<ul>
<li><code>trackerToken</code> توکن ترکر از اتریبیوشن درحال حاضر است و جنس آن رشته میباشد.</li>
<li><code>trackerName</code> اسم ترکر از اتریبیوشن درحال حاضر است و جنس آن رشته میباشد.</li>
<li><code>network</code> لایه نتورک از اتریبیوشن درحال حاضر است و جنس آن رشته میباشد.</li>
<li><code>campain</code> لایه کمپین از اتریبیوشن درحال حاضر است و جنس آن رشته میباشد.</li>
<li><code>adgroup</code> لایه ادگروپ از اتریبیوشن درحال حاضر است و جنس آن رشته میباشد.</li>
<li><code>creative</code> لایه کریتیو از اتریبیوشن درحال حاضر است و جنس آن رشته میباشد.</li>
<li><code>adid</code> شناسه ادتریس است و جنس آن رشته میباشد.</li>
<ul>
</div>

### <div id="af-session-event-callbacks" dir="rtl" align='right'>callback های رویداد و نشست</div>

<div dir="rtl" align='right'>
این امکان فراهم است که یک listener هنگامی که رویداد یا نشستی ردیابی میشود، به اطلاع شما برساند. چهار نوع listener داریم: یکی برای ردیابی موفق بودن رویدادها، یکی برای ردیابی ناموفق بودن رویدادها، دیگری برای موفق بودن نشست و آخری نیز برای ناموفق بودن ردیابی نشست. برای درست کردن همچین listener هایی به صورت زیر عمل میکنیم:
</div>
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

<br/>
<div dir="rtl" align='right'>
listener ها هنگامی فراخوانده میشوند که SDK تلاش به ارسال داده سمت سرور کند. با این listener شما دسترسی به  داده های دریافتی دارید. موارد زیر یک خلاصه ای از داده های دریافتی هنگام نشست موفق میباشد:
</div>
<div dir="rtl" align='right'>
<ul>
<li><code>message</code> پیام از طرف سرور(یا ارور از طرف SDK)</li>
<li><code>timestamp</code> زمان دریافتی از سرور</li>
<li><code>adid</code> یک شناسه یکتا که از طریق ادتریس ساخته شده است</li>
<li><code>jsonResponse</code> شی JSON دریافتی از سمت سرور</li>
</ul>
</div>

<div dir="rtl" align='right'>
هر دو داده دریافتی رویداد شامل موارد زیر میباشد:
</div>

<div dir="rtl" align='right'>
<ul>
<li><code>eventToken</code> توکن مربوط به رویداد مورد نظر</li>
<li><code>cakkbackId</code> <a href="#cp-ep-id">شناسه callback</a> که برای یک رویداد تنظیم میشود</li>
</ul>
</div>

<div dir="rtl" align='right'>
و هر دو رویداد و نشست ناموفق شامل موارد زیر میشوند:
</div>

<div dir="rtl" align='right'>
<ul>
<li><code>willRetry</code> یک boolean ای  تلاش مجدد برای ارسال داده را نشان میدهد ای خیر.</li>
</ul>
</div>

### <div id="af-user-attribution" dir="rtl" align='right'>اتریبیوشن کاربر</div>

<div dir="rtl" align='right'>
همانطور که در بخش <a href="#af-attribution-callback">callback اتریبیوشن</a> توضیح دادیم، این  callback هنگامی که اطلاعات اتریبیوشن عوض بشود، فعالسازی میشود. برای دسترسی به اطلاعات اتریبیوشن فعلی کاربر درهر زمانی که نیاز بود از طریق متد زیر قابل دسترس است:
</div>
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

<br/>
<div dir="rtl" align='right'>
<strong>نکته</strong>: اطلاعات اتریبیوشن فعلی تنها درصورتی دردسترس است که از سمت سرور نصب برنامه ردیابی شود و از طریق callback اتریبیوشن فعالسازی شود. <strong>امکان این نیست که</strong> قبل از اجرا اولیه SDK  و فعالسازی callback اتریبیوشن بتوان به داده های کاربر دسترسی پیدا کرد.
</div>

### <div id="af-send-installed-apps" dir="rtl" align='right'>ارسال برنامه های نصب شده دستگاه</div>

<div dir="rtl" align='right'>
برای افزایش دقت و امنیت در تشخیص تقلب برنامه ای، میتوانید برنامه های ئرون دستگاه کاربر را برای ارسال سمت سرور به صورت زیر فعالسازی کنید:
</div>
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

<div dir="rtl" align='right'>
SDK ادتریس انواع شناسه ها رو به شما پیشنهاد میکند.
</div>

### <div id="af-di-gps-adid" dir="rtl" align='right'>شناسه تبلیغات سرویس های گوگل پلی</div>

<div dir="rtl" align='right'>
سرویس های مشخص (همچون Google Analytics) برای هماهنگی بین شناسه تبلیغات و شناسه کاربر به جهت ممانعت از گزارش تکراری به شما نیاز دارد.
</div>
<br/>

<table>
<tr>
<td>
<b>Native App SDK</b>
</td>
</tr>
<tr>
<td>

<br/>
<div dir="rtl" align='right'>
اگر میخواهید شناسه تبلیغات گوگل را بدست آورید، یک محدودیتی وجود دارد که تنها از طریق ترد پس زمینه قابل خواندن میباشد. میتوانید از طریق تابع <code>getGoogleAdId</code> به همراه context و یک شی از <code>OnDeviceIdsRead</code> به صورت زیر به این شناسه دست پیدا کنید:
</div>

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

<div dir="rtl" align='right'>
برای دستیابی به شناسه تبلیغاتی گوگل لازم است تا یک تابع callback به متد <code>AdTrace.getGoogleAdId</code> که این شناسه را دریافت میکند به صورت زیر استفاده کنید:
</div>

```js
AdTrace.getGoogleAdId(function(googleAdId) {
    // ...
});
```
</td>
</tr>
</table>

### <div id="af-di-amz-adid" dir="rtl" align='right'>شناسه تبلیغات آمازون</div>

<div dir="rtl" align='right'>
اگر میخواهید به شناسه تبلیغات آمازون دست پیدا کنید، متد درون <code>AdTrace</code> را به صورت زیر فراخوانی کنید:
</div>
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

<div dir="rtl" align='right'>
برای هر دستگاهی که نصب میشود، سرور ادتریس یک <strong>شناسه یکتا</strong> (که به صورت <strong>adid</strong> نامیده ومشود) تولید میکند. برای دستیابی به این شناسه میتوانید به صورت زیر استفاده کنید:
</div>
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

<br/>
<div dir="rtl" align='right'>
<strong>نکته</strong>: اطلاعات مربوط به شناسه <strong>شناسه ادتریس</strong> تنها بعد از ردیابی نصب توسط سرور ادتریس قابل درسترس است. دسترسی به شناسه ادتریس قبل این ردیابی و یا قبل راه اندازی ادتریس <strong>امکان پذیر نیست</strong>.
</div>

### <div id="af-pre-installed-trackers" dir="rtl" align='right'>ردیابی قبل از نصب</div>

<div dir="rtl" align='right'>
اگر مایل به این هستید که SDK ادتریس تشخیص این را بدهد که کدام کاربرانی از طریق نصب از پیشن تعیین شده وارد برنامه شده اند مراحل زیر را انجام دهید:
</div>
<div dir="rtl" align='right'>
<ul>
<li>یک ترکر جدید در پنل خود ایجاد نمایید.<li/>
<li>در تنظیمات SDK ادتریس مثل زیر ترکر پیشفرض را اعمال کنید:</li>
</ul>
</div>
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

<div dir="rtl" align='right'>
<ul>
<li>مقدار <code>{TrackerToken}</code> را با مقدار توکن ترکری که در مرحله اول دریافت کرده اید جاگزین کنید.</li>
<li>برنامه خود را بسازید. در قسمت LogCat خود همچین خطی را مشاهده خواهید کرد.</li>
</ul>
</div>

  ```
  Default tracker: 'abc123'
  ```

### <div id="af-offline-mode" dir="rtl" align='right'>حالت آفلاین</div>

<div dir="rtl" align='right'>
برای مسدودسازی ارتباط SDK با سرورهای ادتریس میتوانید از حالت آفلاین SDK استفاده کنید(درحالیکه مجموعه داده ها بعدا برای رصد کردن ارسال میشود). در حالت آفلاین تمامی اطلاعات درون یک فایل ذخیره خواهد شد. توجه کنید که در این حالت رویدادهای زیادی را ایجاد نکنید.
</div>
<br/>
<div dir="rtl" align='right'>
برای فعالسازی حالت آفلاین متد <code>setOfflineMode</code> را با پارامتر <code>true</code> فعالسازی کنید.
</div>
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

<br/>
<div dir="rtl" align='right'>
بر عکس حالت بالا با فراخوانی متد <code>setOfflineMode</code> به همراه متغیر <code>false</code> میتوانید این حالت آفلاین را غیرفعال کنید. هنگامی که SDK ادتریس به حالت آنلاین برگردد، تمامی اطلاعات ذخیر شده با زمان صحیح مربوط به خودش سمت سرور ارسال میشود.
</div>
<br/>
<div dir="rtl" align='right'>
برخلاف غیرفعال کردن ردیابی، این تنظیم بین نشست ها <strong>توصیه نمیشود</strong>. این بدین معنی است که SDK هرزمان که شروع شود در حالت آنلاین است، حتی اگر برنامه درحالت آفلاین خاتمه پیدا کند.
</div>

### <div id="af-disable-tracking" dir="rtl" align='right'>غیرفعال کردن ردیابی</div>

<div dir="rtl" align='right'>
شما میتوانید SDK ادتریس را برای رصدکردن هرگونی فعالیت برای این دستگاه غیر فعال کنید که این کار از طریق متد <code>setEnabled</code> با پارامتر <code>false</code> امکان پذیر است. <strong>این تنظیم بین نشست ها به خاطر سپرده میشود</strong>.
</div>
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

<br/>
<div dir="rtl" align='right'>
شما برای اطلاع از فعال بودن ادتریس میتوانید از تابع <code>isEnabled</code> استفاده کنید. این امکان فراهم است که  SDK ادتریس را با متد <code>setEnabled</code> و پارامتر <code>true</code> فعالسازی کنید.
</div>

### <div id="af-event-buffering" dir="rtl" align='right'>بافرکردن رویدادها</div>

<div dir="rtl" align='right'>
اگر برنامه شما استفاده زیادی از رویدادها میکند، ممکن است بخواهید با یک حالت تاخیر و در یک مجموعه هر دقیقه ارسال کنید. میتوانید از طریق زیر بافرکردن رویدادها را فعالسازی کنید:
</div>
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

<div dir="rtl" align='right'>
رفتار پیشفرض SDK ادتریس هنگامی که برنامه در حالت پس زمینه قرار دارد، به صورت متوقف شده از ارسال داده ها میباشد. برای تغییر این مورد میتوانید به صورت زیر عمل کنید:
</div>
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

<div dir="rtl" align='right'>
راه دیگری که برای افزایش دقت آمار و امنیت در شناسایی نصب های تقلبی صورت میگیرد اینکه از موقعیت کاربر استفاده کند. شما میتوانید برای فعالسازی این ویژگی به صورت زیر عمل کنید:
</div>
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

<br/>
<div dir="rtl" align='right'>
<strong>نکته</strong>: این ویژگی در حالت پیشفرض <strong>فعال</strong> است. اما درحالتی  که مجوز دسترسی به موقعیت کاربر داده نشده است، این داده به سمت سرور ارسال نخواهد شد.
</div>

### <div id="af-gdpr-forget-me" dir="rtl" align='right'>GPDR</div>

<div dir="rtl" align='right'>
بر طبق قانون GPDR شما این اعلان را به ادتریس میتوانید بکنید هنگامی که کاربر حق این را دارد که اطلاعاتش محفوظ بماند. از طریق متد زیر میتوانید این کار را انجام دهید:
</div>
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

<div dir="rtl" align='right'>
طی دریافت این داده، ادتریس تمامی داده های کاربر را پاک خواهد کرد و ردیابی کاربر را متوقف خواهد کرد. هیچ درخواستی از این دستگاه به ادتریس در آینده ارسال نخواهد شد.
</div>
<br/>
<div dir="rtl" align='right'>
درنظر داشته باشید که حتی در زمان تست، این تصمیم بدون تغییر خواهد بود و قابل برگشت <strong>نیست</strong>.
</div>

## <div dir="rtl" align='right'>تست و عیب یابی</div>

### <div id="ts-reset-gps-ad-id" dir="rtl" align='right'>چگونه میتوانم شناسه تبلیغات سرویس گوگل را ریست کنم؟</div>

<div dir="rtl" align='right'>
با استفاده از <a href="doc/english/reset-google-ad-id.md">این دستور العمل</a> میتوانید شناسه تبلیغات سرویس گوگل را ریست کنید.
</div>

### <div id="ts-session-failed" dir="rtl" align='right'>من ارور "Session failed (Ignoring too frequent session. ...)" را مشاهده میکنم.</div>

<div dir="rtl" align='right'>
این ارور معمولا در زمان تست نصب رخ میهد. پاک کردن و نصب مجدد برنامه نمیتواند یک نصب جدید را ایجاد کند. با توجه به داده های طرف سرور، سرورها این تشخیص را میدهند که SDK داده های مربوط به نشست را از دست داده است و این پیام را نادیده میگیرند.
</div>
<br/>
<div dir="rtl" align='right'>
این رفتار در زمان تست مشکل خواهد بود ولی نیاز است که در صورت امکان مثل حالت production، در حالت sandbox نیز این رفتار را داشته باشیم.
</div>
<br/>
<div dir="rtl" align='right'>
شما برای ریست کردن دادهای نشست سمت سرورمیتوانید از <a href="#ts-reset-gps-ad-id">ریست کردن شناسه تبلیغاتی گوگل</a> استفاده کنید.
</div>

### <div id="ts-broadcast-receiver" dir="rtl" align='right'>آیا broadcast receiver من اطلاعات install referrer را دریافت میکند؟</div>

<div dir="rtl" align='right'>
اگر با <a href="#qs-ir-gps-intent">این راهنمایی</a> دستورالعمل را دنبال کرده باشید، boradcase receiver اطلاعات نصب را به SDK و سرورهای ما ارسال خواهد کرد.
</div>
<br/>
<div dir="rtl" align='right'>
شما از طریق زیر میتوانید به صورت دستی این اطلاعات نصب را آزمایش کنید. قسمت <code>com.your.appid</code> را با شناسه برنامه (app ID) خود جایگزین نمایید و سپس دستور زیر را از طریق <a href="http://developer.android.com/tools/help/adb.html">adb</a> در اندروید استودو اجرا کنید:
</div>

```
adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n com.your.appid/io.adtrace.sdk.AdTraceReferrerReceiver --es "referrer" "adtrace_reftag%3Dabc1234%26tracking_id%3D123456789%26utm_source%3Dnetwork%26utm_medium%3Dbanner%26utm_campaign%3Dcampaign"
```

If you are already using a different broadcast receiver for the `INSTALL_REFERRER` intent and followed this [guide][referrer], replace `io.adtrace.sdk.AdTraceReferrerReceiver` with your broadcast receiver.

<div dir="rtl" align='right'>
اگر شما همزمان از boradcast receiver های مختلفی برای <code>INSTALL_REFERRER</code> استفاده میکنید، طبق <a href="doc/english/multiple-receivers.md">این آموزش</a>  کلاس <code>io.adtrace.sdk.AdTraceReferrerReceiver</code> را با broadcast receiver خود جایگزین کنید.
</div>
<br/>
<div dir="rtl" align='right'>
همچنین با پاک کردن قست <code>n com.your.appid/io.adtrace.sdk.AdTraceReferrerReceiver-</code> در قسمت کد دستوری adb امکان این را میدهید تا <code>INSTALL_REFERRER</code> به همه گوشی های داخل دستگاهاتان ارسال شود.
</div>
<br/>
<div dir="rtl" align='right'>
اگر سطح لاگ را به درجه <code>verbose</code> تغییر دهید، امکان مشادهده لاگ را به صورت زیر خواهید داشت:
</div>

```
V/AdTrace: Referrer to parse (adtrace_reftag=abc1234&tracking_id=123456789&utm_source=network&utm_medium=banner&utm_campaign=campaign) from reftag
```

<div dir="rtl" align='right'>
و پکیج نصب به صورت زیر خواهد شد:
</div>

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

<div dir="rtl" align='right'>
اگر این آزمایش را قبل از اجرای برنامه انجام دهید، هیچ پکیجی ارسال نخواهد شد. بسته زمانی که برنامه باز شود ارسال خواهد شد.
</div>
<br/>
<div dir="rtl" align='right'>
<strong>نکته</strong>: این اطلاع را داشته باشید که <code>adb</code> برای تست میباشد و برای آزمایش کامل محتوای referrer (در صورتی که پارامترهای مختلفی با <code>&</code> جدا شده اند)، شما باید این اطلاعات را دستی کدگذاری (encode) کنید. درغیر این صورت <code>adb</code> اطلاعات شما را بعد از اولین <code>&</code> جدا خواهد کرد و اطلاعات غلطی را به سمت broadcast receiver ارسال خواهد کرد.
</div>
<br/>
<div dir="rtl" align='right'>
اگر شما مایل هستید به اینگه چطور برنامه اطلاعات referrer را به صورت غیرکدگذاری(unencoded) دریافت میکند، طبق مثال زیر یک متد به اسم <code>onFireIntentClick</code> درون فایل <code>MainActivity.java</code> خود ایجاد کنید:
</div>

```java
public void onFireIntentClick(View v) {
    Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
    intent.setPackage("com.adtrace.examples");
    intent.putExtra("referrer", "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test");
    sendBroadcast(intent);
}
```

<div dir="rtl" align='right'>
دومین مقدار <code>putExtra</code> را با مقدار دلخواه خود پر کنید.
</div>

### <div id="ts-event-at-launch" dir="rtl" align='right'>آیا می توانم هنگام راه اندازی برنامه اقدام به ایجاد یک رویداد کنم؟</div>

<div dir="rtl" align='right'>
درون متد <code>onCreate</code> داخل کلاس <code>Application</code> نه تنها محل شروع برنامه است بلکه همچنین محلی برای دریافت رویدادی درون برنامه یا سیستم نیز میباشد.
</div>
<br/>
<div dir="rtl" align='right'>
SDK ادتریس در این زمان شروع اولیه  را آغاز میکند ولی در حقیقت شروع نمیشود. این زمانی اتفاق میفتد که Activtiy شروع شود.
</div>
<br/>
<div dir="rtl" align='right'>
به همین دلیل بر طبق چیزی که انتظار دارید پیش نمی آید. این فراخوانی ها زمانی اتفاق میفتد که SDK ادتریس شروع به کار کند تا بتواند رویدادها را ارسال کند.
</div>
<div dir="rtl" align='right'>
اگر مایل به ارسال رویدادی بعد از نصب را دارید از <a href="#af-attribution-callback">callback اتریبیوشن</a> استفاده کنید.
</div>
<br/>
<div dir="rtl" align='right'>
اگر مایل به ارسال ارسال رویدادی بعد از شروع برنامه را دارید، درون متد <code>onCreate</code> کلاس Activity ای که شروع شده است رویداد را ارسال کنید.
</div>

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
