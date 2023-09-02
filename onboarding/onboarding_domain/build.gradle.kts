@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.gumu.onboarding_domain"
}

dependencies {
    "implementation"(project(Modules.core))

    "implementation"(libs.core.ktx)
}
