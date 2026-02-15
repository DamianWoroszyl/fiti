plugins {
    `java-library`
    id("fiti.jvm.library")
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.javax.inject)
}
