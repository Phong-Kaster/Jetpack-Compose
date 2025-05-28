import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("androidx.baselineprofile")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.jetpack"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.jetpack"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val timestamp = SimpleDateFormat("MM-dd-yyyy_hh-mm").format(Date())
        setProperty("archivesBaseName", "Bundeswehr_v${versionName}(${versionCode})_${timestamp}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        // Configuration for the 'debug' build type
        /*getByName("debug") {
            storeFile = file("key/jetpack.jks") // Path to the keystore file
            storePassword = "123465"                  // Password for the keystore
            keyAlias = "jetpack"                        // Alias for the key
            keyPassword = "123456"                    // Password for the key
        }*/

        // Creates a new configuration for the 'release' build type
        create("release") {
            storeFile = file("key/keystore.jks") // Path to the keystore file
            storePassword = "jetpack123456"           // Password for the keystore
            keyAlias = "jetpack"                     // Alias for the key
            keyPassword = "jetpack123456"            // Password for the key
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2025.05.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:3.0.3")
    implementation("androidx.profileinstaller:profileinstaller:1.4.1")


    implementation("androidx.benchmark:benchmark-macro-junit4:1.3.4")
    implementation("androidx.media3:media3-ui:1.7.1")
    implementation("androidx.media3:media3-session:1.7.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.8.2")
    "baselineProfile"(project(":baselineprofile"))

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Dependency injection with Hilt - https://developer.android.com/training/dependency-injection/hilt-android#setup
    // Hilt Android Processor - https://mvnrepository.com/artifact/com.google.dagger/hilt-android-compiler
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-android-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")


    // Preferences DataStore - https://developer.android.com/topic/libraries/architecture/datastore#preferences-datastore-dependencies
    implementation("androidx.datastore:datastore-preferences:1.1.2")


    // Get started with the Navigation component - https://developer.android.com/guide/navigation/get-started#Set-up
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.7")
    implementation("androidx.navigation:navigation-compose:2.8.7")


    // In-app Review - https://developer.android.com/guide/playcore/in-app-review/kotlin-java#setup
    implementation("com.google.android.play:review:2.0.2")
    implementation("com.google.android.play:review-ktx:2.0.2")


    // ConstraintLayout in Compose - https://developer.android.com/jetpack/compose/layouts/constraintlayout#get-started
    // Motion Layout 1 - https://developer.android.com/develop/ui/views/animations/motionlayout
    // Motion Layout 2 - https://medium.com/mindful-engineering/after-going-through-this-blog-youll-achieve-this-kind-of-polished-animation-using-motionlayout-6b76ec41c6ab
    implementation("androidx.constraintlayout:constraintlayout-compose-android:1.1.0")


    // Save data in a local database using Room - https://developer.android.com/training/data-storage/room#setup
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")


    // GSON - https://github.com/google/gson
    implementation("com.google.code.gson:gson:2.10.1")


    // Immutable Collections Library for Kotlin - https://github.com/Kotlin/kotlinx.collections.immutable,
    // Kotlin Immutable Collections - https://www.baeldung.com/kotlin/immutable-collections
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.8")


    // Retrofit - https://github.com/square/retrofit
    // Retrofit - A type-safe HTTP client for Android and Java - https://square.github.io/retrofit/
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    // Lottie for Android - https://github.com/airbnb/lottie-android
    // https://lottiefiles.com/blog/working-with-lottie-animations/getting-started-with-lottie-animations-in-android-app/
    implementation("com.airbnb.android:lottie:6.6.2")
    implementation("com.airbnb.android:lottie-compose:6.5.2")


    // https://mvnrepository.com/artifact/androidx.compose.foundation/foundation
    implementation("androidx.compose.foundation:foundation:1.7.8")


    // Consume flows safely in Jetpack Compose - https://medium.com/androiddevelopers/consuming-flows-safely-in-jetpack-compose-cde014d0d5a3
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")


    // Shared Element Transition - https://developer.android.com/develop/ui/compose/animation/shared-elements
    // Shared Element Transition In Jetpack Compose: Enriching Android User Experiences - https://getstream.io/blog/shared-element-compose/
    implementation("androidx.compose.animation:animation:1.7.8")

    // For map manager
    implementation("com.google.android.gms:play-services-location:21.3.0")


    // Getting started with WorkManager - https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started
    implementation("androidx.work:work-runtime-ktx:2.10.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
    ksp("androidx.hilt:hilt-compiler:1.2.0")


    // For media playback using ExoPlayer
    implementation("androidx.media3:media3-exoplayer:1.5.1")
    implementation("androidx.media:media:1.7.0")


    // Coil Compose - https://github.com/coil-kt/coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Ensure you have Glide library dependency in your build.gradle file
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")


}

// Dependency injection with Hilt - Allow references to generated code
//kapt {
//    correctErrorTypes = true
//}