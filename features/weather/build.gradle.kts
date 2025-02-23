plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.hrudhaykanth116.weather"

    compileSdk = 35

    defaultConfig {
        minSdk = 24

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
    kotlinOptions {
        jvmTarget = "17"
    }
    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }

    val compilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.freeCompilerArgs += compilerArgs
    }

}

dependencies {

    implementation(project(":core"))

    // Hilt
    api(libs.hilt.android)
    kapt(libs.hilt.compiler)

    api(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    api(libs.androidx.room.ktx)

}