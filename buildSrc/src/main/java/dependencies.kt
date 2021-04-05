import com.android.tools.build.bundletool.model.utils.Versions.ANDROID_P_API_VERSION
import com.android.tools.build.bundletool.model.utils.Versions.ANDROID_Q_API_VERSION

object Deps {

    object Versions {
        const val kotlin = "1.4.30"
        const val coroutines = "1.4.3"

        const val appcompat = "1.2.0"
        const val coreKtx = "1.3.2"
        const val fragment = "1.3.2"
        const val androidxTestExt = "1.1.2"
        const val androidxTestEspresso = "3.3.0"
        const val androidTestCore = "1.3.0"
        const val navigation = "2.3.4"
        const val recyclerview = "1.1.0"
        const val constraintLayout = "2.0.4"
        const val room = "2.2.6"
        const val lifecycle = "2.2.0"
        const val archCoreTesting = "2.1.0"

        const val retrofit = "2.9.0"
        const val retrofitOkHttpInterceptor = "4.9.1"
        const val mockWebServer = "4.9.1"

        const val material = "1.2.1"
        const val gsonExtras = "2.8.5"

        const val mockk = "1.11.0"
        const val turbine = "0.4.1"

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
        const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val stdlibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        // Coroutines
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object Androidx {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val androidTestCore = "androidx.test:core:${Versions.androidTestCore}"
        const val androidTestTruth = "androidx.test.ext:truth:${Versions.androidTestCore}"
        const val androidTestExt = "androidx.test.ext:junit:${Versions.androidxTestExt}"
        const val androidTestEspresso =
            "androidx.test.espresso:espresso-core:${Versions.androidxTestEspresso}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.recyclerview}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val room = "androidx.room:room-ktx:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val lifecycleVm = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val fragment = "androidx.fragment:fragment:${Versions.fragment}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val gsonExtras = "com.google.code.gson:gson-extras:${Versions.gsonExtras}"
    }

    object Squareup {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGsonConverter =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val retrofitOkHttpInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitOkHttpInterceptor}"
        const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    }

    object JunitJupiter {
        const val junitApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}"
        const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}"
        const val junitParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}"
    }

    object Mockk {
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
    }

    object Turbine {
        const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    }

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:${PluginVersions.androidGradlePlugin}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${PluginVersions.ktlint}"
        const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginVersions.detekt}"
        const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${PluginVersions.dokka}"
        const val jacoco = "org.jacoco:org.jacoco.core:${PluginVersions.jacoco}"
    }
}