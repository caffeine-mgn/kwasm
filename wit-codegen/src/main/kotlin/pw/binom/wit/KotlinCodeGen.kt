package pw.binom.wit

import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinCodeGen : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("wit", WitExtension::class.java)
        TODO("Not yet implemented")
    }
}