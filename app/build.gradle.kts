import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.ksp)// Use KSP for Room, Hilt, etc.
    alias(libs.plugins.navigation.safeArgs)
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
        getByName("release") {
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))

    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)

    implementation(libs.material3)
    implementation(libs.appcompat)

    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.profileinstaller)

    implementation(libs.benchmark.macro.junit4)

    implementation(libs.media3.ui)
    implementation(libs.media3.session)
    implementation(libs.foundation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    baselineProfile(project(":baselineprofile"))

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Dependency injection with Hilt - https://developer.android.com/training/dependency-injection/hilt-android#setup
    // Hilt Android Processor - https://mvnrepository.com/artifact/com.google.dagger/hilt-android-compiler
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)  // Use KSP for Hilt instead of kapt
    implementation(libs.hilt.navigation.fragment)

    // Preferences DataStore - https://developer.android.com/topic/libraries/architecture/datastore#preferences-datastore-dependencies
    implementation(libs.datastore.preferences)

    // Get started with the Navigation component - https://developer.android.com/guide/navigation/get-started#Set-up
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.compose)

    // In-app Review - https://developer.android.com/guide/playcore/in-app-review/kotlin-java#setup
    implementation(libs.review)
    implementation(libs.review.ktx)

    // ConstraintLayout in Compose - https://developer.android.com/jetpack/compose/layouts/constraintlayout#get-started
    // Motion Layout 1 - https://developer.android.com/develop/ui/views/animations/motionlayout
    // Motion Layout 2 - https://medium.com/mindful-engineering/after-going-through-this-blog-youll-achieve-this-kind-of-polished-animation-using-motionlayout-6b76ec41c6ab
    implementation(libs.constraintlayout.compose.android)

    // Save data in a local database using Room - https://developer.android.com/training/data-storage/room#setup
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // GSON - https://github.com/google/gson
    implementation(libs.gson)

    // Immutable Collections Library for Kotlin - https://github.com/Kotlin/kotlinx.collections.immutable,
    // Kotlin Immutable Collections - https://www.baeldung.com/kotlin/immutable-collections
    implementation(libs.kotlinx.collections.immutable)

    // Retrofit - https://github.com/square/retrofit
    // Retrofit - A type-safe HTTP client for Android and Java - https://square.github.io/retrofit/
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Lottie for Android - https://github.com/airbnb/lottie-android
    // https://lottiefiles.com/blog/working-with-lottie-animations/getting-started-with-lottie-animations-in-android-app/
    implementation(libs.lottie)
    implementation(libs.android.lottie.compose)

    // https://mvnrepository.com/artifact/androidx.compose.foundation/foundation
    implementation(libs.foundation)

    // Consume flows safely in Jetpack Compose - https://medium.com/androiddevelopers/consuming-flows-safely-in-jetpack-compose-cde014d0d5a3
    implementation(libs.lifecycle.runtime.compose)

    // Shared Element Transition - https://developer.android.com/develop/ui/compose/animation/shared-elements
    // Shared Element Transition In Jetpack Compose: Enriching Android User Experiences - https://getstream.io/blog/shared-element-compose/
    implementation(libs.animation)

    // For map manager
    implementation(libs.play.services.location)

    // Getting started with WorkManager - https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started
    implementation(libs.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.hilt.compiler)

    // For media playback using ExoPlayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media)

    // Coil Compose - https://github.com/coil-kt/coil
    implementation(libs.coil.compose)
    implementation(libs.coil.kt.coil.gif)

    // Ensure you have Glide library dependency in your build.gradle file
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    // Allow request permission in Composable
    implementation(libs.accompanist.permissions)
}