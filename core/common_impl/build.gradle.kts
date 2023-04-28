plugins {
    id("cutaway.android.library")
    id("cutaway.android.hilt")
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "io.wetfloo.cutaway.core.commonimpl"
    compileSdk = 33
}

dependencies {
    api(project(":core:common"))

    implementation(libs.datastore.preferences)
    implementation(libs.result)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.viewmodel)
}
