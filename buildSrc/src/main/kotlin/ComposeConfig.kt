// import org.gradle.api.Project
// import org.gradle.api.provider.Provider
// import org.gradle.kotlin.dsl.the
// import com.android.build.api.dsl.CommonExtension
//
// object ComposeConfig {
//
//     /** Flag to enable metrics globally */
//     val ENABLE_COMPOSE_METRICS: Boolean
//         get() = System.getProperty("enableComposeMetrics") == "true" ||
//                 System.getenv("ENABLE_COMPOSE_METRICS") == "true"
//
//     /** Call this from each module’s build.gradle.kts */
//     fun Project.configureComposeCompilerMetrics() {
//         if (ENABLE_COMPOSE_METRICS) {
//             extensions.findByName("android")?.let { ext ->
//                 @Suppress("UNCHECKED_CAST")
//                 val androidExt = ext as CommonExtension<*, *, *, *, *>
//                 androidExt.composeCompiler {
//                     metricsDestination = layout.buildDirectory.dir("compose_compiler/metrics")
//                     reportsDestination = layout.buildDirectory.dir("compose_compiler/reports")
//                 }
//             }
//         }
//     }
// }
