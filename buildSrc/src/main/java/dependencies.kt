import com.android.tools.build.bundletool.model.utils.Versions.ANDROID_P_API_VERSION
import com.android.tools.build.bundletool.model.utils.Versions.ANDROID_Q_API_VERSION

object Deps {

    object Versions {
        const val kotlin = "1.4.30"

        const val appcompat = "1.2.0"
        const val coreKtx = "1.3.2"
        const val androidxTestExt = "1.1.2"
        const val androidxTestEspresso = "3.3.0"

        const val material = "1.2.1"

        const val junitJupiter = "5.7.1"
    }

    object BuildVersions {
        const val minSdk = ANDROID_P_API_VERSION
        const val targetSdk = ANDROID_Q_API_VERSION
        const val compileSdk = ANDROID_Q_API_VERSION
        const val buildTools = "29.0.2"
        const val jvmTarget = "1.8"
    }

    object AppVersion {
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object PluginVersions {
        const val androidGradlePlugin = "4.1.3"
        const val ktlint = "9.2.1"
        const val detekt = "1.7.4"
        const val dokka = "0.10.1"
        const val jacoco = "0.8.5"
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
        const val android = "com.android.tools.build:gradle:${PluginVersions.androidGradlePlugin}"
        const val androidApi = "com.android.tools.build:gradle-api:${PluginVersions.androidGradlePlugin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${PluginVersions.ktlint}"
        const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginVersions.detekt}"
        const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${PluginVersions.dokka}"
        const val jacoco = "org.jacoco:org.jacoco.core:${PluginVersions.jacoco}"
    }
}