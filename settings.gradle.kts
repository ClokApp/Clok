pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // To use the latest Compose Compiler
        maven(url = "https://androidx.dev/storage/compose-compiler/repository/")
    }
}
rootProject.name = "Clok"
include(":app")
