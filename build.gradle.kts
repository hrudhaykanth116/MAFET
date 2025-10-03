import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.ksp) apply false apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.safeArgs) apply false
    alias(libs.plugins.dynamic.feature) apply false
}

// apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")

val secretsPropsFile = rootProject.file("secrets.properties")
val secrets = Properties().apply {
    if (secretsPropsFile.exists()) {
        load(secretsPropsFile.inputStream())
    }
}

extra["PEXELS_API_KEY"] = secrets["PEXELS_API_KEY"] ?: ""
extra["TMDB_API_KEY"] = secrets["TMDB_API_KEY"] ?: ""
extra["OPEN_WEATHER_GEO_CODING_API_KEY"] = secrets["OPEN_WEATHER_GEO_CODING_API_KEY"] ?: ""
extra["OPEN_WEATHER_FORECAST_API_KEY"] = secrets["OPEN_WEATHER_FORECAST_API_KEY"] ?: ""

// Run all unit tests before building the release APK/AAB
// subprojects {
//     plugins.withId("com.android.application") {
//         tasks.named("build") {
//             dependsOn(tasks.matching { it.name.endsWith("UnitTest") })
//         }
//     }
//     plugins.withId("com.android.library") {
//         tasks.named("build") {
//             dependsOn(tasks.matching { it.name.endsWith("UnitTest") })
//         }
//     }
// }