

## <div dir="rtl" align='right'>پشتیبانی از چندین broadcast receiver</div>


 <br/>  
<div dir="rtl" align='right'></div>
</br>
  <br/>  
<div dir="rtl" align='right'>درصورتی که چندین منبع نیاز به ثبت  broadcast receiver برای دسترسی به <code>INSTALL_REFERRER</code> intent داشته باشند لازم است تا <code>BroadcastReceiver</code> خود را پیاده سازی کنید. اگر در broadcast receiver خود را در manifest به صورت زیر اضافه کرده اید: </div>
</br>

```xml 
<receiver    
     android:name="com.your.app.InstallReceiver"    
     android:permission="android.permission.INSTALL_PACKAGES"    
     android:exported="true" >    
    <intent-filter>   
       <action android:name="com.android.vending.INSTALL_REFERRER" />    
    </intent-filter>  
 </receiver> 
 ```
  <br/>  
<div dir="rtl" align='right'>از مطلع کردن SDK ادتریس به صورت زیر اطمینان حاصل نمایید.</div>
</br>

```java 
public class InstallReceiver extends BroadcastReceiver {    
   @Override   
   public void onReceive(Context context, Intent intent) {  
   // AdTrace receiver.    
   new AdTraceReferrerReceiver().onReceive(context, intent);   
   // Google Analytics receiver.   
   new CampaignTrackingReceiver().onReceive(context, intent); // And any other receiver which needs the intent.   
    }  
} 
```