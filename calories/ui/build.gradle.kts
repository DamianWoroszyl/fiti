plugins {
    id("fiti.android.library")
    id("fiti.jetbrains.kotlin.android")
    id("fiti.android.compose")
    id("fiti.android.hilt")
}

android {
    namespace = "com.fullrandom.fiti.calories.ui"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":calories:model"))
    implementation(project(":calories:domain"))
    implementation(project(":calories:assistant:api"))
    implementation(project(":calories:assistant:impl"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.androidx.room.testing)
}