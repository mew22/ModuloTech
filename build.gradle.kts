// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven { url = uri("https://artifactory.cronapp.io/public-release/") }
        google()
        jcenter()
    }
    dependencies {
        classpath (Deps.GradlePlugin.android)
        classpath (Deps.GradlePlugin.kotlin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        maven { url = uri("https://artifactory.cronapp.io/public-release/") }
        google()
        jcenter()
    }
}

plugins {
    id("documentation")
}