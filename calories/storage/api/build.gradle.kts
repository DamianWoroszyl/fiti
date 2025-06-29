plugins {
    `java-library`
    id("fiti.jvm.library")
}

dependencies {
    implementation(project(":calories:model"))
    implementation(libs.kotlinx.coroutines.core)
}