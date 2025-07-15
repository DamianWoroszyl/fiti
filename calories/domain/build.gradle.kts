plugins {
    `java-library`
    id("fiti.jvm.library")
}

dependencies {
    implementation(project(":calories:model"))
    implementation(project(":calories:data:api"))
    implementation(project(":calories:assistant:api"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
}