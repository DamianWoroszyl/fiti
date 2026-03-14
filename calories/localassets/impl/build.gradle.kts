plugins {
    id("fiti.android.library")
    id("fiti.jetbrains.kotlin.android")
    id("fiti.android.hilt")
}

android {
    namespace = "com.fullrandom.calories.localassets.impl"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":calories:localassets:api"))

    implementation(libs.androidx.core.ktx)
}
