# Global rules for all modules
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes *Annotation*

# Preserve the line number information for debugging stack traces
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to hide the original source file name
#-renamesourcefileattribute SourceFile

# Common Android libraries
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Keep the entry points to your app
-keep class com.newton.meruinnovators.activity.MainActivity { *; }
-keep class com.newton.meruinnovators.appication.MeruInnovatorsApp { *; }

# Keep any classes referenced from AndroidManifest.xml
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends androidx.fragment.app.Fragment

# Keep Parcelable implementations
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# Keep Serializable implementations
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Retain generic signatures of TypeToken and its subclasses for reflection
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken