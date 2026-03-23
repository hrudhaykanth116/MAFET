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

    jvm("desktop") {
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
            implementation(project(":core-common"))
            implementation(project(":core-data"))

            api(compose.components.resources)
            api(compose.ui)
            api(compose.foundation)
            api(compose.material3)
            api(compose.materialIconsExtended)
            api(compose.preview)

            api(libs.androidx.lifecycle.viewModelCompose)

            api(libs.compose.navigationevent)

            // Koin
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Coil 3.x - KMP Image Loading
            api(libs.coil.compose)
            api(libs.coil.network.ktor)
        }

        androidMain.dependencies {
            // Core Android
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)

            // Koin Android
            implementation(libs.koin.android)

            // Coil GIF - Android only (AAR format)
            api(libs.coil.gif)
        }

        val desktopMain by getting {
            dependencies {
                // Desktop Compose
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
            }
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
