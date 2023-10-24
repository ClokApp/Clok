

plugins {
    id("com.android.application") version "8.1.2" apply false

    // Kotlin Version
//    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20-Beta2" apply false

    // For Room
//    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false
//    id("com.google.devtools.ksp") version "1.9.20-Beta2-1.0.13" apply false
    id("com.google.devtools.ksp") version "1.9.20-RC-1.0.13" apply false
}

tasks.register(name = "clean", type = Delete::class){
    delete(rootProject.buildDir)
}



