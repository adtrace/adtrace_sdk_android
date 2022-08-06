---
name: Main title for the issue
about: tell me about the problem in one simple sentence
title: ''
labels: ''
assignees: namini40

---

*tell me what happened?*

### a simple description of the problem occurred .

> I can't send `event` with value. after calling `AdTrace.trackEvent()` an exception throws and etc.
> I also tried this and that
-------------------------

### error message you see (related to adtrace sdk)
> this is the error i'm getting:
```java
java.io.IOException: Attempted read from closed stream.
com.android.music.sync.common.SoftSyncException: java.io.IOException: Attempted read from closed stream.
    at com.android.music.sync.google.MusicSyncAdapter.getChangesFromServerAsDom(MusicSyncAdapter.java:545)
    at com.android.music.sync.google.MusicSyncAdapter.fetchDataFromServer(MusicSyncAdapter.java:488)
    at com.android.music.sync.common.AbstractSyncAdapter.download(AbstractSyncAdapter.java:417)
    at com.android.music.sync.common.AbstractSyncAdapter.innerPerformSync(AbstractSyncAdapter.java:313)
    at com.android.music.sync.common.AbstractSyncAdapter.onPerformLoggedSync(AbstractSyncAdapter.java:243)
    at com.google.android.common.LoggingThreadedSyncAdapter.onPerformSync(LoggingThreadedSyncAdapter.java:33)
    at android.content.AbstractThreadedSyncAdapter$SyncThread.run(AbstractThreadedSyncAdapter.java:164)
Caused by: java.io.IOException: Attempted read from closed stream.
    at org.apache.http.impl.io.ChunkedInputStream.read(ChunkedInputStream.java:148)
    at org.apache.http.conn.EofSensorInputStream.read(EofSensorInputStream.java:159)
    at java.util.zip.GZIPInputStream.readFully(GZIPInputStream.java:212)
    at java.util.zip.GZIPInputStream.<init>(GZIPInputStream.java:81)
    at java.util.zip.GZIPInputStream.<init>(GZIPInputStream.java:64)
    at android.net.http.AndroidHttpClient.getUngzippedContent(AndroidHttpClient.java:218)
    at com.android.music.sync.api.MusicApiClientImpl.createAndExecuteMethod(MusicApiClientImpl.java:312)
    at com.android.music.sync.api.MusicApiClientImpl.getItems(MusicApiClientImpl.java:588)
    at com.android.music.sync.api.MusicApiClientImpl.getTracks(MusicApiClientImpl.java:638)
    at com.android.music.sync.google.MusicSyncAdapter.getChangesFromServerAsDom(MusicSyncAdapter.java:512)
    ... 6 more
```
-------------------

###  SDK version that you are using.
> Android SDK: v 2.0.3 or Flutter SDK: v 0.1.3 
-------------------

### Configuration of your tools
* IDE examples:
> android studio 2021.1.2 
> XCode 13.1
> ...
* Mobile OS example:
> Android API level 31
> iOS 15.4
* Device Model example:
> Samsung Galaxy S9 (android 9) or (api level 28)
> iPhone 13 mini (iOS 15.4)
> emulator: pixel 4 (api level 32)
> simulator: iPhone 13 ( iOS 15
