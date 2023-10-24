plugins {
    id("com.android.application")
    kotlin("android")

    // For Room
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.kingfu.clok"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kingfu.clok"
        minSdk = 26
        targetSdk = 34
        versionCode = 15
        versionName = "1.6.1"

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
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile(name = "proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    composeOptions {
        // Compose Compiler Version
//        kotlinCompilerExtensionVersion = "1.5.3"
        kotlinCompilerExtensionVersion = "1.5.4-dev-k1.9.20-Beta2-ac5f960bdaf"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.core:core-ktx:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")

    // Setting Permission For Instrumental Test
    implementation("androidx.test:rules:1.5.0")
    
    // Material Design 3
    implementation("androidx.compose.material3:material3:1.2.0-alpha10")

    // Compose Animation
    implementation("androidx.compose.animation:animation-android:1.6.0-alpha08")

    // Foundation
    implementation("androidx.compose.foundation:foundation-android:1.6.0-alpha08")

    // Runtime
    implementation("androidx.compose.runtime:runtime-android:1.6.0-alpha08")

    // Compose UI
    implementation("androidx.compose.ui:ui:1.6.0-alpha08")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.4")

    // preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // SnapFlingBehavior
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha08")

    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.0")

    // Splash API
    implementation("androidx.core:core-splashscreen:1.0.1")

}