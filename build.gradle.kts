// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.library) apply false
}

gradle.rootProject.extra["BASE_URL_DEBUG"] = "https://newsapi.org/v2"

// For this take-home assignment, the API key is kept in Gradle for easier setup and review.
// In a production project, it would be provided via local.properties or CI/CD secrets and never committed.
gradle.rootProject.extra["API_KEY"] = "209609c8105843f5a7065846038cf9a4"
