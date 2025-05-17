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
include(":wit")
include(":wit-codegen")
include(":wit-node")
include(":wasi-socket")
include(":wasi-clock")
include(":wasi-runtime-config")
//include(":wasm-runner-serialization")
//include(":wasm-serialization")
