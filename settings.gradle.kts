pluginManagement {
    repositories {
        maven(url = "https://repo.binom.pw")
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "Binom-KWASM"
include(":wasm")
include(":wasm-node")
include(":wasm-runner")
include(":wasm-wasi")
//include(":uuid-serialization")
