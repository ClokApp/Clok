

plugins {


    // Android Gradle Plugin
    alias(libs.plugins.androidApplication) apply false

    // Kotlin Version
    alias(libs.plugins.jetbrainsKotlin) apply false

    // KSP (For Room)
    alias(libs.plugins.ksp) apply false

    // google services
    alias(libs.plugins.googleServices) apply false

    // Compose Compiler
    alias(libs.plugins.compose.compiler) apply false




}

tasks.register(name = "clean", type = Delete::class){
    delete(rootProject.buildDir)
}



