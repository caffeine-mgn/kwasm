import pw.binom.publish.allTargets
import pw.binom.publish.applyDefaultHierarchyBinomTemplate

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.binom.publish)
  id("maven-publish")
}

kotlin {
  allTargets()
  applyDefaultHierarchyTemplate()
  sourceSets {
    commonMain.dependencies {
      api(project(":wasm"))
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
