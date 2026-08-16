# ControlX ProGuard & R8 Configuration for Production

# Firebase Auth & Realtime Database Keep Rules
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep Data Models
-keep class com.example.controlx.Users { *; }
-keepclassmembers class com.example.controlx.Users {
    <fields>;
    <methods>;
}

# Keep all Activities and Services in ControlX
-keep public class com.example.controlx.** extends android.app.Activity
-keep public class com.example.controlx.** extends android.app.Service
-keep public class com.example.controlx.** extends android.content.BroadcastReceiver
-keep public class com.example.controlx.** extends android.content.ContentProvider
-keep public class com.example.controlx.** extends android.app.Application

# Firebase Realtime Database POJOs and Reflection
-keepclassmembers class * {
    @com.google.firebase.database.PropertyName <fields>;
    @com.google.firebase.database.PropertyName <methods>;
    @com.google.firebase.database.IgnoreExtraProperties <fields>;
    @com.google.firebase.database.IgnoreExtraProperties <methods>;
    @com.google.firebase.database.Exclude <fields>;
    @com.google.firebase.database.Exclude <methods>;
}

# Material Design & AndroidX
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**
-dontwarn androidx.**

# Strip debug logging in release builds (optional optimization)
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
}
