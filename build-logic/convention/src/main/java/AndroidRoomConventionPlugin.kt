import com.google.devtools.ksp.gradle.KspExtension
import io.wetfloo.cutaway.buildlogic.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.process.CommandLineArgumentProvider
import java.io.File

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            extensions.configure<KspExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
            }

            dependencies {
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("room.runtime").get(),
                )
                add(
                    configurationName = "implementation",
                    dependencyNotation = libs.findLibrary("room.ktx").get(),
                )
                add(
                    configurationName = "ksp",
                    dependencyNotation = libs.findLibrary("room.compiler").get(),
                )
            }
        }
    }

    /**
     * [Export schemas](https://developer.android.com/training/data-storage/room/migrating-db-versions#export-schemas)
     */
    private class RoomSchemaArgProvider(
        @get:InputDirectory
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val schemaDir: File,
    ) : CommandLineArgumentProvider {

        override fun asArguments(): Iterable<String> =
            setOf("room.schemaLocation=${schemaDir.path}")
    }
}
