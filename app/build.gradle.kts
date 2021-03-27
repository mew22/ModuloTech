plugins {
    id("app")
    id("qa")
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

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Androidx.coreKtx)
    implementation(Deps.Androidx.appcompat)
    implementation(Deps.Google.material)
    testImplementation(Deps.JunitJupiter.junit)
    androidTestImplementation(Deps.Androidx.androidTestExt)
    androidTestImplementation(Deps.Androidx.androidTestEspresso)
}
