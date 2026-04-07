import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// ── Version ──────────────────────────────────────────────────────────────────
// Only change versionName. versionCode is derived automatically.
// Formula: major * 10_000_000 + minor * 100_000 + patch * 100
//   → max major ≈ 210  |  minor 0–99  |  patch 0–999
//   → 0.0.1 = 100  |  1.0.0 = 10_000_000  |  99.99.999 = 999_999_900
val major = 0
val minor = 0
val patch = 1
val appVersionName = "$major.$minor.$patch"          // to add suffix: "$major.$minor.$patch-beta"
val appVersionCode = major * 10_000_000 + minor * 100_000 + patch * 100

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
}

android {
    namespace = "com.hrudhaykanth116.mafet"

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.hrudhaykanth116.mafet"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = appVersionCode
        versionName = appVersionName

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] =
                    "$projectDir/schemas"
            }
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    composeOptions {
          kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api(project(":core-common"))
    api(project(":core-data"))
    implementation(project(":core-network"))
    implementation(project(":core-ui"))
    implementation(project(":composeApp"))

    // Compose Multiplatform Resources - needed to use Res from core-ui
    implementation(libs.androidx.compose.bom)
    implementation("org.jetbrains.compose.components:components-resources:1.7.3")
    implementation(project(":features:todo"))
    implementation(project(":features:weather"))
    implementation(project(":features:tv"))
    implementation(project(":features:auth"))
    implementation(project(":features:journal"))
    implementation(project(":features:media"))
    implementation(project(":features:games"))
    implementation(project(":training"))

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)

    implementation(libs.compose.navigationevent)

    implementation(libs.play.services.ads.api)
    implementation(libs.play.app.update.ktx)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.ktx)

    // Gson for Kotpref
    implementation("com.google.code.gson:gson:2.10.1")

    implementation(libs.androidx.core.splashscreen)

    // WorkManager for background sync
    implementation(libs.androidx.work.runtime)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}