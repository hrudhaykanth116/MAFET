import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.serialization)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.hrudhaykanth116.todo.resources"
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

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/compose/resourceGenerator/kotlin/commonMainResourceAccessors")
            kotlin.srcDir("build/generated/compose/resourceGenerator/kotlin/commonResClass")
        }

        commonMain.dependencies {
            implementation(project(":core-common"))
            implementation(project(":core-ui"))
            implementation(project(":core-data"))

            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)

            api(libs.jetbrains.lifecycle.viewmodel)
            api(libs.jetbrains.lifecycle.runtime.compose)

            // Room KMP - runtime only in common
            implementation(libs.androidx.room.runtime)

            // Kotlin
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.kotlinx.coroutines.core)

            // Koin
            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
        }

        androidMain.dependencies {
            implementation(project(":core"))

            // Android Compose
            implementation(libs.androidx.compose.bom)
            implementation(libs.androidx.material3)

            // Android Coroutines - Android-only
            implementation(libs.kotlinx.coroutines.android)

            // Room KTX is Android-only
            implementation(libs.androidx.room.ktx)

            // Navigation - Android-only
            implementation(libs.androidx.navigation.compose)

            // Koin Android
            implementation(libs.koin.android)
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.androidx.sqlite.bundled)
            }
        }

        iosMain.dependencies {
            implementation(libs.androidx.sqlite.bundled)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }

        val desktopTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.mockk)
            }
        }
    }
}

// Configure KSP for Room
dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspDesktop", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.generateKotlin", "true")
}

android {
    namespace = "com.hrudhaykanth116.todo"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    // Room schema export for Android
    sourceSets.getByName("main") {
        assets.srcDirs("src/androidMain/assets")
    }
}