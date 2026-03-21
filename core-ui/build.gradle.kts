import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "mafet.core_ui.generated.resources"
    generateResClass = always
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets.configureEach {
        languageSettings.optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/compose/resourceGenerator/kotlin/commonMainResourceAccessors")
            kotlin.srcDir("build/generated/compose/resourceGenerator/kotlin/commonResClass")
        }

        commonMain.dependencies {
            implementation(project(":core-data"))

            // Compose Multiplatform Resources
            implementation(compose.components.resources)

            // Compose Multiplatform UI Tooling for Previews
            implementation(compose.ui)
            implementation(compose.uiTooling)
            implementation(compose.preview)

            // Kotlin
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.kotlinx.collections.immutable)

            // Koin
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            // Core Android
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)

            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Jetpack Compose
            implementation(libs.androidx.material3)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.ui.tooling)
            implementation(libs.androidx.ui.util)
            implementation(compose.foundation)  // JetBrains Compose Foundation for KMP consistency

            // Material Icons
            implementation(libs.material.icons.core)
            implementation(libs.androidx.material.icons.extended)

            // Activity & Lifecycle Compose
            implementation(libs.androidx.activity.compose.v1101)
            implementation(libs.androidx.lifecycle.viewModelCompose)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Navigation Compose
            implementation(libs.androidx.navigation.compose)

            // ConstraintLayout Compose
            implementation(libs.androidx.constraintlayout.compose.v111)

            // Paging Compose
            implementation(libs.androidx.paging.compose)

            // Image Loading - Coil
            implementation(libs.coil)
            implementation(libs.coil.kt.compose)
            implementation(libs.coil.gif)

            // Lottie Animations
            implementation(libs.lottie.compose)

            // Scalable DP/SP
            implementation(libs.sdp.compose)

            // Material Design Components (for legacy views if needed)
            implementation(libs.google.android.material)
        }
    }
}

android {
    namespace = "com.hrudhaykanth116.core.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    // Debug-only dependencies for Android
    debugImplementation(libs.androidx.ui.tooling)
}
