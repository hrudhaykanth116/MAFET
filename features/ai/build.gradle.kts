plugins {
    // id("com.android.library")
    alias(libs.plugins.dynamic.feature)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    // alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.hrudhaykanth116.chatgpt"

    compileSdk = libs.versions.compileSdk.get().toInt()

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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }

    // val compilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    // tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    //     kotlinOptions.freeCompilerArgs += compilerArgs
    // }
}

dependencies {

    // implementation(project(":core"))
    implementation(project(":app"))

    // Hilt
    // api("com.google.dagger:hilt-android:2.55")
    // kapt("com.google.dagger:hilt-compiler:2.55")

    val room_version = "2.6.1"
    api("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    api("androidx.room:room-ktx:$room_version")

}