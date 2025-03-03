plugins {
    alias(libs.plugins.androidApplication)

    alias(libs.plugins.jetbrainsKotlin)

    // For Room
    alias(libs.plugins.ksp)

    // Compose Compiler
    alias(libs.plugins.compose.compiler)

    // Kotlin serialization plugin for type-safe routes and navigation arguments
    alias(libs.plugins.kotlin.serialization)


}

android {
    namespace = "com.kingfu.clok"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kingfu.clok"
        minSdk = 26
        targetSdk = 35
        versionCode = 17
        versionName = "2.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile(name = "proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(name = "debug")
        }
//        getByName("debug") {
//            isMinifyEnabled = false
//            isShrinkResources = false
//            proguardFiles(
//                getDefaultProguardFile(name = "proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
//    composeOptions {
//        // Compose Compiler Version
////        kotlinCompilerExtensionVersion = "1.5.3"
////        kotlinCompilerExtensionVersion = "1.5.4-dev-k1.9.20-Beta2-ac5f960bdaf"
//        kotlinCompilerExtensionVersion = "1.5.12"
//    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    // Android
    implementation(libs.androidx.core.ktx)

    // Preview
    implementation(libs.androidx.ui.tooling.preview)
    
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Test
    testImplementation(libs.junit)


    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Setting Permission For Instrumental Test
    implementation(libs.androidx.rules)
    
    // Material Design 3
    implementation(libs.androidx.material3)

    // Compose Animation
    implementation(libs.androidx.animation.android)

    // Foundation
    implementation(libs.androidx.foundation.android)

    // Runtime
    implementation(libs.androidx.runtime.android)

    // Compose UI
    implementation(libs.androidx.ui)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // preferences DataStore
    implementation(libs.androidx.datastore.preferences)

    // SnapFlingBehavior
    implementation(libs.androidx.foundation)

    // Icons
    implementation(libs.androidx.material.icons.extended)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // Splash API
    implementation(libs.androidx.core.splashscreen)

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation(libs.kotlinx.serialization.json)


    // Android Test
    androidTestImplementation(libs.navigation.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)











}