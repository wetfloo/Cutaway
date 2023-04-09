plugins {
    id("cutaway.android.compose-library")
}

android {
    namespace = "io.wetfloo.cutaway.core.ui"
}

dependencies {
    implementation(libs.compose.material3)
    implementation(libs.compose.icons)
}
