import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.hrudhaykanth116.ai.resources"
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

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)

            api(libs.jetbrains.lifecycle.viewmodel)
            api(libs.jetbrains.lifecycle.runtime.compose)

            implementation(libs.androidx.room.runtime)

            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.kotlinx.coroutines.core)

            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
        }

        androidMain.dependencies {
            implementation(project(":core"))

            implementation(libs.androidx.compose.bom)
            implementation(libs.androidx.material3)

            implementation(libs.kotlinx.coroutines.android)

            implementation(libs.androidx.room.ktx)

            implementation(libs.androidx.navigation.compose)

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
    }
}

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
    namespace = "com.hrudhaykanth116.ai"
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

    sourceSets.getByName("main") {
        assets.srcDirs("src/androidMain/assets")
    }
}
