package pw.binom.wit

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import pw.binom.wit.node.WitNode
import pw.binom.wit.parser.BasicTokenizer
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.parser.Tokenizer
import pw.binom.wit.readers.WitReader
import java.io.Closeable
import java.io.Reader

abstract class WitKotlinCodegenTask : DefaultTask() {

    @get:Input
    val params = project.extensions.getByType(WitExtension::class.java)

    class InputTokenizer(val data: Reader) : BasicTokenizer(), Closeable {
        override fun nextChar(): Char {
            val code = data.read()
            if (code == -1) {
                throw Tokenizer.EOFException()
            }
            return code.toChar()
        }

        override fun close() {
            data.close()
        }
    }

    @TaskAction
    fun execute() {
        val witFiles = params.directory.get().asFile.walkTopDown().filter {
            it.isFile && it.name.endsWith(".wit")
        }.map {
            val wit = WitNode(null, emptyList())
            it.inputStream().bufferedReader().use {
                InputTokenizer(it).use { tokenizer ->
                    WitReader.parse(tokenizer = BufferedTokenizer(100, tokenizer), fileRootVisitor = wit)
                }
            }
            wit
        }
    }
}