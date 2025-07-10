package com.fullrandom.convention

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project

internal fun Project.configureDetekt(
    extension: DetektExtension,
) {
    extension.apply {
        source.setFrom(rootDir)
        toolVersion = libs.findVersion("detekt").get().requiredVersion
        config.setFrom(files("$rootDir/config/detekt/config.yml"))
        buildUponDefaultConfig = true
    }
}
