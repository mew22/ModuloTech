object Deps {

    object Versions {

        const val androidGradlePlugin = "4.1.3"
        const val kotlin = "1.4.30"

        const val appcompat = "1.2.0"
        const val coreKtx = "1.3.2"
        const val androidxTestExt = "1.1.2"
        const val androidxTestEspresso = "3.3.0"

        const val material = "1.2.1"

        const val junitJupiter = "5.7.1"
    }

    object BuildVersions {
        const val minSdk = 28
        const val targetSdk = 29
        const val compileSdk = 29
        const val buildTools = "29.0.2"
        const val jvmTarget = "1.8"
    }

    object AppVersion {
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    }

    object Androidx {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val androidTestExt = "androidx.test.ext:junit:${Versions.androidxTestExt}"
        const val androidTestEspresso =
            "androidx.test.espresso:espresso-core:${Versions.androidxTestEspresso}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object JunitJupiter {
        const val junit = "org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}"
    }

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }
}