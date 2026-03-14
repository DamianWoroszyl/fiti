plugins {
    id("fiti.android.application")
    id("fiti.jetbrains.kotlin.android")
    id("fiti.android.compose")
    id("fiti.android.hilt")
    id("fiti.kotlin.detekt")
}

android {
    namespace = "com.fullrandom.fiti"

    defaultConfig {
        applicationId = "com.fullrandom.fiti"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
}

dependencies {
    implementation(project(":calories:ui:api"))
    implementation(project(":calories:ui:impl"))
    implementation(project(":calories:model"))
    implementation(project(":calories:data:impl"))
    implementation(project(":calories:assistant:impl"))
    implementation(project(":calories:storage:api"))
    implementation(project(":calories:storage:database"))
    implementation(project(":calories:localassets:impl"))
    implementation(project(":core:ui:api"))
    implementation(project(":core:ui:impl"))
    implementation(project(":core:common"))
    implementation(project(":core:init"))

    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.material3.adaptive.navigation3)
    implementation(libs.kotlinx.serialization.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}