package io.wetfloo.cutaway.buildlogic.convention

import com.android.build.api.dsl.CommonExtension
import io.wetfloo.cutaway.buildlogic.convention.extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 33

        defaultConfig {
            minSdk = 26
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            //            freeCompilerArgs = freeCompilerArgs + listOf(
            //                "-opt-in=kotlin.RequiresOptIn",
            // Enable experimental coroutines APIs, including Flow
            //                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            //                "-opt-in=kotlinx.coroutines.FlowPreview",
            //                "-opt-in=kotlin.Experimental",
            //            )

            jvmTarget = JavaVersion.VERSION_17.toString()
        }

        packaging {
            resources.pickFirsts.apply {
                add("META-INF/LICENSE-notice.*")
                add("META-INF/LICENSE.*")
                add("META-INF/*.properties")
                add("META-INF/AL2.0")
                add("META-INF/LGPL2.1")
            }
        }
    }

    dependencies {
        add(
            configurationName = "coreLibraryDesugaring",
            dependencyNotation = libs
                .findLibrary("core-desugaring")
                .get(),
        )
    }

    with(kotlinExtension) {
        jvmToolchain(17)
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware)
        .extensions
        .configure("kotlinOptions", block)
}
