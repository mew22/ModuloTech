@file:Suppress("unused")

import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("org.jetbrains.dokka")
}

tasks {

    // clean up task
    val clean by registering {
        delete("$buildDir")
    }

    // HTML Documentation
    val dokka by getting(DokkaTask::class) {
        subProjects = subprojects.map {
            it.name
        }

        configuration {
            jdkVersion = 8
        }
    }

    // Javadoc Documentation
    val javadoc by registering(DokkaTask::class) {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/javadoc"

        subProjects = subprojects.map {
            it.name
        }

        configuration {
            jdkVersion = 8
        }
    }

    // GFM Documentation
    val markdown by registering(DokkaTask::class) {
        outputFormat = "gfm"
        outputDirectory = "$buildDir/markdown"
        subProjects = subprojects.map {
            it.name
        }

        configuration {
            jdkVersion = 8
        }
    }
}
