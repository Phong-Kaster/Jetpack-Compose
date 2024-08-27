// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false

    /* Dependency injection with Hilt  - https://developer.android.com/training/dependency-injection/hilt-android#setup*/
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("com.android.test") version "8.5.2" apply false
    id("androidx.baselineprofile") version "1.2.4" apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}