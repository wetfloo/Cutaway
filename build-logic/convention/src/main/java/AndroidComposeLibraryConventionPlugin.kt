import com.android.build.gradle.LibraryExtension
import io.wetfloo.cutaway.buildlogic.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidComposeLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("cutaway.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}
