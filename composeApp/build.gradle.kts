import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.serialization)
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add(
                "-lsqlite3" // Link SQLite C library while building framework
            )
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Feature modules
            api(project(":features:todo"))
            api(project(":features:weather"))
            api(project(":features:tv"))
            api(project(":features:media"))
            api(project(":features:journal"))
            api(project(":features:ai"))

            // Core modules
            api(project(":core-common"))
            api(project(":core-network"))
            api(project(":core-ui"))
            api(project(":core-data"))

            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)

            // Serialization
            implementation(libs.kotlinx.serialization.json)

            // Koin for dependency injection
            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)

            // Lifecycle
            api(libs.jetbrains.lifecycle.viewmodel)
            api(libs.jetbrains.lifecycle.runtime.compose)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)

            // Firebase Remote Config
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.remote.config)

            implementation(project(":features:auth"))
            implementation(project(":features:games"))
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "com.hrudhaykanth116.composeapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
