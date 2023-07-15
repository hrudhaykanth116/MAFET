plugins {
    kotlin("android")
    id("com.android.library")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.hrudhaykanth116.todo"

    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
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
    api("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-compiler:2.45")

    val room_version = "2.5.1"
    api("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    api("androidx.room:room-ktx:$room_version")

}