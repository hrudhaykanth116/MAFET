# ── Stack traces ─────────────────────────────────────────────────────────────
# Keep file names + line numbers so Crashlytics shows meaningful stack traces.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ── Kotlin ────────────────────────────────────────────────────────────────────
-keep class kotlin.Metadata { *; }
-keepclassmembers class **$Companion { *; }

# ── Kotlin Serialization ──────────────────────────────────────────────────────
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    *** Companion;
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class **$$serializer { *; }

# ── Moshi ─────────────────────────────────────────────────────────────────────
-keep @com.squareup.moshi.JsonClass class * { *; }
-keepclassmembers class * {
    @com.squareup.moshi.FromJson <methods>;
    @com.squareup.moshi.ToJson <methods>;
}

# ── Gson ──────────────────────────────────────────────────────────────────────
-keepattributes Signature
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# ── Room ──────────────────────────────────────────────────────────────────────
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.TypeConverter class * { *; }
-keepclassmembers class * extends androidx.room.RoomDatabase {
    abstract *;
}

# ── Koin ──────────────────────────────────────────────────────────────────────
# Koin uses class names for lookups — prevent renaming of injected classes.
-keepnames class com.hrudhaykanth116.** { *; }
-keepnames class org.koin.** { *; }

# ── Ktor ──────────────────────────────────────────────────────────────────────
-dontwarn io.ktor.**
-keep class io.ktor.** { *; }

# ── OkHttp ────────────────────────────────────────────────────────────────────
-dontwarn okhttp3.**
-dontwarn okio.**

# ── Coroutines ────────────────────────────────────────────────────────────────
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# ── Enums ─────────────────────────────────────────────────────────────────────
# Prevent enum entries from being removed (used in when expressions, Remote Config parsing).
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ── Parcelable ────────────────────────────────────────────────────────────────
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

# ── Firebase / Crashlytics ────────────────────────────────────────────────────
# Firebase bundles its own rules; these cover any gaps.
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
