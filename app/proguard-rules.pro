# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Dhairya_Madhu\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.* { }
-dontwarn okio.
-dontwarn android.net.http.AndroidHttpClient.**
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-dontwarn org.apache.http.**
-dontwarn rx.**
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}

-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection
-keepattributes *Annotation*
-dontwarn com.hwangjr.rxbus.**
-keep class com.hwangjr.rxbus.** { *;}