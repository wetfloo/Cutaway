plugins {
    id("cutaway.android.library")
    id("cutaway.android.hilt")
}

android {
    namespace = "io.wetfloo.cutaway.communication"
}

dependencies {
    implementation(project(":core:common_impl"))
}
