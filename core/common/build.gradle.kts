plugins {
    id("java-library")
    id("fiti.jvm.library")
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    implementation(libs.kotlinx.coroutines.core)
}