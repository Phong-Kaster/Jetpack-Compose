import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.jetpack"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jetpack"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val timestamp = SimpleDateFormat("MM-dd-yyyy_hh-mm").format(Date())
        setProperty("archivesBaseName", "Bundeswehr_v${versionName}(${versionCode})_${timestamp}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Dependency injection with Hilt - https://developer.android.com/training/dependency-injection/hilt-android#setup
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")


    // Preferences DataStore - https://developer.android.com/topic/libraries/architecture/datastore#preferences-datastore-dependencies
    implementation("androidx.datastore:datastore-preferences:1.0.0")


    // Get started with the Navigation component - https://developer.android.com/guide/navigation/get-started#Set-up
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.navigation:navigation-compose:2.7.6")


    // In-app Review - https://developer.android.com/guide/playcore/in-app-review/kotlin-java#setup
    implementation("com.google.android.play:review:2.0.1")
    implementation("com.google.android.play:review-ktx:2.0.1")


    // ConstraintLayout in Compose - https://developer.android.com/jetpack/compose/layouts/constraintlayout#get-started
    // Motion Layout - https://developer.android.com/develop/ui/views/animations/motionlayout
    // Motion Layout - https://medium.com/mindful-engineering/after-going-through-this-blog-youll-achieve-this-kind-of-polished-animation-using-motionlayout-6b76ec41c6ab
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")


    // Save data in a local database using Room - https://developer.android.com/training/data-storage/room#setup
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")


    // GSON - https://github.com/google/gson
    implementation("com.google.code.gson:gson:2.10.1")


    // Immutable Collections Library for Kotlin - https://github.com/Kotlin/kotlinx.collections.immutable,
    // Kotlin Immutable Collections - https://www.baeldung.com/kotlin/immutable-collections
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.6")


    // Retrofit - https://github.com/square/retrofit
    // Retrofit - A type-safe HTTP client for Android and Java - https://square.github.io/retrofit/
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}