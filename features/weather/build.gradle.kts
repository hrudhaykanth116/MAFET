import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.hrudhaykanth116.weather"

    compileSdk = libs.versions.compileSdk.get().toInt()

    val geoCodingApi = rootProject.extra["OPEN_WEATHER_GEO_CODING_API_KEY"] as String
    val foreCastApi = rootProject.extra["OPEN_WEATHER_FORECAST_API_KEY"] as String

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        buildConfigField("String", "OPEN_WEATHER_GEO_CODING_API_KEY", geoCodingApi)
        buildConfigField("String", "OPEN_WEATHER_FORECAST_API_KEY", foreCastApi)

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
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    composeOptions {
          kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
    kotlinOptions {
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }


}

dependencies {

    implementation(project(":core"))
    // TODO: May be data dependency is good enough
    implementation(project(":core-network"))
    implementation(project(":core-data"))
    implementation(project(":core-ui"))

    // Compose Multiplatform Resources - needed to use Res from core-ui
    implementation(libs.androidx.compose.bom)
    implementation("org.jetbrains.compose.components:components-resources:1.7.3")

    // Koin - explicitly added for koinViewModel
    implementation(libs.koin.compose)

    // Ktor - for Ktor-based API service
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)

    api(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room.ktx)

}