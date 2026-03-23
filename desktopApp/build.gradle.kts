import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose)
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                // Core modules
                implementation(project(":core-common"))
                implementation(project(":core-ui"))
                implementation(project(":core-data"))
                implementation(project(":core-network"))

                // Feature modules
                implementation(project(":features:todo"))

                // Compose Desktop
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)

                // Koin
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.hrudhaykanth116.mafet.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MAFET Desktop"
            packageVersion = "1.0.0"

            macOS {
                iconFile.set(project.file("icon.icns"))
            }
            windows {
                iconFile.set(project.file("icon.ico"))
            }
            linux {
                iconFile.set(project.file("icon.png"))
            }
        }
    }
}
