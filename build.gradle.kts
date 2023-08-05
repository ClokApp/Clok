

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    // For Room
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false
}

tasks.register(name = "clean", type = Delete::class){
    delete(rootProject.buildDir)
}



