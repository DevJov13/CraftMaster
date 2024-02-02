
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes Signature
-keepattributes SetJavaScriptEnabled
-keepattributes JavascriptInterface


#region [ JavaScript Interface Classes ]
-keepclassmembers class dev.joven.mygame.WebViewModel {
   public *;
}

-keepclassmembers class dev.joven.mygame.AdjustSDK {
   public *;
}
-keep class com.adjust.sdk.** { *; }
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

#region [ Google Services ]
#Notes: Required for Adjust
-keep public class com.android.installreferrer.** { *; }
#endregion
-dontwarn java.awt.Color*
-dontwarn java.awt.Font*
-dontwarn java.awt.Point*