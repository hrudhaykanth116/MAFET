plugins {
    id("com.android.library")
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.hrudhaykanth116.core"

    compileSdk = 35

    defaultConfig {
        minSdk = 24
        targetSdk = 35

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
        buildConfig = true
    }

    val compilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.freeCompilerArgs += compilerArgs
    }

}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    /************************** Android basic ******************************/

    api("androidx.appcompat:appcompat:1.7.0")
    api("androidx.constraintlayout:constraintlayout:2.2.0")

    /************************** Android basic ******************************/

    /************************** Kotlin ******************************/

    // Kotlin
    api("androidx.core:core-ktx:1.15.0")

    // Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    // Kotlin immutable collections. (https://github.com/Kotlin/kotlinx.collections.immutable)
    api("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.8")

    /************************** Kotlin ******************************/

    /***************************** Jetpack Compose ****************************/

    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    api(platform("androidx.compose:compose-bom:2025.02.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.02.00"))

    api("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    api("androidx.compose.ui:ui-util")
    api("androidx.compose.foundation:foundation")

    // api("androidx.compose.compiler:compiler:2.1.10")


    api("androidx.constraintlayout:constraintlayout-compose:1.1.0")

    // Material
    // api("androidx.compose.material:material")
    // https://developer.android.com/jetpack/androidx/releases/compose-material3
    api("androidx.compose.material3:material3")

    // Material design icons
    api("androidx.compose.material:material-icons-core")
    api("androidx.compose.material:material-icons-extended")

    // Integration with activities
    api("androidx.activity:activity-compose:1.10.0")
    // Integration with ViewModels
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    // Integration with lifecycle
    api("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    // hilt
    api("androidx.hilt:hilt-navigation-compose:1.2.0")

    api("androidx.metrics:metrics-performance:1.0.0-beta01")

    /***************************** Jetpack Compose ****************************/

    /*********** Firebase ************/
    api(platform("com.google.firebase:firebase-bom:33.9.0"))
    // Firebase authentication
    api("com.google.firebase:firebase-auth-ktx")
//    api("com.google.firebase:firebase-common-ktx'
    api("com.google.firebase:firebase-storage-ktx")
    api("com.google.firebase:firebase-database-ktx")
    /*********** Firebase ************/

    // Life cycle
    val lifecycle_version = "2.8.7"
    api("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    api("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    // Saved state module for ViewModel
    api("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
    // Annotation processor
    api("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")

    // Navigation component
    val nav_version = "2.8.7"
    api("androidx.navigation:navigation-fragment-ktx:$nav_version")
    api("androidx.navigation:navigation-ui-ktx:$nav_version")
    api("androidx.navigation:navigation-compose:$nav_version")
    // Feature module Support
    // api("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Room
    val room_version = "2.6.1"
    api("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    api("androidx.room:room-ktx:$room_version")

    //kot-pref for local shared preferences data.
    val kotpref_version = "2.13.2"
    api("com.chibatching.kotpref:enum-support:$kotpref_version")
    api("com.chibatching.kotpref:kotpref:$kotpref_version")
    api("com.chibatching.kotpref:initializer:$kotpref_version")
    api("com.chibatching.kotpref:gson-support:$kotpref_version")
    api("com.chibatching.kotpref:livedata-support:$kotpref_version")

    // Network
    api("com.squareup.retrofit2:retrofit:2.11.0")
    api("com.squareup.retrofit2:converter-gson:2.11.0")
    // Moshi
    api("com.squareup.retrofit2:converter-moshi:2.11.0")
    api("com.squareup.moshi:moshi:1.15.2")
    api("com.squareup.moshi:moshi-kotlin:1.15.2")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.2")

    // Hilt
    api("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-compiler:2.55")

    // Material design(https://maven.google.com/web/index.html#com.google.android.material:material)
    api("com.google.android.material:material:1.12.0")

    //intuit sdp(scalable dp) and ssp(scalable sp)
    val intuit_version = "1.1.1"
    api("com.intuit.sdp:sdp-android:$intuit_version")
    api("com.intuit.ssp:ssp-android:$intuit_version")

    // ViewPager
    api("androidx.viewpager2:viewpager2:1.1.0")

    // Glide
    val glide_version = "4.15.0"
    api("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // Coil
    api("io.coil-kt:coil:2.7.0")
    api("io.coil-kt:coil-compose:2.7.0")

    // Paging
    val paging_version = "3.0.0"
    api("androidx.paging:paging-runtime-ktx:3.3.6")

    //Lottie
    api("com.airbnb.android:lottie:6.6.2")

    // Shimmer(http://facebook.github.io/shimmer-android/)
    api("com.facebook.shimmer:shimmer:0.5.0")

    // testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Hamcrest for assert (http://hamcrest.org/JavaHamcrest/distributables)
    val hamcrest_version = "3.0"
    testImplementation("org.hamcrest:hamcrest:$hamcrest_version")

}