plugins {
    id("cutaway.android.application")
    id("cutaway.android.hilt")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    defaultConfig {
        applicationId = "io.wetfloo.cutaway"
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(name = "proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    namespace = "io.wetfloo.cutaway"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:common_impl"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    implementation(libs.material.material)
    implementation(libs.bundles.lifecycle)

    implementation(libs.compose.material3)
    implementation(libs.compose.icons)
    implementation(libs.compose.activity)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    // Detect memory leaks
    debugImplementation(libs.square.leakcanary)
}
