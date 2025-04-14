package pw.binom.wit

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import kotlin.system.exitProcess

abstract class WitExtension {
    @get:InputDirectory
    abstract val directory: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDirection: RegularFileProperty

    @get:Input
    abstract val world: Property<String>
}