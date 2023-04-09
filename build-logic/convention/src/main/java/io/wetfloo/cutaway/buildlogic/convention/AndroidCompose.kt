package io.wetfloo.cutaway.buildlogic.convention

import com.android.build.api.dsl.CommonExtension
import io.wetfloo.cutaway.buildlogic.convention.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs
                .findVersion("compose-compiler")
                .get()
                .toString()
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }
    }
}
