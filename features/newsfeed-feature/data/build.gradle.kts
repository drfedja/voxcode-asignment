plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.voxcode.data"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    // hilt di
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // junit
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.junit)

    // retrofit
    implementation(libs.retrofit)

    // moshi
    implementation(libs.moshi.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    ksp(libs.moshi.codegen)

    // project implementation
    implementation(project(":core:core-api"))
    implementation(project(":features:newsfeed-feature:domain"))
}