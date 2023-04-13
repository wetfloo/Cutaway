group = "io.wetfloo.cutaway.buildlogic"

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "cutaway.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidFeature") {
            id = "cutaway.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidComposeLibrary") {
            id = "cutaway.android.compose-library"
            implementationClass = "AndroidComposeLibraryConventionPlugin"
        }

        register("androidLibrary") {
            id = "cutaway.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidHilt") {
            id = "cutaway.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidRoom") {
            id = "cutaway.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}
