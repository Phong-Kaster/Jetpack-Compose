import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("androidx.baselineprofile")
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
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.profileinstaller:profileinstaller:1.3.1")


    implementation("androidx.benchmark:benchmark-macro-junit4:1.2.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    "baselineProfile"(project(":baselineprofile"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Dependency injection with Hilt - https://developer.android.com/training/dependency-injection/hilt-android#setup
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")


    // Preferences DataStore - https://developer.android.com/topic/libraries/architecture/datastore#preferences-datastore-dependencies
    implementation("androidx.datastore:datastore-preferences:1.0.0")


    // Get started with the Navigation component - https://developer.android.com/guide/navigation/get-started#Set-up
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.navigation:navigation-compose:2.7.7")


    // In-app Review - https://developer.android.com/guide/playcore/in-app-review/kotlin-java#setup
    implementation("com.google.android.play:review:2.0.1")
    implementation("com.google.android.play:review-ktx:2.0.1")


    // ConstraintLayout in Compose - https://developer.android.com/jetpack/compose/layouts/constraintlayout#get-started
    // Motion Layout - https://developer.android.com/develop/ui/views/animations/motionlayout
    // Motion Layout - https://medium.com/mindful-engineering/after-going-through-this-blog-youll-achieve-this-kind-of-polished-animation-using-motionlayout-6b76ec41c6ab
    implementation("androidx.constraintlayout:constraintlayout-compose-android:1.1.0-alpha13")


    // Save data in a local database using Room - https://developer.android.com/training/data-storage/room#setup
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")


    // GSON - https://github.com/google/gson
    implementation("com.google.code.gson:gson:2.10.1")


    // Immutable Collections Library for Kotlin - https://github.com/Kotlin/kotlinx.collections.immutable,
    // Kotlin Immutable Collections - https://www.baeldung.com/kotlin/immutable-collections
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")


    // Retrofit - https://github.com/square/retrofit
    // Retrofit - A type-safe HTTP client for Android and Java - https://square.github.io/retrofit/
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Lottie for Android - https://github.com/airbnb/lottie-android
    // https://lottiefiles.com/blog/working-with-lottie-animations/getting-started-with-lottie-animations-in-android-app/
    implementation("com.airbnb.android:lottie:6.3.0")
    implementation("com.airbnb.android:lottie-compose:6.3.0")

    // https://mvnrepository.com/artifact/androidx.compose.foundation/foundation
    implementation("androidx.compose.foundation:foundation:1.7.0-alpha04")


    // collectAsStateWithLifecycle() collects values from a Flow in a lifecycle-aware manner, allowing your app to conserve app resources
    // https://developer.android.com/develop/ui/compose/state#use-other-types-of-state-in-jetpack-compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}