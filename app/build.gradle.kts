@file:Suppress("UnstableApiUsage")

plugins {
    id("cutaway.android.application")
    id("cutaway.android.hilt")
    id("cutaway.android.room")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.android.safeargs)
}

android {
    defaultConfig {
        applicationId = "io.wetfloo.cutaway"
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        @Suppress("UNUSED_VARIABLE")
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(name = "proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    flavorDimensions += "dataSource"
    productFlavors {
        create("demo") {
            dimension = "dataSource"
        }

        create("real") {
            dimension = "dataSource"
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
    implementation(libs.datastore.preferences)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.lifecycleCompose)
    implementation(libs.bundles.navigation)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.compose.material3)
    implementation(libs.compose.icons)
    implementation(libs.compose.activity)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.viewBindingDelegate)

    implementation(libs.result)
    implementation(libs.resultCoroutines)

    implementation(libs.zxingAndroidEmbedded)
    implementation(libs.awesomeQr) {
        exclude(
            group = "com.waynejo",
            module = "androidndkgif",
        ) // exclude jcenter module
    }

    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Detect memory leaks
    //    debugImplementation(libs.square.leakcanary)
}
