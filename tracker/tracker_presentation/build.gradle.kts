@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.gumu.tracker_presentation"
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.trackerDomain))

    "implementation"(libs.coil)
}
