plugins {
    id("cutaway.android.library")
}

android {
    namespace = "io.wetfloo.cutaway.core.commonimpl"
    compileSdk = 33
}

dependencies {
    api(project(":core:common"))

    implementation(libs.javax.inject)
    implementation(libs.datastore.preferences)
    implementation(libs.result)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
}
