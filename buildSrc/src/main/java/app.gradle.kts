plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("org.jetbrains.dokka")
}

android {
    compileSdkVersion(Deps.BuildVersions.compileSdk)

    defaultConfig {
        minSdkVersion(Deps.BuildVersions.minSdk)
        targetSdkVersion(Deps.BuildVersions.targetSdk)
        versionCode = Deps.AppVersion.versionCode
        versionName = Deps.AppVersion.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isTestCoverageEnabled = false
            isShrinkResources = true
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    lintOptions {
        ignore("RequiredSize")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Deps.BuildVersions.jvmTarget
    }
}
