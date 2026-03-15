plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.cityfix"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.cityfix"
        minSdk = 25
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

//val cameraxVersion = "1.3.1"
//implementation("androidx.camera:camera-core:${cameraxVersion}")
//implementation("androidx.camera:camera-camera2:${cameraxVersion}")
//implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
//implementation("androidx.camera:camera-view:${cameraxVersion}")

dependencies {
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation(libs.firebase.firestore)

    // UI & Navigation
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(libs.androidx.navigation.compose)
    implementation("com.patrykandpatrick.vico:compose-m3:2.0.0-alpha.22") // charts

    // Maps & Location
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("org.osmdroid:osmdroid-android:6.1.18")
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    // AndroidX & Compose (From Version Catalog)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}