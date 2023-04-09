package io.wetfloo.cutaway.buildlogic.convention.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType


/**
 * Return version catalog for libs
 */
internal val Project.libs: VersionCatalog
    get() = versions.named("libs")

private val Project.versions: VersionCatalogsExtension
    get() = extensions.getByType()
