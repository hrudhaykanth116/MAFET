import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.hrudhaykanth116.weather"

    compileSdk = libs.versions.compileSdk.get().toInt()

    val geoCodingApi = rootProject.extra["OPEN_WEATHER_GEO_CODING_API_KEY"] as String
    val foreCastApi = rootProject.extra["OPEN_WEATHER_FORECAST_API_KEY"] as String

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        buildConfigField("String", "OPEN_WEATHER_GEO_CODING_API_KEY", geoCodingApi)
        buildConfigField("String", "OPEN_WEATHER_FORECAST_API_KEY", foreCastApi)

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
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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

    implementation(project(":core"))

    // Hilt
    api(libs.hilt.android)
    ksp(libs.hilt.compiler)


    api(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room.ktx)

}