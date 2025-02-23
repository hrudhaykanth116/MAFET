plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.safeArgs) apply false
    alias(libs.plugins.dynamic.feature) apply false
}

// apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")