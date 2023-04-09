plugins {
    id("cutaway.android.library")
    id("cutaway.android.hilt")
}

android {
    namespace = "io.wetfloo.cutaway.wiring"
}

dependencies {
    implementation(project(":core:common_impl"))
}
