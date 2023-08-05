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
        versionCode = 14
        versionName = "1.5.6"

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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.core:core-ktx:1.10.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.3")

    // Material 2
    implementation("androidx.compose.material:material:1.4.3")

    // Material Design 3
    implementation("androidx.compose.material3:material3:1.2.0-alpha04")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.0-rc01")

    // preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // SnapFlingBehavior
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha02")

    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.4.3")

    // Room
    implementation("androidx.room:room-runtime:2.6.0-alpha02")
    ksp("androidx.room:room-compiler:2.6.0-alpha02")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.0-alpha02")

    // Splash API
    implementation("androidx.core:core-splashscreen:1.0.1")

}