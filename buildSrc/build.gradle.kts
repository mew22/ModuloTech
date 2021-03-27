import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
    }
}

repositories {
    maven { url = uri("https://plugins.gradle.org/m2/") }
    jcenter()
    google()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.4.30"
}

dependencies {

    // Android
    val androidPluginVersion="4.1.3"
    implementation("com.android.tools.build:gradle:$androidPluginVersion")
    implementation("com.android.tools.build:gradle-api:$androidPluginVersion")

    // Kotlin
    val kotlinVersion="1.4.30"
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")

    // KtLint
    val ktLintPluginVersion = "9.2.1"
    implementation("org.jlleitschuh.gradle:ktlint-gradle:${ktLintPluginVersion}")

    // Detekt
    val detektPluginVersion = "1.7.4"
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${detektPluginVersion}")

    // Dokka
    val dokkaPluginVersion = "0.10.1"
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${dokkaPluginVersion}")

    // Jacoco
    val jacocoPluginVersion = "0.8.5"
    implementation("org.jacoco:org.jacoco.core:${jacocoPluginVersion}")
}
