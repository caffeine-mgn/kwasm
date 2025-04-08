allprojects {
    group = "pw.binom.wasm"

    repositories {
        mavenLocal()
        mavenCentral()
        maven(url = "https://repo.binom.pw")
        gradlePluginPortal()
    }
}

tasks {
    val publishToMavenLocal by creating {
        val self = this
        getTasksByName("publishToMavenLocal", true).forEach {
            if (it !== self) {
                dependsOn(it)
            }
        }
    }

    val publish by creating {
        val self = this
        getTasksByName("publish", true).forEach {
            if (it !== self) {
                dependsOn(it)
            }
        }
    }
}
