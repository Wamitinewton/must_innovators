-keep class com.newton.core.** { *; }
-keepnames class com.newton.core.** { *; }
-keepclassmembers class com.newton.core.** { *; }
-keepclasseswithmembers class com.newton.core.** { *; }

-keep class com.newton.database.** { *; }
-keepnames class com.newton.database.** { *; }
-keepclassmembers class com.newton.database.** { *; }
-keepclasseswithmembers class com.newton.database.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keep,allowobfuscation interface retrofit2.Call
-keep,allowobfuscation interface retrofit2.http.**
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class com.squareup.okhttp.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepattributes Signature
-keepattributes *Annotation*

