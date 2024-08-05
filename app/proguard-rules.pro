# AppCompat
-keep class androidx.appcompat.** { *; }
-dontwarn androidx.appcompat.**

# Material Components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public class * extends com.bumptech.glide.annotation.GlideModule
-dontwarn com.bumptech.glide.**
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

# Firebase Analytics
-keep class com.google.firebase.analytics.** { *; }
-keep class com.google.android.gms.measurement.** { *; }
-keep class com.google.android.gms.common.** { *; }

# Firebase Database
-keep class com.google.firebase.database.** { *; }

# Gson (used by Firebase)
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# JUnit
-keep class org.junit.** { *; }
-dontwarn org.junit.**

# AndroidX Test (JUnit extension)
-keep class androidx.test.ext.junit.** { *; }
-dontwarn androidx.test.ext.junit.**

# Espresso Core
-keep class androidx.test.espresso.** { *; }
-dontwarn androidx.test.espresso.**

# Keep annotations
-keepattributes *Annotation*

# Keep class names for reflection
-keepnames class androidx.appcompat.** { *; }
-keepnames class com.google.android.material.** { *; }
-keepnames class com.bumptech.glide.** { *; }
-keepnames class com.google.firebase.analytics.** { *; }
-keepnames class com.google.firebase.database.** { *; }
-keepnames class org.junit.** { *; }
-keepnames class androidx.test.ext.junit.** { *; }
-keepnames class androidx.test.espresso.** { *; }

# Keep generic types for Firebase Database
-keepattributes Signature

# If you are using Firebase Authentication
-keep class com.google.firebase.auth.** { *; }

# If you are using Firebase Messaging
-keep class com.google.firebase.messaging.** { *; }

# If you are using Firebase Firestore
-keep class com.google.firebase.firestore.** { *; }

# Keep your models (if any) that are serialized/deserialized
#-keep class com.yourpackage.models.** { *; }

# Keep Parcelable classes
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
