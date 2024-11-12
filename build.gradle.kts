// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false

    /* Dependency injection with Hilt  - https://developer.android.com/training/dependency-injection/hilt-android#setup*/
    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("com.android.test") version "8.7.1" apply false
    id("androidx.baselineprofile") version "1.3.3" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.3")
    }
}