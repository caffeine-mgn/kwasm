import pw.binom.publish.allTargets
import pw.binom.publish.applyDefaultHierarchyBinomTemplate

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.binom.publish)
  id("maven-publish")
}
kotlin {
  wasmWasi {
    nodejs()
  }
  applyDefaultHierarchyTemplate()
  sourceSets {
    commonMain.dependencies {
    }
    commonTest.dependencies {
      api(kotlin("test-common"))
      api(kotlin("test-annotations-common"))
    }
  }
}
