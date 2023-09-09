@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.gumu.tracker_data"
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.trackerDomain))

    // Retrofit
    "implementation"(libs.retrofit)
    "implementation"(libs.retrofit.converter.gson)
    "implementation"(libs.okhttp)
    "implementation"(libs.okhttp.logging.interceptor)

    // Room
    "implementation"(libs.room.ktx)
    "implementation"(libs.room.runtime)
    "kapt"(libs.room.compiler)
}
