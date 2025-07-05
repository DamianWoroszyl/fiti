plugins {
    `kotlin-dsl`
}

group = "com.fullrandom.buildlogic" // Package name for the our plugins
// todo dw check/research sourceCompatibility - which version is best?
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        // Will add in next step
    }
}