plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.hrudhaykanth116.mafet"

    compileSdk = 35


    // dynamicFeatures.addAll(
    //     listOf(
    //         // ":features:todo"
    //     )
    // )

    dynamicFeatures += setOf(":features:ai")

    defaultConfig {
        applicationId = "com.hrudhaykanth116.mafet"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    }

    val compilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.freeCompilerArgs += compilerArgs
    }

}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    api(project(":core"))
    implementation(project(":features:todo"))
    implementation(project(":features:weather"))
    implementation(project(":features:tv"))
    implementation(project(":features:auth"))
    implementation(project(":training"))

    // Hilt
    api("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-compiler:2.55")

    implementation("androidx.core:core-splashscreen:1.0.1")


//    /************************** Android basic ******************************/
//
//    implementation 'androidx.appcompat:appcompat:1.6.1'
//    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
//    implementation 'com.google.android.material:material:1.8.0'
//
//    /************************** Android basic ******************************/
//
//    /************************** Kotlin ******************************/
//
//    // Kotlin
//    implementation 'androidx.core:core-ktx:1.10.0'
//
//    // Coroutines
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
//
//    /************************** Kotlin ******************************/
//
//    /***************************** Jetpack Compose ****************************/
//
//    def composeBom = platform('androidx.compose:compose-bom:2023.03.00')
//    implementation composeBom
//    androidTestImplementation composeBom
//
//    implementation 'androidx.compose.ui:ui-tooling-preview'
//    debugImplementation 'androidx.compose.ui:ui-tooling'
//    implementation 'androidx.compose.foundation:foundation'
//
//    // Material
//    implementation 'androidx.compose.material:material'
//    // Material design icons
//    implementation 'androidx.compose.material:material-icons-core'
//    implementation 'androidx.compose.material:material-icons-extended'
//
//    // Integration with activities
//    implementation 'androidx.activity:activity-compose:1.7.0'
//    // Integration with ViewModels
//    implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1'
//    // Integration with lifecycle
//    implementation "androidx.lifecycle:lifecycle-runtime-compose:2.6.1"
//
//    // hilt
//    implementation "androidx.hilt:hilt-navigation-compose:1.0.0"
//
//
//    /*
//    implementation 'androidx.compose.ui:ui:1.1.1'
//    // Tooling support (Previews, etc.)
//    implementation 'androidx.compose.ui:ui-tooling:1.1.1'
//    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
//    implementation 'androidx.compose.foundation:foundation:1.1.1'
//    // Material Design
//    implementation 'androidx.compose.material:material:1.1.1'
//    // Material design icons
//    implementation 'androidx.compose.material:material-icons-core:1.1.1'
//    implementation 'androidx.compose.material:material-icons-extended:1.1.1'
//    // Integration with activities
//    implementation 'androidx.activity:activity-compose:1.5.0'
//    // Integration with ViewModels
//    implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-alpha01'
//    // Integration with lifecycle
//    implementation "androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha01"
//    // Integration with observables
//    implementation 'androidx.compose.runtime:runtime-livedata:1.1.1'
//    implementation 'androidx.compose.runtime:runtime-rxjava2:1.1.1'
//
//    // Hilt
//    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
//
//    // UI Tests
//    androidTestImplementation 'androidx.compose.ui:ui-test-junit4:1.1.1'
//
//    // When using a MDC theme
//    // implementation "com.google.android.material:compose-theme-adapter:1.1.14"
//
//    // When using a AppCompat theme
//    // implementation "com.google.accompanist:accompanist-appcompat-theme:0.16.0"
//
//     */
//
//    /***************************** Jetpack Compose ****************************/
//
//    /*********** Firebase ************/
//    implementation platform('com.google.firebase:firebase-bom:31.4.0')
//    // Firebase authentication
//    implementation 'com.google.firebase:firebase-auth-ktx'
////    implementation 'com.google.firebase:firebase-common-ktx'
//    implementation 'com.google.firebase:firebase-storage-ktx'
//    implementation 'com.google.firebase:firebase-database-ktx'
//    /*********** Firebase ************/
//
//    // Life cycle
//    def lifecycle_version = "2.5.0"
//    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
//    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
//    // Lifecycles only (without ViewModel or LiveData)
//    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
//    // Saved state module for ViewModel
//    implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
//    // Annotation processor
//    implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
//
//    // Navigation component
//    def nav_version = "2.5.3"
//    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
//    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
//    implementation("androidx.navigation:navigation-compose:$nav_version")
//    // Feature module Support
//    implementation "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"
//    // Testing Navigation
//    androidTestImplementation "androidx.navigation:navigation-testing:$nav_version"
//
//    // Room
//    def room_version = "2.5.1"
//    implementation "androidx.room:room-runtime:$room_version"
//    kapt "androidx.room:room-compiler:$room_version"
//    // optional - Kotlin Extensions and Coroutines support for Room
//    implementation "androidx.room:room-ktx:$room_version"
//
//    //kot-pref for local shared preferences data.
//    def kotpref_version = "2.13.1"
//    implementation "com.chibatching.kotpref:enum-support:$kotpref_version"
//    implementation "com.chibatching.kotpref:kotpref:$kotpref_version"
//    implementation "com.chibatching.kotpref:initializer:$kotpref_version"
//    implementation "com.chibatching.kotpref:gson-support:$kotpref_version"
//    implementation "com.chibatching.kotpref:livedata-support:$kotpref_version"
//
//    // Network
//    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
//    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
//    // Moshi
//    implementation "com.squareup.retrofit2:converter-moshi:2.9.0"
//    implementation "com.squareup.moshi:moshi:1.13.0"
//    kapt "com.squareup.moshi:moshi-kotlin-codegen:1.13.0"
//
//    // Hilt
//    implementation "com.google.dagger:hilt-android:2.45"
//    kapt "com.google.dagger:hilt-compiler:2.44"
//
//    // Material design(https://maven.google.com/web/index.html#com.google.android.material:material)
//    implementation 'com.google.android.material:material:1.8.0'
//
//    //intuit sdp(scalable dp) and ssp(scalable sp)
//    def intuit_version = '1.0.6'
//    implementation "com.intuit.sdp:sdp-android:$intuit_version"
//    implementation "com.intuit.ssp:ssp-android:$intuit_version"
//
//    // ViewPager
//    implementation "androidx.viewpager2:viewpager2:1.0.0"
//
//    // Glide
//    def glide_version = '4.12.0'
//    implementation "com.github.bumptech.glide:glide:4.12.0"
//    kapt "com.github.bumptech.glide:compiler:4.12.0"
//
//    // Paging
//    def paging_version = "3.0.0"
//    implementation "androidx.paging:paging-runtime-ktx:3.1.1"
//
//    //Lottie
//    implementation "com.airbnb.android:lottie:3.4.4"
//
//    // Shimmer(http://facebook.github.io/shimmer-android/)
//    implementation 'com.facebook.shimmer:shimmer:0.5.0'
//
//    // testing
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
//
//    // Hamcrest for assert (http://hamcrest.org/JavaHamcrest/distributables)
//    def hamcrest_version = "2.2"
//    testImplementation "org.hamcrest:hamcrest:$hamcrest_version"

}