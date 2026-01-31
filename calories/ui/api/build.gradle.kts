plugins {
    `java-library`
    id("fiti.jvm.library")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.kotlinx.serialization.core)
}