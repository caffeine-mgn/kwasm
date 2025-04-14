import pw.binom.publish.allTargets

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.binom.publish)
    `java-gradle-plugin`
    `maven-publish`
}

gradlePlugin {
    plugins {
        create("wit-codegen") {
            id = "pw.binom.wit.kotlin-codegen"
            implementationClass = "pw.binom.wit.KotlinCodeGen"
            description = "Kotlin WIT Codegen"
        }
    }
}

/*
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.16.0"
}


*/

dependencies {
    api(project(":wit-node"))
}