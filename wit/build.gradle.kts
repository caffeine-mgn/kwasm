import pw.binom.publish.allTargets
import pw.binom.publish.applyDefaultHierarchyBinomTemplate

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.binom.publish)
    id("maven-publish")
}
kotlin {
    allTargets {
        -"watchosArm64"
        -"watchosX64"
        -"watchosDeviceArm64"
        -"iosX64"
        -"watchosSimulatorArm64"
        -"watchosArm32"
        -"tvosSimulatorArm64"
        -"tvosArm64"
        -"iosArm64"
        -"iosSimulatorArm64"
        -"wasmJs"
    }
    wasmJs()
    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain.dependencies {
            api(libs.binom.io.file)
        }
        commonTest.dependencies {
            api(kotlin("test-common"))
            api(kotlin("test-annotations-common"))
        }
        jvmTest.dependencies {
            api(kotlin("test-junit"))
        }
    }
}
