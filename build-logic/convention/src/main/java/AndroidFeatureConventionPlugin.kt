import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                // order of these plugins matters here for some reason
                apply("cutaway.android.compose-library")
                apply("cutaway.android.hilt")
            }
        }
    }
}
