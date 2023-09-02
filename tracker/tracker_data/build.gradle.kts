apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.trackerDomain))

    "implementation"(libs.core.ktx)

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
