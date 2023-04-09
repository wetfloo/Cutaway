import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}

// this annotation is staying here
//  until https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.ksp) apply false

    alias(libs.plugins.benmanes.versions) apply true
}

// -- Detecting stable (as in, not alpha/beta/rc) dependency updates --
val String.isStable: Boolean
    get() {
        val stableKeyword = stableWordSet.any(uppercase()::contains)
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        return stableKeyword || regex.matches(this)
    }

// Reject any non-stable (including release candidate) versions of libraries
// when checking for dependency updates
tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        !candidate.version.isStable && currentVersion.isStable
    }
}

private val stableWordSet = setOf(
    "RELEASE",
    "FINAL",
    "GA",
)
