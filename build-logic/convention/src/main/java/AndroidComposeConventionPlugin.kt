import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import com.fullrandom.convention.configureAndroidCompose
import com.fullrandom.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlin-compose").get().get().pluginId)
            }

            extensions.findByType(ApplicationExtension::class.java)?.apply {
                configureAndroidCompose(this)
            }
            extensions.findByType(LibraryExtension::class.java)?.apply {
                configureAndroidCompose(this)
            }
        }
    }
}
