plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Deps.BuildVersions.compileSdk)
    buildToolsVersion =  Deps.BuildVersions.buildTools

    defaultConfig {
        applicationId = "com.example.modulotech"
        minSdkVersion(Deps.BuildVersions.minSdk)
        targetSdkVersion(Deps.BuildVersions.targetSdk)
        versionCode = Deps.AppVersion.versionCode
        versionName = Deps.AppVersion.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Deps.BuildVersions.jvmTarget
    }
}

dependencies {

    implementation (Deps.Kotlin.stdlib)
    implementation (Deps.Androidx.coreKtx)
    implementation (Deps.Androidx.appcompat)
    implementation (Deps.Google.material)
    testImplementation (Deps.JunitJupiter.junit)
    androidTestImplementation (Deps.Androidx.androidTestExt)
    androidTestImplementation (Deps.Androidx.androidTestEspresso)
}