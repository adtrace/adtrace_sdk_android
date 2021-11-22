

## <div dir="rtl" align='right'>SDK ادرتیس برای اپلیکیشن های دارای multi-process</div>


 
 <div dir="rtl" align='right'>اپلیکیشن های اندرویدی می توانند شامل یک یا چند process باشند. شما می توانید سرویس ها و اکتیویتی را روی process غیر از process اصلی اجرا کنید. این کار را می توان با استفاده از تنظیم <code>android:process</code> در process یا اکتیویتی داخل فایل Android manifest انجام دهید. </div>

```xml 
<activity    
	android:name=".YourActivity"    
	android:process=":YourProcessName"> 
</activity> 
```   
```xml 
<service    
	android:name=".YourService"    
	android:process=":YourProcessName"> 
</service> 
```
<br/>
<div dir="rtl" align='right'>هنگامی که این گونه سرویس یا اکتیویتی خود را تعریف می کنید  در واقع آن را ملزم به اجرا شدن روی process غیر از اصلی می نمایید.</div>
</br>

<div dir="rtl" align='right'>به صورت پیش فرض نام process اصلی با package name اپلیکیشن یکسان است. برای مثال اگر package name اپلیکیشن شما <code>com.example.myapp</code> باشد. همین مقدار نام process اصلی خواهد بود. در این صورت <code>YourActivity</code> و  یا <code>YourService</code>  بر روی process با نام <code>com.example.myapp:YourProcessName</code> اجرا خواهد شد.  </div>

 <br/>  
<div dir="rtl" align='right'>در حال حاضر SDK ادتریس از فعالیت روی چند process داخل اپلیکیشن <strong>پشتیبانی نمی کند</strong>. اگر از multiple processes در اپلیکیشن خود استفاده می کنید لازم است تا در  process اصلی خود <code>AdTraceConfig</code> را پیاده سازی کنید. </div>
</br>

```java 
String appToken = "{YourAppToken}"; 
String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;  // or AdTraceConfig.ENVIRONMENT_PRODUCTION    
AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
config.setProcessName("com.example.myapp");    
AdTrace.onCreate(config); 
 ```

 <br/>  
<div dir="rtl" align='right'>همچنین می توانید نام process اصلی را با تغییر<code>android:process</code> در <code>application</code> داخل فایل <code>AndroidManifest.xml</code> تغییر دهید. </div>
</br>

```xml 
<application    
	android:name=".YourApp"    
	android:icon="@drawable/ic_launcher"    
	android:label="@string/app_name"    
	android:theme="@style/AppTheme"    
	android:process=":YourMainProcessName"> 
</application> 
```

```java
config.setProcessName("com.example.myapp:YourMainProcessName"); 
```

<br/>  
<div dir="rtl" align='right'>با این کار SDK ما از نام process اصلی اپلیکیشن آگاه می شود در نتیجه روی processغیر از اصلی که مایل اپلیکیشن بخواهد استفاده کند اجرا نمی شود. اگر بخواهید که SDK را از هر process دیگری اجرا کنید پیام زیر را در log دریافت خواهید کرد.</div>
</br>

```
05-06 17:15:06.885
8743-8743/com.example.myapp:YourProcessName 
I/AdTrace﹕ Skipping initialization in background process (com.example.myapp:YourProcessName)
```
<br/>  
<div dir="rtl" align='right'>درصورتی که  این کار به درستی انجام نشود <code>AdTraceConfig</code>  سعی می کند که بر روی processهای مختلف اجرا شود و از آنجایی که هر process در اندروید سهم خود را از memory دارد و باهم متفاوت است ممکن است برخی رخدادهای غیرقابل پیش بینی به وجود بیاید لذا <strong>به شدت توصیه می شود</strong> که اگر از اپلیکیشن شما multi-process است اقدامات ذکر شده را به درستی پیاده سازی کنید و یا از اینکه SDK ادتریس تنها بر روی یک process اجرا شود اطمینان حاصل نمایید.</div>
</br>
