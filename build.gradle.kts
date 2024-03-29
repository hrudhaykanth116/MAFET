// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks{
    register("clean", Delete::class.java){
        delete(rootProject.buildDir)
    }
}

subprojects {

    val compilerArgs = listOf(
        "-Xopt-in=kotlin.RequiresOptIn",
        "-P=plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + project.buildDir.absolutePath + "/compose_metrics",
        "-P=plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + project.buildDir.absolutePath + "/compose_metrics"
    )

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        if (project.findProperty("myapp.enableComposeCompilerReports") == "true") {
            kotlinOptions.freeCompilerArgs += compilerArgs
        }
    }
}