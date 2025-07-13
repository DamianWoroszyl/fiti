pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Fiti"
include(":app")
include(":calories:model")
include(":calories:storage")
include(":calories:storage:api")
include(":calories:storage:database")
include(":calories:data")
include(":calories:data:api")
include(":calories:data:impl")
