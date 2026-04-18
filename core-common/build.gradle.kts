import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
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

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // Kotlin
            api(libs.kotlinx.collections.immutable)
            api(libs.kotlinx.serialization.json)
            api(libs.kotlinx.datetime)
            api(libs.kotlinx.coroutines.core)

            // Koin for DI
            api(libs.koin.core)

            // Kermit Logger
            api(libs.kermit)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.kotlinx.coroutines.android)
        }

        val desktopMain by getting {
            kotlin.srcDir("build/generated/desktopBuildConfig")
            dependencies {
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
    }
}

android {
    namespace = "com.hrudhaykanth116.core.common"
    compileSdk = libs.versions.compileSdk.get().toInt()

    val openWeatherApiKey = rootProject.extra["OPEN_WEATHER_FORECAST_API_KEY"] as String
    val tmdbApiKey = rootProject.extra["TMDB_API_KEY"] as String
    val pexelsApiKey = rootProject.extra["PEXELS_API_KEY"] as String

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        buildConfigField("String", "OPEN_WEATHER_FORECAST_API_KEY", openWeatherApiKey)
        buildConfigField("String", "TMDB_API_KEY", tmdbApiKey)
        buildConfigField("String", "PEXELS_API_KEY", pexelsApiKey)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }
}

val desktopOpenWeatherApiKey = rootProject.extra["OPEN_WEATHER_FORECAST_API_KEY"] as String
val desktopTmdbApiKey = rootProject.extra["TMDB_API_KEY"] as String
val desktopPexelsApiKey = rootProject.extra["PEXELS_API_KEY"] as String

val generateDesktopBuildConfig by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated/desktopBuildConfig")
    val openWeatherKey = desktopOpenWeatherApiKey
    val tmdbKey = desktopTmdbApiKey
    val pexelsKey = desktopPexelsApiKey
    outputs.dir(outputDir)
    doLast {
        val dir = outputDir.get().asFile.resolve("com/hrudhaykanth116/core/common")
        dir.mkdirs()
        dir.resolve("DesktopBuildConfig.kt").writeText(
            """
            |package com.hrudhaykanth116.core.common
            |
            |object DesktopBuildConfig {
            |    const val OPEN_WEATHER_FORECAST_API_KEY = $openWeatherKey
            |    const val TMDB_API_KEY = $tmdbKey
            |    const val PEXELS_API_KEY = $pexelsKey
            |}
            """.trimMargin()
        )
    }
}

tasks.matching { it.name == "compileKotlinDesktop" }.configureEach {
    dependsOn(generateDesktopBuildConfig)
}
