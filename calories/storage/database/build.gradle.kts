plugins {
    id("fiti.android.library")
    id("fiti.jetbrains.kotlin.android")
    id("fiti.android.room")
    id("fiti.android.hilt")
}

android {
    namespace = "com.fullrandom.fiti.calories.storage.database"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":calories:model"))
    implementation(project(":calories:storage:api"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.androidx.room.testing)
}