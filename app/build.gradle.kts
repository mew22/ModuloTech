plugins {
    id("app")
    id("qa")
    id("kotlin-android")
}

android {

    defaultConfig {
        applicationId = "com.example.modulotech"
    }
}

detekt {
    autoCorrect = true
    config = files("$rootDir/config/detekt/default_rules.yml",
        "$rootDir/config/detekt/custom_rules.yml")
}

dependencies {

    implementation(Deps.Kotlin.stdlibCommon)
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlin.stdlibJdk8)
    implementation(Deps.Kotlin.reflect)
    implementation(Deps.Kotlin.coroutinesCore)
    implementation(Deps.Kotlin.coroutinesAndroid)

    implementation(Deps.Androidx.coreKtx)
    implementation(Deps.Androidx.appcompat)
    implementation(Deps.Androidx.fragment)
    implementation(Deps.Androidx.constraintLayout)
    implementation(Deps.Androidx.recyclerview)
    implementation(Deps.Androidx.swipeRefreshLayout)
    implementation(Deps.Androidx.navigationFragment)
    implementation(Deps.Androidx.navigationUi)
    implementation(Deps.Androidx.lifecycleExt)
    implementation(Deps.Androidx.lifecycleVm)
    implementation(Deps.Androidx.livedata)
    implementation(Deps.Androidx.room)
    kapt(Deps.Androidx.roomCompiler)

    implementation(Deps.Google.material)
    implementation(Deps.Google.gsonExtras)

    implementation(Deps.Squareup.retrofit)
    implementation(Deps.Squareup.retrofitGsonConverter)
    implementation(Deps.Squareup.retrofitOkHttpInterceptor)

    testRuntimeOnly(Deps.JunitJupiter.junitEngine)
    testImplementation(Deps.JunitJupiter.junitApi)
    testImplementation(Deps.JunitJupiter.junitParams)
    testImplementation(Deps.Androidx.androidTestCore)
    testImplementation(Deps.Androidx.androidTestTruth)
    testImplementation(Deps.Mockk.mockk)
    testImplementation(Deps.Turbine.turbine)
    testImplementation(Deps.Squareup.retrofitMock)
    testImplementation(Deps.Squareup.mockWebServer)
    testImplementation(Deps.Kotlin.coroutinesTest)
    testImplementation(Deps.Androidx.archCoreTesting)

    androidTestImplementation(Deps.Androidx.androidTestExt)
    androidTestImplementation(Deps.Androidx.androidTestEspresso)
}
