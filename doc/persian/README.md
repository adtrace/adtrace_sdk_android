




<p align="center"><a href="https://adtrace.io" target="_blank" rel="noopener noreferrer"><img width="100" src="https://adtrace.io/fa/wp-content/uploads/2020/09/cropped-logo-sign-07-1.png" alt="Adtrace logo"></a></p>  
<p align="center">  
  <a href='https://opensource.org/licenses/MIT'><img src='https://img.shields.io/badge/License-MIT-green.svg'></a>  
</p>  

## <div dir="rtl" align='right'>SDK اندروید ادتریس</div>

<div dir="rtl" align='right'>  
  برای اطلاعات بیشتر می توانید به <a href="adtrace.io">وب سایت ادتریس</a>  مراجعه کنید.  
</div>  

## <div dir="rtl" align='right'>فهرست محتوا</div>

### <div dir="rtl" align='right'>شروع سریع</div>

<div dir="rtl" align='right'>  
<ul>  
  <li><a href="#qs-example-apps">اپلیکیشن های نمونه</a>  
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
            <li><a href="#qs-huawei-referrer-api">Huawei Referrer API</a></li>  
        </ul>  
      </li>  
    </ul>  
    </li>  
  </ul>  
  <li><a href="#qs-integ-sdk">پیاده سازی SDK داخل اپلیکیشن</a>  
    <ul>  
      <li><a href="#qs-integ-basic-setup">راه اندازی اولیه</a></li>  
      <ul>  
        <li><a href="#qs-integ-basic-setup-native">Native App SDK</a></li>  
        <li><a href="#qs-integ-basic-setup-web">Web View SDK</a></li>  
      </ul>  
      <li><a href="#qs-integ-session-tracking">ردیابی session (Session Tracking)</a></li>  
      <ul>  
        <li><a href="#qs-integ-session-tracking-api14"> API بالاتر از 14</a></li>  
        <li><a href="#qs-integ-session-tracking-api9">API بین 9 و 13</a></li>  
      </ul>  
      <li><a href="#qs-sdk-signature">امضا (SDK Signature)</a></li>                   
      <li><a href="#qs-adtrace-logging">لاگ ادتریس</a></li>  
      <li><a href="#qs-build-the-app">ساخت اپلیکیشن</a></li>  
    </ul>  
    </li>  
  </ul>  
  </li>  
</ul>  
</div>  

### <div dir="rtl" align='right'>لینک عمیق (Deep Linking)</div>

<div dir="rtl" align='right'>  
<ul>  
  <li><a href="#dl-overview">بررسی اجمالی deep linking</a></li>                    
  <li><a href="#dl-standard">سناریو deep link استاندارد</a></li>  
  <li><a href="#dl-deferred">سناریو deferred deep link</a></li>  
  <li><a href="#dl-reattribution">Reattribution با استفاده از deep link</a></li>  
</ul>  
</div>  

### <div dir="rtl" align='right'>ردیابی رویداد (Event Tracking)</div>

<div dir="rtl" align='right'>  
<ul>  
  <li><a href="#et-track-event">event tracking معمولی</a></li>                   
  <li><a href="#et-track-revenue">event tracking درآمدی(Revenue)</a></li>  
  <li><a href="#et-revenue-deduplication">جلوگیری از revenue تکراری</a></li>  
</ul>  
</div>  

### <div dir="rtl" align='right'>پارامترهای دلخواه</div>

<div dir="rtl" align='right'>  
<ul>  
  <li><a href="#cp-overview">بررسی اجمالی پارامترهای دلخواه</a></li>  
  <li><a href="#cp-ep">پارامترهای event</a>  
    <ul>  
      <li><a href="#cp-ep-callback">پارامترهای event callback </a></li>                   
      <li><a href="#cp-ep-partner">پارامترهای event partner</a></li>  
      <li><a href="#cp-ep-id">Event callback identifier</a></li>  
      <li><a href="#cp-ep-value">Event value</a></li>  
    </ul>  
  </li>                   
  <li><a href="#cp-sp" >Session parameters</a>  
    <ul>  
      <li><a href="#cp-sp-callback">پارامترهای session callback</a></li>                   
      <li><a href="#cp-sp-partner">پارامترهای session partner</a></li>  
      <li><a href="#cp-sp-delay-start">شروع با تاخیر</a></li>  
    </ul>  
  </li>  
</ul>  
</div>  

### <div dir="rtl" align='right'>قابلیت های بیشتر</div>

<div dir="rtl" align='right'>  
<ul>  
  <li><a href="#af-push-token">Push Token ( تعداد حذف اپلیکیشن)</a></li>   
  <li><a href="#af-ad-revenue">Attribution callback</a></li>  
    <li><a href="#af-attribution-callback">Ad revenue tracking</a></li>  
        <li><a href="#af-subscriptions">Subscription tracking</a></li>  
  <li><a href="#af-session-event-callbacks">Callback های event و session</a></li>  
  <li><a href="#af-user-attribution">Attribution کاربر</a></li>                   
  <li><a href="#af-di">IDهای دستگاه</a>  
    <ul>  
      <li><a href="#af-di-gps-adid">Google Play Services advertising identifier (GPS Ad Id)</a></li>                   
      <li><a href="#af-di-amz-adid">Amazon advertising identifier</a></li>  
      <li><a href="#af-di-adid">AdTrace device identifier</a></li>  
    </ul>  
  </li>                   
  <li><a href="#af-pre-installed-trackers">اپلیکیشن های preinstalled</a></li>                   
  <li><a href="#af-offline-mode">حالت آفلاین</a></li>                   
  <li><a href="#af-disable-tracking">غیرفعال سازی</a></li>                   
  <li><a href="#af-event-buffering">Event buffering</a></li>                    
  <li><a href="#af-background-tracking">Background tracking</a></li>                   
  <li><a href="#af-location">موقعیت مکانی</a></li>                    
  <li><a href="#af-gdpr-forget-me">GPDR</a></li>  
  <ul>  
    <li><a href="#af-third-party-sharing">Third-party sharing</a></li>                
        <li><a href="#af-disable-third-party-sharing">غیرفعال کردن Third-party sharing</a></li>                    
            <li><a href="#af-enable-third-party-sharing">فعال کردن Third-party sharing</a></li>                    
</ul>  
        <li><a href="#af-measurement-consent">سنجش رضایت</a></li>                    

</div>  

### <div dir="rtl" align='right'>تست و عیب یابی</div>

<div dir="rtl" align='right'>  
<ul>  
  <li><a href="#ts-reset-gps-ad-id">چگونه می توانم GPS Ad Id را ریست کنم؟</a></li>                    
  <li><a href="#ts-session-failed">من خطای  "Session failed (Ignoring too frequent session. ...)" را مشاهده می کنم.</a></li>  
  <li><a href="#ts-broadcast-receiver">آیا broadcast receiver من اطلاعات install referrer را دریافت می کند؟</a></li>  
  <li><a href="#ts-event-at-launch">آیا می توانم هنگام راه اندازی اپلیکیشن اقدام به ایجاد یک event کنم؟</a></li>  
</ul>  
</div>  

## <div dir="rtl" align='right'>پیاده سازی سریع</div>

### <div id="qs-example-apps" dir="rtl" align='right'>اپلیکیشن های نمونه</div>

<div dir="rtl" align='right'>  
<ul>  
<li>برای پیاده سازی به صورت native می توانید  ازمثال های <a href="https://github.com/adtrace/adtrace_sdk_android/tree/master/example-app-java"> Java</a> ,<a href="https://github.com/adtrace/adtrace_sdk_android/tree/master/example-app-kotlin">kotlin</a> , <a href="https://github.com/adtrace/adtrace_sdk_android/tree/master/example-app-webbridge">webbridge</a> ,<a href="https://github.com/adtrace/adtrace_sdk_android/tree/master/example-app-app-tv">android-tv</a> استفاده کنید.</li>  
</li>  
</ul>  
</div>  

### <div id="qs-getting-started" dir="rtl" align='right'>شروع پیاده سازی</div>

<div dir="rtl" align='right'>  
موارد زیر حداقل موارد لازم برای تعامل SDK ادتریس درون پروژه اندروید میباشد. در نظر ما شما برای توسعه اندروید از Android Studio و API حداقل <strong>9 (Gingerbread)</strong> یا به بالا را استفاده میکنید.  
</div>  

### <div id="qs-add-sdk" dir="rtl" align='right'>افزودن SDK به پروژه</div>

<div dir="rtl" align='right'>  
موارد زیر را به فایل <code>build.gradle</code> داخل قسمت  <code>dependencies</code> اضافه کنید:  
</div>  
<br/>  

```
implementation 'io.adtrace.adtrace-android:2.0.1'
implementation 'com.android.installreferrer:installreferrer:2.2'
```  

<br/>  
<div dir="rtl" align='right'>  
اگر مایل هستید که SDK ادتریس را داخل web view اپ خود استفاده کنید، این قسمت را همانند بالا داخل <code>dependencies</code> اضافه کنید:  
</div>  
<br/>  

```gradle  
implementation 'io.adtrace.sdk:adtrace-android-webbridge:2.0.1' 
```  

### <div id="qs-add-gps" dir="rtl" align='right'>افزودن سرویس های گوگل پلی</div>

<div dir="rtl" align='right'>  
از تاریخ 1 آگوست 2014، اپلیکیشن های داخل گوگل پلی باید از <a href="https://support.google.com/googleplay/android-developer/answer/6048248?hl=en">شناسه تبلیغاتی گوگل</a> برای شناسایی یکتابودن دستگاه استفاده کنند. برای فعالسازی امکان استفاده از این شناسه خط زیر را به <code>dependencies</code> فایل <code>build.gradle</code> خود اضافه کنید:  
</div>  
<br/>  

```gradle  
implementation 'com.google.android.gms:play-services-ads-identifier:17.0.0'  
```  

<br/>  
<div dir="rtl" align='right'>  
<strong>نکته: </strong> SDK ادتریس محصور به استفاده از ورژن خاصی از <code>play-services-ads-identifier</code> گوگل پلی نیست. بنابراین استفاده از آخرین نسخه این کتابخانه برای ادتریس مشکلی ایجاد نمی کند.  
</div>  
<br/>  

### <div id="qs-add-permissions" dir="rtl" align='right'>افزودن مجوزها</div>

<div dir="rtl" align='right'>  
در ادامه دسترسی های زیر را در فایل <code>AndroidManifest.xml</code> خود اضافه کنید.   
</div>  
<br/>  

```xml  
<uses-permission android:name="android.permission.INTERNET"/>  
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>  
```  

<br/>  
<div dir="rtl" align='right'>  
اگر استور مدنظر شما <strong>به جز گوگل پلی</strong> باشد، دسترسی زیر را نیز اضافه کنید:  
</div>  
<br/>  

```xml  
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>  
```  




### <div id="qs-proguard-settings" dir="rtl" align='right'>تنظیمات Proguard</div>

<div dir="rtl" align='right'>  
اگر از Progaurd استفاده میکنید، دستورهای زیر را در فایل Progaurd خود اضافه کنید:  
</div>  
<br/>  

```  
-keep public class io.adtrace.sdk.** { *; }  
-keep class com.google.android.gms.common.ConnectionResult {  
 int SUCCESS;}  
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {  
 com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);}  
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {  
 java.lang.String getId(); boolean isLimitAdTrackingEnabled();}  
-keep public class com.android.installreferrer.** { *; }  
```  

<br/>  
<div dir="rtl" align='right'>  
اگر هدف شما استوری به <strong>جز گوگل پلی</strong> میباشد، از موارد زیر استفاده کنید.    
</div>  

```  
-keep public class io.adtrace.sdk.** { *; }  
```  


### <div id="qs-ir" dir="rtl" align='right'>تنظیمات Install referrer</div>

<div dir="rtl" align='right'>  
برای آنکه به درستی نصب(install) یک اپلیکیشن به منبع (source) خودش اختصاص داده (attribute) شود، ادتریس به اطلاعاتی درمورد <strong>install referrer</strong> نیاز دارد. این مورد با استفاده از <strong>Google Play Referrer API</strong> یا توسط <strong>Google Play Store intent</strong> بواسطه یک broadcast receiver دریافت می شود.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
<strong>نکته مهم:</strong> Google Play Referrer API جدیدا راه حلی قابل اعتمادتر و با امنیت بیشتر برای جلو گیری از تقلب click injection  توسط گوگل  جدیدا معرفی شده است. <strong>به شدت</strong> توصیه می شود که از این مورد در اپلیکیشن های خود استفاده کنید. Google Play Store intent امنیت کمتری در این مورد دارد و در آینده deprecate خواهد شد.  
</div>  

#### <div id="qs-ir-gpr-api" dir="rtl" align='right'>Google Play Referrer API</div>

<div dir="rtl" align='right'>  
به منظور استفاده از این کتابخانه مطمئن شوید که <a href="#qs-add-sdk">افزودن SDK به پروژه</a> را به درستی پیاده سازی کردید و خط زیر به <code>build.gradle</code> اضافه شده است:  
</div>  
<br/>  

```gradle  
implementation 'com.android.installreferrer:installreferrer:2.2'  
```  

<br/>  
<div dir="rtl" align='right'>  
همچنین مطمئن شوید که درصورت داشتن Progaurd، بخش <a href="qs-proguard-settings">تنظیمات Progaurd</a> به صورت کامل اضافه شده است، مخصوصا دستور زیر:  
</div>  
<br/>  

```  
-keep public class com.android.installreferrer.** { *; }  
```  

#### <div id="qs-ir-gps-intent" dir="rtl" align='right'>Google Play Store intent</div>

<div dir="rtl" align='right'>  
گوگل طی <a href="https://android-developers.googleblog.com/2019/11/still-using-installbroadcast-switch-to.html">بیانیه ای</a> اعلام کرد که از 1 مارچ 2020 دیگر اطلاعات <code>INSTALL_REFERRER</code> را به صورت broadcast ارسال نمی کند، برای همین به رویکرد <a href="#qs-ir-gpr-api">Google Play Referrer API</a> مراجعه کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
شما باید اطلاعات <code>INSTALL_REFERRER</code> گوگل پلی را توسط یک broadcast receiver دریافت کنید. اگر از <strong>broadcast receiver خود</strong> استفاده نمی کنید، تگ <code>receiver</code> را داخل تگ <code>application</code> درون فایل <code>AndroidManifest.xml</code> خود اضافه کنید:  
</div>  
<br/>  

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

<br/>  
<div dir="rtl" align='right'>  
اگر قبلا از یک broadcast receiver برای دریافت اطلاعات <code>INSTALL_REFERRER</code> استفاده می کردید، از <a href="../english/multiple-receivers.md">این دستورالعمل</a>  برای اضافه نمودن broadcast receiver ادتریس استفاده کنید.  
</div>  


### <div id="qs-huawei-referrer-api" dir="rtl" align='right'>Huawei Referrer API</div>
<div dir="rtl" align='right'>  
از نسخه 2.0.1 به بعد SDK ادتریس دستگاه های Huawei را پشتیبانی می کند. نسخه   Huawei App Gallery باید از 10.4 و بالاتر باشد. با اضافه کردن پلاگین مخصوص این امکان فراهم شده و نیازی به اعمال تنظیمات دیگری نیست.  
</div>  

### <div id="qs-integ-sdk" dir="rtl" align='right'>پیاده سازی SDK داخل اپلیکیشن</div>

<div dir="rtl" align='right'>  
ابتدا session tracking ساده را پیاده سازی می کنیم.  
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
ما توصیه میکنیم یک کلاس <a href="http://developer.android.com/reference/android/app/Application.html">Application</a> برای راه اندازی SDK درون اپلیکیشن خود ایجاد کنید :  
<ul>  
<li>یک کلاس ایجاد کنید که <code>Application</code> را <code>extend</code> کند.</li>  
<code>AndroidManifest.xml</code> را باز کنید و وارد تگ <code> application</code></li> شوید.
<li>مقدار <code>android:name</code>را همان کلاسی که ایجاد کرده اید قرار دهید.</li>  
<li>در مثال زیر نام کلاس ایجاد شده <code>GlobalApplication</code> است، بنابراین فایل manifest ما به صورت زیر خواهد بود:</li>  
</ul>  
</div>  
<br/>  

```xml
<application  
  android:name=".GlobalApplication"  
	...
 </application>  
```  

<br/>  
<div dir="rtl" align='right'>  
<ul>  
<li>داخل کلاس <code>Application</code> متد <code>onCreate</code> را پیدا کنید و کد زیر را برای راه اندازی  SDK ادتریس به آن اضافه کنید:</li>  
</ul>  
</div>  
<br/>  

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
<br/>  
<div dir="rtl" align='right'>  
مقدار <code>{YourAppToken}</code> را با token اپلیکیشن خود که از  <a href="http://panel.adtrace.io">پنل</a> دریافت کرده اید، عوض کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
با توجه به نوع ساخت اپلیکیشن شما برای تست یا انتشار، باید  <code>environment</code> را یکی از مقادیر زیر قرار دهید:  
</div>  
<br/>  

```java  
String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;  
String environment = AdTraceConfig.ENVIRONMENT_PRODUCTION;  
```  

<br/>  
<div dir="rtl" align='right'>  
<strong>نکته:</strong> این مقدار تنها در زمان تست(debug) اپلیکیشن شما باید مقدار <code> AdTraceConfig.ENVIROMENT_SANDBOX</code> قرار بگیرد. این پارامتر را به <code>AdTraceConfig.ENVIROMENT_PRODUCTION</code> قبل از انتشار اپلیکیشن خود تغییر دهید.  
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
<br/>  

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

<br/>  
<div dir="rtl" align='right'>  
بعد از مراحل بالا پل ارتباطی میان جاوااسکریپت و اندروید ادتریس به صورت موفقیت آمیز برقرار خواهد شد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
داخل فایل HTML خود فایل های جاوا اسکریپتی را در در پوشه <a href="https://github.com/adtrace/adtrace_sdk_android/tree/master/android-sdk-plugin-webbridge/src/main/assets  
">assets</a> قرار دارند را وارد کنید:  
</div>  
<br/>  

```html  
<script type="text/javascript" src="adtrace.js"></script>  
<script type="text/javascript" src="adtrace_event.js"></script>  
<script type="text/javascript" src="adtrace_config.js"></script>  
```  

<br/>  
<div dir="rtl" align='right'>  
بعد از وارد کردن فایلهای بالا، دستور زیر را داخل جاوااسکریپت  خود برای راه اندازی SDK کدهای زیر را  اضافه کنید:  
</div>  
<br/>  

```js  
let yourAppToken = '{YourAppToken}';  
let environment = AdTraceConfig.EnvironmentSandbox;  
let adtraceConfig = new AdTraceConfig(yourAppToken, environment);  
  
AdTrace.onCreate(adtraceConfig);  
```  

<br/>  
<div dir="rtl" align='right'>  
مقدار <code>{YourAppToken}</code> را با token اپلیکیشن خود که از <a href="http://panel.adtrace.io">پنل</a> دریافت کرده اید، عوض کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
با توجه به نوع ساخت اپلیکیشن شما برای تست یا انتشار، باید  <code>environment</code> را یکی از مقادیر زیر قرار دهید:  
</div>  
<br/>  

```js  
let environment = AdTraceConfig.EnvironmentSandbox;  
let environment = AdTraceConfig.EnvironmentProduction;  
```  

<br/>  
<div dir="rtl" align='right'>  
<strong>نکته:</strong> این مقدار تنها در زمان تست(debug) اپلیکیشن شما باید مقدار <code> AdTraceConfig.ENVIROMENT_SANDBOX</code> قرار داده شود. حتما قبل از انتشار اپلیکیشن این پارامتر را به <code>AdTraceConfig.ENVIROMENT_PRODUCTION</code>      تغییر دهید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
 ما از enviroment برای تفکیک ترافیک داده واقعی و آزمایشی استفاده می کنیم.  
</div>  

### <div id="qs-integ-session-tracking" dir="rtl" align='right'>ردیابی نشست (Session Tracking)</div>

<div dir="rtl" align='right'>  
<strong>نکته مهم:</strong> این مرحله <strong>اهمیت بالایی</strong> دارد . از <strong>پیاده سازی صحیح آن  مطمئن شوید.</strong>  
</div>  

### <div id="qs-integ-session-tracking-api14" dir="rtl" align='right'>API بالاتر از 14</div>

<div dir="rtl" align='right'>  
<ul>  
<li>یک کلاس private  در کلاس <code>Application</code>  خود بسازید به صورتی که <code>ActivityLifecycleCallbacks</code> را <code>implement</code> کند. اگر با خطای دسترسی مواجه شدید، بدین معناست که API اپلیکیشن شما زیر 14 است و باید از <a href="#qs-integ-session-tracking-api9">این دستورالعمل</a> استفاده کنید.</li>  
<br/>  
<li>داخل متد <code>onActivityResumed(Activity activity)</code> دستور <code>()AdTrace.onResume</code> را اضافه کنید. و داخل متد <code>onActivityPaused(Activity activity)</code> دستور <code>()AdTrace.onPause</code> را قرار دهید.</li>  
<li>درون متد <code>()onCreate</code> درمحلی که ادتریس پیاده سازی شده است، یک <code>Object</code> از این کلاس را به متد <code>registerActivityLifecycleCallbacks</code>وارد کنید.</li>  
</ul>  
</div>  
<br/>  

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
		  
	 private static final class AdTraceLifecycleCallbacks implements ActivityLifecycleCallbacks 
	 {
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
اگر <code>minSdkVersion</code> اپلیکیشن شما داخل gradle بین 9 و 13 باشد، درنظر داشته باشید که برای طولانی مدت بهتر است به 14 ارتقا پیدا کند تا تعامل بهتری با ادتریس داشته باشد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
برایsession tracking باید دو متد از ادتریس را در هنگامی که Activity متوقف میشود و یا ادامه پیدا میکند فراخوانی شود. در غیراین صورت SDK ممکن است ابتدا یا پایان session را از دست بدهد. برای این کار شما باید <strong>موارد زیر را برای هر Activity اپلیکیشن اجرا کنید:</strong>  
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
<br/>  

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

<br/>  
<div dir="rtl" align='right'>  
فراموش نکنید که این مراحل را برای <strong>هر Activity</strong> اجرا کنید.  
</div>  

### <div id="qs-sdk-signature" dir="rtl" align='right'>امضا (SDK Signature)</div>

<div dir="rtl" align='right'>  
برای استفاده از این ویژگی باید مدیر اکانت در داخل <a href="http://panel.adtrace.io">پنل</a> فعال کند. برای اطلاعات بیشتر میتوانید از طریق <a href="info@adtrace.io">info@adtrace.io</a> در تماس باشید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
اگر در <a href="http://panel.adtrace.io">پنل</a> SDK signature فعال شده است، از متد زیر برای پیاده سازی استفاده کنید:  
</div>  
<br/>  
<div dir="rtl" align='right'>  
 App Secret با استفاده از متد <code>setAppSecret</code> در <code>AdTraceConfig</code> فراخوانی می شود:  
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

### <div id="qs-adtrace-logging" dir="rtl" align='right'>لاگ(Log) ادتریس</div>

<div dir="rtl" align='right'>  
برای کنترل کردن نحوه لاگ در حین تست, از طریق متد <code>setLogLevel</code> که در <code>AdTraceConfig</code> قرار دارد استفاده کنید:  
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

<br/>  
<div dir="rtl" align='right'>  
در صورتی که می خواهید همه لاگ های ادتریس غیر فعال شود، علاوه بر مقدار <code>AdTraceConfig.LogLevelSuppress</code> باید در تنظیمات ادتریس    قرار دهید :  
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

### <div id="qs-build-the-app" dir="rtl" align='right'>build اپلیکیشن</div>

<div dir="rtl" align='right'>  
حال اپلیکیشن خود را کامپایل کنید و اجرا کنید. در قسمت <code>LogCat</code> میتوانید فیلتر <code>tag:AdTrace</code> را  اضافه کنید تا فقط لاگ های مربوط به ادتریس را مشاهده نمایید.  
</div>  

## <div dir="rtl" align='right'>لینک دهی عمیق(Deep Linking)</div>

### <div id="dl-overview" dir="rtl" align='right'>بررسی اجمالی deep linking</div>

<div dir="rtl" align='right'>  
اگر از url ادتریس با فعال بودن تنظیمات deep link برایtracking استفاده می کنید، امکان دریافت اطلاعات و محتوا deep link از طریق ادتریس فراهم می باشد. کاربر ممکن است که قبلا اپلیکیشن را داشته باشد(سناریو deep linking استاندارد) یا اگر اپلیکیشن را نصب نداشته باشد(سناریو deferred deep linking    ) این فابلیت به کار برده شود. در سناریو deep linking استاندارد ، دستگاه اندروید محتوا deep link را می تواند در اختیار شما قرار دهد. در سناریو deferred deep linking  خود پلتفرم اندروید توانایی پشتیبانی به خودی خود را ندارد، در این صورت SDK ادتریس مکانیزم مشخصی را برای دریافت این اطلاعات به شما ارائه می دهد.</div>  

### <div id="dl-standard" dir="rtl" align='right'>سناریوی deep link استاندارد</div>

<div dir="rtl" align='right'>  
اگر کاربر اپلیکیشن شما را نصب داشته باشد و شما بخواهید از طریق کلیک tracker ادتریس به اپلیکیشن منتقل شود، باید deep link را درون اپلیکیشن خود فعالسازی کنید. این راه از طریق یک <strong>scheme یکتا</strong> درون Activity مورد نظر فعال میشود. برای این کار درون <code>AndroidManifest.xml</code> یک تگ <code>intent-filter</code> به Activity مورد نظر تعریف کنید که مقدار <code>android:scheme</code> آن با یک مقدار مناسب پر شده باشد:  
</div>  
<br/>  

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


<div dir="rtl" align='right'>در صورتی که می خواهید اپلیکیشن شما با tracker اجرا شود از scheme تعریف شده استفاده کنید و پارامترها را مستقیم به url ایجاد شده برای <code>deep_link</code> اضافه کنید. مثال زیر نمونه ای از این کار را نشان می دهد:  
 </div>  

```  
https://app.adtrace.io/adt1ex?deep_link=adtraceExample%3A%2F%2F 
```  
 <div dir="rtl" align='right'>به خاطر داشته باشید که <strong>URL حتما باید encode شود</strong>  
 </div>  
 <br>  

<br/>  
  <br>  
 </br>  
<div dir="rtl" align='right'>  
 تنظیمات activity در <code>android:launchMode</code> در <code>AndroidManifest.xml</code>مشخص می کند که محل تحویل دادن <code>deep_link</code> در کدام activity باشد. برای اطلاعات بیشتبر به <a href="https://developer.android.com/guide/topics/manifest/activity-element.html">document رسمی اندروید</a> مراجعه کنید.  
</div>  
<div dir="rtl" align='right'>  
در دو قسمت از Activity اطلاعات deep link از طریق   <code>Intent</code> قابل استفاده است، از طریق <code>onCreate</code> <strong>یا متد</strong> <code>onNewIntent</code> این امکان فراهم می باشد که به صورت زیر آموزش داده شده است:  
</div>  
<br/>  

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

### <div id="dl-deferred" dir="rtl" align='right'>سناریوی deferred deep link</div>

<div dir="rtl" align='right'>  
این سناریو هنگامی رخ می دهد که بعد از کلیک کردن tracker ادتریس اپلیکیشن مورد نظر درون دستگاه کاربر نصب نباشد، و به گوگل پلی برای دانلود و نصب این اپلیکیشن ارجاع می شود. بعد از باز کردن اولین بار اپلیکیشن پارامترهای deep link به اپلیکیشن منتقل می شود. برای این کار SDK ادتریس به صورت خودکار deep link را باز می کند و نیازی به تنظیمات اضافه نیست.  
</div>  

#### <div dir="rtl" align='right'>Deferred deep linking callback</div>

<div dir="rtl" align='right'>  
برای شناسایی deep link میتوانید از طریق یک متد callback داخل تنظیمات ادتریس استفاده کنید:  
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

<br/>  
<div dir="rtl" align='right'>  
بعد از دریافت اطلاعات deep link از طریق SDK ادتریس، محتوا این اطلاعات با استفاده از یک listener و مقدار boolean به شما بازمی گرداند. مقدار بازگشتی با توجه به تصمیم شما مبنی بر اینکه می خواهید Activity موردنظر با آن scheme مربوطه را دارید یا خیر.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
اگر مقدار بازگشتی شما <code>true</code> باشد، همانند <a href="#dl-standard">سناریو لینک دهی استاندارد</a> انجام میشود. اگر میخواهید که SDK آن Activity مورد نظر را باز نکند باید <code>false</code> را برگردانید (با توجه به محتوا deep link).  
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

adtraceConfig.setDeferredDeeplinkCallback(function (deeplink) {
	});  
AdTrace.onCreate(adtraceConfig);  
```  

<br/>  
<div dir="rtl" align='right'>  
در این سناریو ب، یک مورد اضافی باید به تنظیمات اضافه شود. هنگامی که SDK ادتریس اطاعات deep link را دریافت کرد، شما امکان این را دارید که SDK، با استفاده از این اطلاعات باز شود یا خیر که از طریق  متد <code>setOpenDeferredDeeplink</code> قابل استفاده است:  
</div>  
<br/>  

```js  
// ...  
  
function deferredDeeplinkCallback(deeplink) {
		}  
  
let adtraceConfig = new AdTraceConfig(appToken, environment);  
adtraceConfig.setOpenDeferredDeeplink(true);  
adtraceConfig.setDeferredDeeplinkCallback(deferredDeeplinkCallback);  
  
AdTrace.start(adtraceConfig);  
  
```  

<div dir="rtl" align='right'>  
توجه فرمایید که اگر callback تنظیم نشود، <strong>SDK ادتدریس در حالت پیشفرض تلاش می کند تا URL را اجرا کند</strong>.  
</div>  

</td>  
</tr>  
</table>  

### <div id="dl-reattribution" dir="rtl" align='right'>Reattribution با استفاده از deep link</div>

<div dir="rtl" align='right'>  
ادتریس این امکان را دارد تا از طریق deep link کمپین ها را مجددا اطلاعات گذاری کند.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
اگر شما از این ویژگی استفاده می کنید، برای اینکه کاربر به درستی مجددا attribute شود، نیاز دارید یک دستور اضافی به اپلیکیشن خود اضافه کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
هنگامی که اطلاعات deep link را دریافت میکنید، متد <code>AdTrace.appWillOpenUrl(Uri, Context)</code>  را فراخوانی کنید. از طریق این SDK تلاش میکند تا ببیند اطلاعات جدیدی درون deep link برای attribute کردن قرار دارد یا خیر. اگر وجود داشت، این اطلاعات به سرور ارسال میشود.  اگر کاربر از طریق کلیک بر tracker ادتریس مجددا attribute شود، میتوانید از قسمت <a href="#af-attribution-callback">اتریبیوشن کالبک</a> اطلاعات جدید را برای این کاربر دریافت کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
فراخوانی متد <code>AdTrace.appWillOpenUrl(Uri, Context)</code> باید مثل زیر باشد:  
</div>  
<br/>  

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
<br/>  

```js  
AdTrace.appWillOpenUrl(deeplinkUrl);
```  

## <div dir="rtl" align='right'>ردیابی رویداد(Event Tracking)</div>

### <div id="et-track-event" dir="rtl" align='right'>event tracking معمولی</div>

<div dir="rtl" align='right'>  
شما برای یک event میتوانید از انواع eventها درون اپلیکیشن خود استفاده کنید. فرض کنید که می خواهید لمس یک دکمه را رصد کنید. باید ابتدا یک event درون <a href="http://panel.adtrace.io">پنل</a> خود ایجاد کنید. اگر فرض کنیم که token event شما <code>adt1ex</code> باشد، سپس در متد <code>onClick</code> دکمه مربوطه کد زیر را برای tracking لمس دکمه اضافه کنید:  
</div>  

</td>  
</tr>  
<tr>  
<td>  
<b>Native App SDK</b>  
</td>  
</tr>  
<tr>  
<td>  

```java  
AdTraceEvent event = new AdTraceEvent("adt1ex");
AdTrace.trackEvent(event);  
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
let adtraceEvent = new AdTraceEvent('adt1ex');
AdTrace.trackEvent(adtraceEvent);
```  

### <div id="et-track-revenue" dir="rtl" align='right'>event tracking درآمدی(Revenue)</div>

<div dir="rtl" align='right'>  
اگر کاربران شما از طریق کلیک بر روی تبلیغات یا پرداخت درون اپلیکیشن ، event می توانند ایجاد کنند، شما می توانید آن درآمد را از طریق event مشخص رصد کنید. اگر فرض کنیم که یک کلیک خاص به ارزش یک سنت از واحد یورو باشد، کد شما برای track این event به صورت زیر می باشد:  
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
let adtraceEvent = new AdTraceEvent('adt1ex');
adtraceEvent.setRevenue(52000, 'IRR');
AdTrace.trackEvent(adtraceEvent); 
``` 
</td>    
</tr>    
</table>    

<div dir="rtl" align='right'>  
این ویژگی میتواند با پارامترهای callback نیز ترکیب شود.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
هنگامی که واحد پول را تنظیم کردید، ادتریس  درآمدهای ورودی را به صورت خودکار به انتخاب شما تبدیل می کند.  
</div>  

### <div id="et-revenue-deduplication" dir="rtl" align='right'>جلوگیری از revenue تکراری</div>

<div dir="rtl" align='right'>  
شما می توانید یک شناسه خرید مخصوص برای جلوگیری از تکرار event درآمدی استفاده کنید. 10 شناسه آخر ذخیره می شود و درآمدهای eventهایی که شناسه خرید تکراری دارند درنظر گرفته نمی شوند. این برای موارد خرید درون اپلیکیشن  بسیار کاربرد دارد. به مثال زیر توجه کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
اگر میخواهید پرداخت درون اپلیکیشن را رصد کنید، فراخوانی متد <code>trackEvent</code> را زمانی انجام دهید که خرید انجام شده است و محصول خریداری شده است. بدین صورت شما از تکرار event درآمدی جلوگیری کرده اید.  
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
let adtraceEvent = new AdTraceEvent('adt1ex'); 
adtraceEvent.setRevenue(52000, 'IRR'); 
adtraceEvent.setOrderId('{OrderId}'); 
AdTrace.trackEvent(event); 
``` 
</td>    
</tr>    
</table>    

## <div dir="rtl" align='right'>پارامترهای دلخواه</div>

### <div id="cp-overview" dir="rtl" align='right'>بررسی اجمالی پارامترهای دلخواه</div>

<div dir="rtl" align='right'>  
علاوه بر داده هایی که SDK ادتریس به صورت خودکار جمع آوری می کند، شما از ادتریس می توانید مقدارهای سفارشی زیادی را با توجه به نیاز خود (شناسه کاربر، شناسه محصول و ...) به event یا session خود اضافه کنید. پارامترهای سفارشی تنها به صورت خام و export شده قابل دسترسی میباشد و در <a href="http://panel.adtrace.io">پنل</a> ادتریس قابل نمایش <strong>نمی باشد</strong>.</div>   
<br/>  
<div dir="rtl" align='right'>  
شما از <strong>پارامترهای callback</strong> برای استفاده داخلی خود بکار می برید و از <strong>پارامترهای partner</strong> برای به اشتراک گذاری به شریکان خارج از اپلیکیشن استفاده خواهید کرد. اگر از یک مقدار (مثل شناسه محصول) برای خود و شریکان خارجی استفاده می کنید، ما پیشنهاد می کنیم که از هر دو پارامتر partner و callback استفاده کنید.  
</div>  

### <div id="cp-ep" dir="rtl" align='right'>پارامترهای event</div>

### <div id="cp-ep-callback" dir="rtl" align='right'>پارامترهای event callback </div>

<div dir="rtl" align='right'>  
شما میتوانید یک آدرس callback برای event خود داخل <a href="http://panel.adtrace.io">پنل</a> اضافه کنید. ادتریس یک درخواست GET به آن آدرسی که اضافه نموده اید، ارسال خواهد کرد. همچنین پارامترهای callback برای آن event را از طریق متد <code>addCallbackParameter</code> برای آن event قبل از ترک آن استفاده کنید. ما این پارامترها را به آخر آدرس callback شما اضافه خواهیم کرد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
به عنوان مثال اگر شما آدرس <code>http://www.example.com/callback</code> را به event خود اضافه نموده اید، event tracking به صورت زیر خواهد بود:  
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
let adtraceEvent = new AdTraceEvent('adt1ex');  
adtraceEvent.addCallbackParameter('key', 'value');  
adtraceEvent.addCallbackParameter('foo', 'bar');  
AdTrace.trackEvent(adtraceEvent);  
```  
</td>  
</tr>  
</table>  

<br/>  
<div dir="rtl" align='right'>  
در اینصورت ما event شما را رصد خواهیم کرد و یک درخواست به صورت زیر ارسال خواهیم کرد:  
</div>  
<br/>  

```  
http://www.example.com/callback?key=value&foo=bar  
```  

<br/>  
<div dir="rtl" align='right'>  
ادتریس از پارامترهای <code>placeholders</code> پشتیبانی می کند. برای مثال <code>gps_adid</code> که می توانید آن را در پارامترهای خود اضافه کرده و از سمت ما دریافت کنید. در نظر داشته باشید کهما پارامترهای دلخواه شما را <strong>ذخیره نمی کنیم</strong> بلکه تنها سمت شما فرستاده می شود. اگر برای یک event مشخص callbackمشخص نکرده باشید ما حتی آن پارامترها را نمی خوانیم.  
</div>  
<br/>  

### <div id="cp-ep-partner" dir="rtl" align='right'>پارامترهای event partner</div>

<div dir="rtl" align='right'>  
شما همچنین پارامترهایی را برای شریکان خود تنظیم کنید که درون <a href="http://panel.adtrace.io">پنل</a> ادتریس فعالسازی میشود.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
این پارامترها به صورت callback که در بالا مشاهده میکنید استفاده میشود، فقط از طریق متد <code>addPartnerParameter</code> درون یک شی از <code>AdTraceEvent</code> فراخوانی میشود.  
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
AdTraceEvent adtraceEvent = new AdTraceEvent("adt1ex");  
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
let adtraceEvent = new AdTraceEvent('adt1ex');  
adtraceEvent.addPartnerParameter('key', 'value');  
adtraceEvent.addPartnerParameter('foo', 'bar');  
AdTrace.trackEvent(adtraceEvent);  
```  
</td>  
</tr>  
</table>  

### <div id="cp-ep-id" dir="rtl" align='right'>Event callback identifier</div>

<div dir="rtl" align='right'>  
شما می توانید یک شناسه به صورت String برای هریک از event هایی که رصد کرده اید اضافه کنید. این شناسه بعدا در callback موفق یا رد شدن آن event به دست شما خواهد رسید که متوجه شوید این tracking به صورت موفق انجام شده است یا خیر. این مقدار از طریق متد <code>setCallbackId</code> درون <code>AdTraceEvent</code> قابل تنظیم است.  
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
let adtraceEvent = new AdTraceEvent('adt1ex');  
adtraceEvent.setCallbackId('Your-Custom-Id');  
AdTrace.trackEvent(adtraceEvent);  
```  
</td>  
</tr>  
</table>  

### <div id="cp-ep-value" dir="rtl" align='right'>Event value</div>

<div dir="rtl" align='right'>  
شما همچنین می توانید یک String دلخواه به event خود  اضافه کنید. این مقدار از طریق <code>setEventValue</code> قابل استفاده است:  
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
AdTraceEvent adtraceEvent = new AdTraceEvent("adt1ex");  
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
let adtraceEvent = new AdTraceEvent('adt1ex');  
adtraceEvent.setEventValue('Your-Value');  
AdTrace.trackEvent(adtraceEvent);  
```  
</td>  
</tr>  
</table>  

### <div id="cp-sp" dir="rtl" align='right'>Session parameters</div>

<div dir="rtl" align='right'>  
پارامترهای session به صورت محلی ذخیره می شوند و به همراه هر <strong>event</strong> یا <strong>session</strong> ادتریس ارسال خواهند شد. هنگامی که هرکدام از این پارامترها  اضافه شدند، ما آنها را ذخیره خواهیم کرد پس نیازی به اضافه کردن مجدد آنها نیست. افزودن مجدد پارامترهای مشابه تاثیری نخواهد داشت.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
این پارامترها می توانند قبل از شروع SDK ادتریس تنظیم شوند. اگر می خواهید هنگام نصب آنها را ارسال کنید، ولی پارامترهای آن بعد از نصب دراختیار شما قرار خواهد گرفت، برای اینکار می توانید از <a href="#cp-sp-delay-start">تاخیر در شروع اولیه</a>  استفاده کنید.  
</div>  

### <div id="cp-sp-callback" dir="rtl" align='right'>پارامترهای session callback</div>

<div dir="rtl" align='right'>  
شما می توانید هر پارامتر callback ای که برای <a href="#cp-ep-callback">event</a> ارسال شده را در هر event یا session ادتریس ذخیره کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
این پارامترهای callback session مشابه event میباشد. برخلاف اضافه کردن key و value به یک event، آنها را از طریق متد <code>AdTrace.addSessionCallbackParameter(String key, String value)</code> استفاده کنید:  
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
پارامترهای callback session با پارامترهای callback به یک event افزوده اید ادغام خواهد شد. پارامترهای event بر session تقدم و برتری دارند، بدین معنی که اگر شما پارامتر callback یک ایونت را با یک key مشابه که به session افزوده شده است، این مقدار نسبت داده شده به این key از event استفاده خواهد کرد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
این امکان فراهم هست که مقدار پارامترهای callback session از طریق key مورد نظربا متد <code>AdTrace.removeSessionCallbackParameter(String key)</code> حذف شود:  
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
اگر شما مایل هستید که تمام مقدایر پارامترهای callback session را پاک کنید، باید از متد <code>()AdTrace.resetSessionCallbackParameters</code> استفاده کنید:  
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

### <div id="cp-sp-partner" dir="rtl" align='right'>پارامترهای session partner</div>

<div dir="rtl" align='right'>  
به همین صورت پارامترهای partner مثل <a href="#cp-sp-callback">پارامترهای callback session</a> در هر event یا session ارسال خواهند شد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
این مقادیر برای تمامی شریکان که در <a href="http://panel.adtrace.io">پنل</a> خود فعالسازی کرده اید ارسال می شود.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
پارامترهای partner session همچون event میباشد. باید از متد <code>AdTrace.addSessionPartnerParameter(String key, String value)</code> استفاده شود:  
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
پارامترهای partner session با پارامترهای partner به یک event افزوده اید ادغام خواهد شد. پارامترهای event بر session تقدم و برتری دارند، بدین معنی که اگر شما پارامتر partner یک ایونت را با یک key مشابه که به session افزوده شده است، این مقدار نسبت داده شده به این key از event استفاده خواهد کرد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
این امکان فراهم هست که مقدار پارامترهای partner session از طریق key مورد نظربا متد <code>AdTrace.removeSessionPartnerParameter(String key)</code> حذف شود:  
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
اگر شما مایل هستید که تمام مقدایر پارامترهای partner session را پاک کنید، باید از متد <code>()AdTrace.resetSessionPartnerParameters</code> استفاده کنید:  
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
شروع با تاخیر SDK ادتریس این امکان را به اپلیکیشن شما میدهد تا پارامترهای session شما در زمان نصب ارسال شوند.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
  با استفاده از متد <code>setDelayStart</code> که ورودی آن عددی به ثانیه است، باعث تاخیر در شروع اولیه خواهد شد:  
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
در این مثال SDK ادتریس مانع از ارسال session نصب اولیه و هر event با تاخیر 5.5 ثانیه خواهد شد. بعد از اتمام این زمان (یا فراخوانی متد <code>()AdTrace.sendFirstPackages</code> در طی این زمان) هر پارامتر sessionی با تاخیر آن زمان افزوده خواهد شد و بعد آن ادتریس به حالت عادی به کار خود ادامه می دهد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
<strong>بیشترین زمان ممکن برای تاخیر در شروع SDK ادتریس 10 ثانیه خواهد بود.</strong>  
</div>  

## <div dir="rtl" align='right'>قابلیت های بیشتر</div>

<div dir="rtl" align='right'>  
هنگامی که شما SDK ادتریس را پیاده سازی کردید، میتوانید از ویژگی های زیر بهره ببرید:  
</div>  

### <div id="af-push-token" dir="rtl" align='right'> Push token (تعداد حذف اپلیکیشن)</div>

<div dir="rtl" align='right'>  
push token برای برقراری ارتباط با کاربران استفاده می شود، همچنین برای track تعداد حذف یا نصب مجدد اپلیکیشن از این token کاربرد دارد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
برای ارسال push token نوتیفیکشین خط زیر را در قسمتی که کد را دریافت کرده اید (یا هنگامی که مقدار آن تغییر می کند) اضافه نمایید:  
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

### <div id="af-attribution-callback" dir="rtl" align='right'>attribution callback</div>

<div dir="rtl" align='right'>  
شما می توانید هنگامی که اتریبیشون tracker تغییر کند،یک listener داشته باشید. ما امکان فراهم سازی این اطلاعات را به صورت همزمان به دلیل تنوع منبع attribution نداریم.  
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
	@Override public void onAttributionChanged(AdTraceAttribution attribution) {
			}});  
  
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
function attributionCallback(attribution) {
	}  
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
این تابع بعد از دریافت آخرین اطلاعات اتریبیوشن call می شود. با این تابع، به پارامتر <code>attribution</code> دسترسی پیدا خواهید کرد. موارد زیر یک خلاصه ای از امکانات گفته شده است:  
</div>  
<div dir="rtl" align='right'>  
<ul>  
<li><code>trackerToken</code> مشمول attribution حال حاضر و از جنس String</li>  
<li><code>trackerName</code>مشمول attribution حال حاضر و از جنس String</li>  
<li><code>network</code> لایه نتورک مشمول attribution حال حاضر و از جنس String</li>  
<li><code>campain</code> لایه کمپین مشمول attribution حال حاضر و از جنس String</li>  
<li><code>adgroup</code> لایه ادگروپ مشمول attribution حال حاضر و از جنس String</li>  
<li><code>creative</code>لایه کریتیو مشمول attribution حال حاضر و از جنس String</li>  
<li><code>adid</code>شناسه ادتریس دستگاه و از جنس String</li>  
<li><code>costType</code>نوع هزینه یا درآمد از جنس String</li>  
<li><code>costAmount</code>مقدار هزینه یا درآمد</li>  
<li><code>costCurrency</code>واحد ارزی هزینه یا درآمد از جنس String</li>  
</ul>

</div>  
<div dir="rtl" align='right'>  
<strong>توجه داشته باشید:</strong> موارد مربوط به هزینه و یا درآمد (<code>costType</code>, <code>costAmount</code> , <code>costCurrency</code>) تنها در صورتی قابل استفاده خواهند بود که در تنظیمات <code>AdTraceConfig</code> با استفاده از متد <code>setNeedsCost</code>مشخص کنید( مقدار <code>true</code>). در صورتی که تنظیم نشده باشد یا به هرصورتی در attribution  نقشی نداشته باشد مقدار <code>null</code>خواهد داشت.  
این قابلیت از SDK v2.0.1 در دسترس قرار گرفته است.  
</div>  


### <div id="af-session-event-callbacks" dir="rtl" align='right'>callback های event و session</div>

<div dir="rtl" align='right'>  
این امکان فراهم است که یک listener هنگامی که event یا track، session می شود، به اطلاع شما برساند. چهار نوع listener وجود دارد: یکی برای track موفق بودن event، یکی برای track ناموفق بودن event، دیگری برای موفق بودن session و آخری نیز برای ناموفق بودن track session. برای درست کردن چنین listener هایی به صورت زیر عمل می کنیم:  
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
	@Override public void onFinishedSessionTrackingFailed(AdTraceSessionFailure sessionFailureResponseData) { 
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
listener ها هنگامی فراخوانده می شوند که SDK تلاش به ارسال داده سمت سرور کند. با این listener شما دسترسی به  داده های دریافتی دارید. موارد زیر یک خلاصه ای از داده های دریافتی هنگام session موفق می باشد:  
</div>  
<div dir="rtl" align='right'>  
<ul>  
<li><code>message</code> پیام از طرف سرور(یا خطا از طرف SDK)</li>  
<li><code>timestamp</code> زمان دریافتی از سرور</li>  
<li><code>adid</code> یک شناسه یکتا که از طریق ادتریس ساخته شده است</li>  
<li><code>jsonResponse</code> شی JSON دریافتی از سمت سرور</li>  
</ul>  
</div>  

<div dir="rtl" align='right'>  
هر دو داده دریافتی event شامل موارد زیر می باشد:  
</div>  

<div dir="rtl" align='right'>  
<ul>  
<li><code>eventToken</code> token مربوط به event مورد نظر</li>  
<li><code>cakkbackId</code> <a href="#cp-ep-id">شناسه callback</a> که برای یک event تنظیم می شود</li>  
</ul>  
</div>  

<div dir="rtl" align='right'>  
و هر دو event و session ناموفق شامل موارد زیر میشوند:  
</div>  

<div dir="rtl" align='right'>  
<ul>  
<li><code>willRetry</code> یک boolean ای  تلاش مجدد برای ارسال داده را نشان میدهد .</li>  
</ul>  
</div>  

### <div id="af-user-attribution" dir="rtl" align='right'>Attribution کاربر</div>

<div dir="rtl" align='right'>  
همانطور که در بخش <a href="#af-attribution-callback">attribution callback</a> توضیح دادیم، این  callback هنگامی که اطلاعات attribution  تغییر کند، فعالسازی می شود. برای دسترسی به اطلاعات attribution فعلی کاربر درهر زمانی که نیاز بود از طریق متد زیر قابل دسترس است:  
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
<strong>نکته</strong>: اطلاعات attribution فعلی تنها درصورتی دردسترس است که از سمت سرور نصب اپلیکیشن track شود و از طریق callback attribution فعالسازی شود. <strong>امکان این نیست که</strong> قبل از اجرا اولیه SDK  و فعالسازی callback attribution بتوان به داده های کاربر دسترسی پیدا کرد.  
</div>  

### <div id="af-di" dir="rtl" align='right'>ID های دستگاه</div>

<div dir="rtl" align='right'>  
SDK ادتریس انواع ID ها (شناسه) را به شما پیشنهاد می کند.  
</div>  

### <div id="af-di-gps-adid" dir="rtl" align='right'>Google Play Services advertising identifier (GPS Ad Id)</div>

<div dir="rtl" align='right'>  
سرویس های مشخص (همچون Google Analytics) برای هماهنگی بین شناسه تبلیغات و شناسه کاربر به جهت ممانعت از گزارش تکراری به به این شناسه نیاز دارد.  
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
اگر می خواهید GpsAdId را بدست آورید، یک محدودیتی وجود دارد که تنها از طریق thread پس زمینه قابل خواندن می باشد. می توانید از طریق تابع <code>getGoogleAdId</code> به همراه context و   از <code>OnDeviceIdsRead</code> به صورت زیر به این شناسه دست پیدا کنید:  
</div>  
<br/>  

```java  
AdTrace.getGoogleAdId(this, new OnDeviceIdsRead() {
	@Override public void onGoogleAdIdRead(String googleAdId) {
	
		}
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
برای دستیابی بهGPSAdId لازم است تا یک تابع callback به متد <code>AdTrace.getGoogleAdId</code> که این شناسه را دریافت می کند به صورت زیر استفاده کنید:  
</div>  

```js  
AdTrace.getGoogleAdId(function(googleAdId) {
	// ...
	});  
```  
</td>  
</tr>  
</table>  

### <div id="af-di-amz-adid" dir="rtl" align='right'>Amazon advertising identifier</div>

<div dir="rtl" align='right'>  
اگر می خواهید به شناسه تبلیغات آمازون دست پیدا کنید، متد درون <code>AdTrace</code> را به صورت زیر فراخوانی کنید:  
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

### <div id="af-di-adid" dir="rtl" align='right'>AdTrace device identifier</div>

<div dir="rtl" align='right'>  
برای هر دستگاهی که نصب می شود، سرور ادتریس یک <strong>شناسه یکتا</strong> (که به صورت <strong>adid</strong> نامیده ومشود) تولید می کند. برای دستیابی به این شناسه می توانید به صورت زیر استفاده کنید:  
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
<strong>نکته</strong>: اطلاعات مربوط به شناسه <strong>شناسه ادتریس</strong> تنها بعد از شناسایی نصب توسط سرور ادتریس قابل درسترس است. دسترسی به شناسه ادتریس قبل این tracking و یا قبل راه اندازی ادتریس <strong>امکان پذیر نیست</strong>.  
</div>  

### <div id="af-pre-installed-trackers" dir="rtl" align='right'>اپلیکیشن های preinstalled</div>

<div dir="rtl" align='right'>  
اگر مایل به این هستید که SDK ادتریس تشخیص این را بدهد که چه کاربرانی از طریق نصب پیشین         وارد اپلیکیشن شده اند مراحل زیر را انجام دهید:  
</div>  
<div dir="rtl" align='right'>  
<ul>  
<li>یک tracker جدید در <a href="http://panel.adtrace.io">پنل</a> خود ایجاد نمایید.</li>  
<li>در تنظیمات SDK ادتریس مثل زیر tracker پیشفرض را اعمال کنید:</li>  
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
<li>مقدار <code>{TrackerToken}</code> را با مقدار token tracker که در مرحله اول دریافت کرده اید جاگزین کنید.</li>  
<li>اپلیکیشن خود را بسازید. در قسمت LogCat خود همچین خطی را مشاهده خواهید کرد.</li>  
</ul>  
</div>  
<br/>  


``` 
 Default tracker: 'adt1ex' 
```  
### <div id="af-offline-mode" dir="rtl" align='right'>حالت آفلاین</div>

<div dir="rtl" align='right'>  
برای مسدودسازی ارتباط SDK با سرورهای ادتریس می توانید از حالت آفلاین SDK استفاده کنید(درحالیکه مجموعه داده ها بعدا برای رصد کردن ارسال می شود). در حالت آفلاین تمامی اطلاعات درون یک فایل ذخیره خواهد شد. توجه کنید که در این حالت eventهای زیادی را ایجاد نکنید.  
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
بر عکس حالت بالا با فراخوانی متد <code>setOfflineMode</code> به همراه متغیر <code>false</code> میتوانید این حالت آفلاین را غیرفعال کنید. هنگامی که SDK ادتریس به حالت آنلاین برگردد، تمامی اطلاعات ذخیر شده با زمان صحیح مربوط به خودش سمت سرور ارسال می شود.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
برخلاف غیرفعال کردن tracking، این تنظیم بین session ها <strong>توصیه نمیشود</strong>. این بدین معنی است که SDK هرزمان که شروع شود در حالت آنلاین است، حتی اگر اپلیکیشن درحالت آفلاین خاتمه پیدا کند.  
</div>  

### <div id="af-disable-tracking" dir="rtl" align='right'>غیرفعال سازی</div>

<div dir="rtl" align='right'>  
شما می توانید SDK ادتریس را ازانجام هرگونه فعالیت برای یک دستگاه غیر فعال کنید که این کار از طریق متد <code>setEnabled</code> با پارامتر <code>false</code> امکان پذیر است. <strong>این تنظیم بین session ها ذخیره خواهد شد.</strong>.  
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

### <div id="af-event-buffering" dir="rtl" align='right'>Event buffering</div>

<div dir="rtl" align='right'>  
اگر اپلیکیشن شما حجم زیادی  از eventها را ارسال می کند، ممکن است بخواهید اطلاعات را با یک حالت تاخیر و در یک مجموعه هر دقیقه ارسال کنید. می توانید از طریق زیر بافرکردن eventها را فعالسازی کنید:  
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

### <div id="af-background-tracking" dir="rtl" align='right'>Background tracking</div>

<div dir="rtl" align='right'>  
هنگامی که اپلیکیشن در حالت پس زمینه قرار دارد حالت پیشفرض SDK ادتریس ، به صورت متوقف شده بوده و فعالیتی ندارد. برای تغییر این مورد می توانید به صورت زیر عمل کنید:  
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

### <div id="af-gdpr-forget-me" dir="rtl" align='right'>GPDR</div>

<div dir="rtl" align='right'>  
بر طبق قانون GPDR شما می توانید ادتریس را از پاک کردن اطلاعات کاربر با خبر سازید.   کاربر حق این را دارد که اطلاعاتش محفوظ بماند و با انجام تنظیمات زیر می توانید این کار را انجام دهید :  
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

<br/>  
<div dir="rtl" align='right'>  
طی دریافت این تنظیمات، ادتریس تمامی داده های کاربر را پاک خواهد کرد و tracking کاربر را متوقف خواهد کرد. هیچ درخواستی از این دستگاه به ادتریس در آینده ارسال نخواهد شد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
درنظر داشته باشید که حتی در زمان تست، این تصمیم بدون تغییر خواهد بود و قابل برگشت <strong>نیست</strong>.  
</div>  



### <div id="af-gdpr-forget-me" dir="rtl" align='right'>[آزمایشی] Data Residency (برای اپلیکیشن های غیر ایرانی)</div>
<div dir="rtl" align='right'>  
به منظور فعال کردن قابلیت انتخاب محل اقامت داده ها از متد <code>setUrlStrategy</code> در <code>AdTraceConfig</code> استفاده کنید.</div>  

<table>    
<tr>    
<td>    
<b>Native App SDK</b>    
</td>    
</tr>    
<tr>    
<td>    

```java 
adtraceConfig.setUrlStrategy(AdTraceConfig.DATA_RESIDENCY_IR); // for Iran data residency region
adtraceConfig.setUrlStrategy(AdTraceConfig.DATA_RESIDENCY_TR); // for Turkey data residency region
adtraceConfig.setUrlStrategy(AdTraceConfig.DATA_RESIDENCY_AE); // for Emarat data residency region 
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
adtraceConfig.setUrlStrategy(AdTraceConfig.DataResidencyIR); // for Iran data residency region
adtraceConfig.setUrlStrategy(AdTraceConfig.DataResidencyTR); // for Turkey data residency region
adtraceConfig.setUrlStrategy(AdTraceConfig.DataResidencyAE); // for Emarat data residency region 
``` 
</td>    
</tr>    
</table>    
<div dir="rtl" align='right'>  
<strong>توجه داشته باشید:</strong> این قابلیت درحال حاضر در مرحله آزمایشی است. برای اپلیکیشن های ایرانی نیازی به اضافه کردن این بخش نیست و به صورت پیشفرض ایران انتخاب می شود. برای اطلاعات بیشتر با ما در ارتباط باشید. توجه داشته باشید <strong>درصورتی که مطمئن نیستید این تنظیمات را  اضافه نکنید </strong> چرا که ممکن است باعث از بین رفتن ترافیک اطلاعات شما  شود. </div>  

## <div dir="rtl" align='right'>تست و عیب یابی</div>

### <div id="ts-reset-gps-ad-id" dir="rtl" align='right'>چگونه می توانم GPS Ad Id را ریست کنم؟</div>

<div dir="rtl" align='right'>  
با استفاده از <a href="../english/reset-google-ad-id.md">این دستور العمل</a> میتوانید شناسه تبلیغات سرویس گوگل را ریست کنید.  
</div>  

### <div id="ts-session-failed" dir="rtl" align='right'>من خطای "Session failed (Ignoring too frequent session. ...)" را مشاهده می کنم.</div>

<div dir="rtl" align='right'>  
این خطا معمولا در زمان تست نصب رخ می دهد. پاک کردن و نصب مجدد اپلیکیشن نمی تواند یک نصب جدید را ایجاد کند. با توجه به داده های طرف سرور، سرورها این تشخیص را می دهند که SDK داده های مربوط به session را از دست داده است و این پیام را نادیده می گیرند.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
این رفتار در زمان تست مشکل خواهد بود ولی نیاز است که در صورت امکان مثل حالت production، در حالت sandbox نیز این رفتار را داشته باشیم.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
 برای ریست کردن دادهای session سمت سرورمی توانید از <a href="#ts-reset-gps-ad-id">ریست کردن GPSAdId</a> استفاده کنید.  
</div>  

### <div id="ts-broadcast-receiver" dir="rtl" align='right'>آیا broadcast receiver من اطلاعات install referrer را دریافت می کند؟</div>

<div dir="rtl" align='right'>  
اگر با <a href="#qs-ir-gps-intent">این راهنمایی</a> دستورالعمل را دنبال کرده باشید، boradcase receiver اطلاعات نصب را به SDK و سرورهای ما ارسال خواهد کرد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
شما از طریق زیر می توانید به صورت دستی این اطلاعات نصب را آزمایش کنید. قسمت <code>com.your.appid</code> را با شناسه اپلیکیشن (app ID) خود جایگزین نمایید و سپس دستور زیر را از طریق <a href="http://developer.android.com/tools/help/adb.html">adb</a> در اندروید استودو اجرا کنید:  
</div>  
<br/>  

```  
adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n com.your.appid/io.adtrace.sdk.AdTraceReferrerReceiver --es "referrer" "adtrace_reftag%3Dadt1ex%26tracking_id%3D123456789%26utm_source%3Dnetwork%26utm_medium%3Dbanner%26utm_campaign%3Dcampaign"  
```  

<br/>  
<div dir="rtl" align='right'>  
اگر شما همزمان از boradcast receiver های مختلفی برای <code>INSTALL_REFERRER</code> استفاده می کنید، طبق <a href="../english/multiple-receivers.md">این آموزش</a>  کلاس <code>io.adtrace.sdk.AdTraceReferrerReceiver</code> را با broadcast receiver خود جایگزین کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
همچنین با پاک کردن قست <code>n com.your.appid/io.adtrace.sdk.AdTraceReferrerReceiver-</code> در قسمت کد دستوری adb امکان این را می دهید تا <code>INSTALL_REFERRER</code> به همه گوشی های داخل دستگاهای تان ارسال شود.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
اگر log level را به  <code>verbose</code> تغییر دهید، امکان مشاهده log را به صورت زیر خواهید داشت:  
</div>  
<br/>  

```  
V/AdTrace: Referrer to parse (adtrace_reftag=adt1ex2&tracking_id=123456789&utm_source=network&utm_medium=banner&utm_campaign=campaign) from reftag  
```  

<br/>  
<div dir="rtl" align='right'>  
و پکیج نصب به صورت زیر خواهد شد:  
</div>  
<br/>  

```  
V/AdTrace: Path:      /sdk_click  
	ClientSdk: android4.6.0 
	Parameters:
		app_token        adt1exadt1ex 
		click_time       yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z 
		created_at       yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z 
		environment      sandbox 
		gps_adid         12345678-0abc-de12-3456-7890abcdef12 
		needs_attribution_data 1 
		referrer         adtrace_reftag=adt1ex2&tracking_id=123456789&utm_source=network&utm_medium=banner&utm_campaign=campaign 
		reftag           adt1ex2 
		source           reftag 
		tracking_enabled 1
```  

<br/>  
<div dir="rtl" align='right'>  
اگر این آزمایش را قبل از اجرای اپلیکیشن انجام دهید، هیچ پکیجی ارسال نخواهد شد. تنها زمانی که اپلیکیشن باز شود ارسال خواهد شد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
<strong>نکته</strong>: این اطلاع را داشته باشید که <code>adb</code> برای تست میباشد و برای آزمایش کامل محتوای referrer (در صورتی که پارامترهای مختلفی با <code>&</code> جدا شده اند)، شما باید این اطلاعات را دستی کدگذاری (encode) کنید. درغیر این صورت <code>adb</code> اطلاعات شما را بعد از اولین <code>&</code> جدا خواهد کرد و اطلاعات غلطی را به سمت broadcast receiver ارسال خواهد کرد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
اگر شما مایل هستید به اینگه چطور اپلیکیشن اطلاعات referrer را به صورت غیرکدگذاری(unencoded) دریافت می کند، طبق مثال زیر یک متد به اسم <code>onFireIntentClick</code> درون فایل <code>MainActivity.java</code> خود ایجاد کنید:  
</div>  
<br/>  

```java  
public void onFireIntentClick(View v) {
	Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
	intent.setPackage("com.adtrace.examples");
	intent.putExtra("referrer", "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test");
	sendBroadcast(intent);
	}  
```  

<br/>  
<div dir="rtl" align='right'>  
دومین مقدار <code>putExtra</code> را با مقدار دلخواه خود پر کنید.  
</div>  

### <div id="ts-event-at-launch" dir="rtl" align='right'>آیا می توانم هنگام راه اندازی اپلیکیشن اقدام به ایجاد یک event کنم؟</div>

<div dir="rtl" align='right'>  
درون متد <code>onCreate</code> داخل کلاس <code>Application</code> نه تنها محل شروع اپلیکیشن است بلکه همچنین محلی برای دریافت event درون اپلیکیشن یا سیستم نیز میباشد.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
SDK ادتریس در این زمان شروع اولیه  را آغاز میکند ولی در حقیقت شروع نمیشود. این زمانی اتفاق میفتد که Activtiy شروع شود.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
به همین دلیل بر طبق چیزی که انتظار دارید پیش نمی آید. این فراخوانی ها زمانی اتفاق میفتد که SDK ادتریس شروع به کار کند تا بتواند eventها را ارسال کند.  
</div>  
<div dir="rtl" align='right'>  
اگر مایل به ارسال event بعد از نصب را دارید از <a href="#af-attribution-callback">callback اتریبیوشن</a> استفاده کنید.  
</div>  
<br/>  
<div dir="rtl" align='right'>  
اگر مایل به ارسال ارسال event بعد از شروع اپلیکیشن را دارید، درون متد <code>onCreate</code> کلاس Activity ای که شروع شده است event را ارسال کنید.  
</div>

### <div dir="rtl" align='right'>موارد خاص</div>
 <div dir="rtl" align='right'><a href="doc/persian/multi-process-app.md">اپلیکیشن های multi-process</a></div>
 <div dir="rtl" align='right'><a href="doc/persian/multiple-receivers.md">استفاده از چندین broadcast receiver</a></div>