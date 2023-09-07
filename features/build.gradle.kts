plugins {
    kotlin("android")
    id("com.android.library")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("kotlin-android")
}

android {
    namespace = "com.hrudhaykanth116.features"

    compileSdk = 34

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


}