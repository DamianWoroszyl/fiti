package com.fullrandom.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

const val COMPILE_SDK = 34
const val MIN_SDK = 24
const val TARGET_SDK = 34

internal fun Project.configureAndroidCommon(
    extension: CommonExtension<*, *, *, *, *, *>,
) {
    extension.apply {
        compileSdk = COMPILE_SDK

        defaultConfig {
            minSdk = MIN_SDK

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}
