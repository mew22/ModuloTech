import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("jacoco")
}

ktlint {
    version.set("0.36.0")
    android.set(true)
}

detekt {
    baseline = file("$projectDir/detekt.xml")
}

jacoco {
    toolVersion = "0.8.5"
}

tasks {
    withType<Detekt> {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        this.jvmTarget = "1.8"
    }

    withType<Test>().configureEach {
        configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
        }
    }
}

private val classDirectoriesTree = fileTree(project.buildDir) {
    include(
        "**/classes/**/main/**",
        "**/intermediates/classes/debug/**",
        "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
        "**/tmp/kotlin-classes/debug/**"
    )

    exclude(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*\$Lambda$*.*", // Jacoco can not handle several "$" in class name.
        "**/*\$inlined$*.*" // Kotlin specific, Jacoco can not handle several "$" in class name.
    )
}

private val sourceDirectoriesTree = fileTree("${project.buildDir}") {
    include(
        "src/main/java/**",
        "src/main/kotlin/**",
        "src/debug/java/**",
        "src/debug/kotlin/**"
    )
}

private val executionDataTree = fileTree(project.buildDir) {
    include(
        "outputs/code_coverage/**/*.ec",
        "jacoco/jacocoTestReportDebug.exec",
        "jacoco/testDebugUnitTest.exec",
        "jacoco/test.exec"
    )
}

fun JacocoReportsContainer.reports() {
    xml.isEnabled = true
    html.isEnabled = true
    xml.destination = file("${buildDir}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    html.destination = file("${buildDir}/reports/jacoco/jacocoTestReport/html")
}

fun JacocoCoverageVerification.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

fun JacocoReport.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}


if (tasks.findByName("coverageReport") == null) {

    tasks.register<JacocoReport>("coverageReport") {
        group = "verification"
        description = "Code coverage report for Unit tests."
        // dependsOn("testDebugUnitTest")
        reports {
            reports()
        }
        setDirectories()
    }
}

if (tasks.findByName("androidCoverageReport") == null) {

    tasks.register<JacocoReport>("androidCoverageReport") {
        group = "verification"
        description = "Code coverage report for Unit tests."
        // dependsOn("testDebugUnitTest", "createDebugCoverageReport")
        reports {
            reports()
        }
        setDirectories()
    }
}

if (tasks.findByName("coverageVerification") == null) {
    tasks.register<JacocoCoverageVerification>("coverageVerification") {
        group = "verification"
        description = "Code coverage verification for both Android and Unit tests."
        // dependsOn("testDebugUnitTest", "createDebugCoverageReport")
        violationRules {
            rule {
                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal(0.9)
                }
            }
        }
        setDirectories()
    }
}

if (tasks.findByName("androidCoverageVerification") == null) {
    tasks.register<JacocoCoverageVerification>("androidCoverageVerification") {
        group = "verification"
        description = "Code coverage verification for both Android and Unit tests."
        // dependsOn("testDebugUnitTest", "createDebugCoverageReport")
        violationRules {
            rule {
                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    minimum = BigDecimal(0.9)
                }
            }
        }
        setDirectories()
    }
}

