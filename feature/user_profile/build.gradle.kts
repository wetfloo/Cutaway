plugins {
    id("cutaway.android.feature")
}

android {
    namespace = "io.wetfloo.cutaway.feature.user_profile"
}

dependencies {
    implementation(libs.compose.material3)
    implementation(libs.compose.icons)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.appyx.core)
}
