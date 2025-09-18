import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.hrudhaykanth116.core"

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

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
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    /************************** Android basic ******************************/

    api(libs.androidx.appcompat)
    api(libs.androidx.constraintlayout)

    /************************** Android basic ******************************/

    /************************** Kotlin ******************************/

    // Kotlin
    api(libs.androidx.core.ktx)

    // Coroutines
    api(libs.kotlinx.coroutines.android)

    // Kotlin immutable collections. (https://github.com/Kotlin/kotlinx.collections.immutable)
    api(libs.kotlinx.collections.immutable)

    /************************** Kotlin ******************************/

    /***************************** Jetpack Compose ****************************/

    api(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    api(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    api(libs.androidx.ui.util)
    api(libs.androidx.foundation)

    // api("androidx.compose.compiler:compiler:2.1.10")


    api(libs.androidx.constraintlayout.compose.v111)

    // Material
    // api("androidx.compose.material:material")
    // https://developer.android.com/jetpack/androidx/releases/compose-material3
    api(libs.androidx.material3)

    // Material design icons
    api(libs.material.icons.core)
    api(libs.androidx.material.icons.extended)

    // Integration with activities
    //noinspection GradleDependency
    api(libs.androidx.activity.compose.v1101)
    // Integration with ViewModels
    api(libs.androidx.lifecycle.viewModelCompose)
    // Integration with lifecycle
    api(libs.androidx.lifecycle.runtime.compose)

    // hilt
    api(libs.androidx.hilt.navigation.compose)

    api(libs.androidx.metrics.performance)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.common)

    /***************************** Jetpack Compose ****************************/

    api(libs.androidx.paging.runtime)
    api(libs.androidx.paging.compose)

    /*********** Firebase ************/
    api(platform(libs.firebase.bom))
    // Firebase authentication
    api(libs.firebase.auth.ktx)
//    api("com.google.firebase:firebase-common-ktx'
    api(libs.firebase.storage.ktx)
    api(libs.firebase.database.ktx)
    api(libs.firebase.vertexai)
    /*********** Firebase ************/

    /*********** Google ***************/

    api(libs.play.services.location)

    /*********** Google ***************/

    // Life cycle
    api(libs.androidx.lifecycle.livedata.ktx)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    // Lifecycles only (without ViewModel or LiveData)
    api(libs.androidx.lifecycle.runtime)
    // Saved state module for ViewModel
    api(libs.androidx.lifecycle.viewmodel.savedstate)
    // Annotation processor
    api(libs.androidx.lifecycle.common.java8)

    // Navigation component
    api(libs.androidx.navigation.fragment)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.navigation.compose)
    // Feature module Support
    // api("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    // Testing Navigation
    androidTestImplementation(libs.androidx.navigation.testing)

    // Room
    api(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.room.ktx)

    //kot-pref for local shared preferences data.
    api(libs.enum.support)
    api(libs.kotpref)
    api(libs.kotpref.initializer)
    api(libs.gson.support)
    api(libs.livedata.support)

    // Network
    api(libs.okhttp)
    api(libs.logging.interceptor)
    api(libs.retrofit)
    api(libs.converter.gson)
    // Moshi
    api(libs.converter.moshi)
    api(libs.moshi)
    api(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // Hilt
    api(libs.hilt.android)
    ksp(libs.hilt.compiler)


    // Material design(https://maven.google.com/web/index.html#com.google.android.material:material)
    api(libs.google.android.material)

    //intuit sdp(scalable dp) and ssp(scalable sp)
    api(libs.sdp.android)
    api(libs.ssp.android)
    api(libs.sdp.compose)

    // ViewPager
    api(libs.androidx.viewpager2)

    // Glide
    api(libs.glide)
    ksp(libs.glide.ksp)

    // Coil
    api(libs.coil)
    api(libs.coil.kt.compose)
    api(libs.coil.gif)

    // Paging
    api(libs.androidx.paging.runtime.ktx)

    //Lottie
    api(libs.lottie)
    api(libs.lottie.compose) // Use latest version

    // Shimmer(http://facebook.github.io/shimmer-android/)
    api(libs.shimmer)

    // testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    // Hamcrest for assert (http://hamcrest.org/JavaHamcrest/distributables)
    testImplementation(libs.hamcrest)

}