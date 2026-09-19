plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.voxcode.api"
    compileSdk {
        version = release(37)
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            val baseUrl = rootProject.extra["BASE_URL_DEBUG"] as String
            val apikey = rootProject.extra["API_KEY"] as String
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "API_KEY", "\"$apikey\"")
        }
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
    // retrofit
    implementation(libs.retrofit)

    // hilt di
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
}