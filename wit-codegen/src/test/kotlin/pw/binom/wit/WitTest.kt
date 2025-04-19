package pw.binom.wit

import org.junit.jupiter.api.Test
import pw.binom.wit.node.PackageNode
import pw.binom.wit.node.WitNode
import pw.binom.wit.parser.BufferedTokenizer
import pw.binom.wit.readers.WitReader
import java.io.File

class WitTest {
    @Test
    fun aa() {
//        val wits = File("/tmp/ff/wasi-http/wit").listFiles()
//            .filter {
//                it.isFile && it.name.endsWith(".wit")
//            }.map { file ->
//                val wit = WitNode(null, emptyList())
//                file.inputStream().reader().use {
//                    WitKotlinCodegenTask.InputTokenizer(it).use { tokenizer ->
//                        try {
//                            WitReader.parse(tokenizer = BufferedTokenizer(100, tokenizer), fileRootVisitor = wit)
//                        } catch (e: Throwable) {
//                            throw IllegalStateException("Can't parse file ${file.absolutePath}", e)
//                        }
//                    }
//                }
//                wit
//            }.toList()

        val p = WitProject.create(File("/tmp/ff/wasi-http/wit"))
//        val dd = p.get(PackageNode("wasi", "http", "0.2.5"), "proxy")
        val exports = HashSet<InterfacePath>()
        val imports = HashSet<InterfacePath>()
        p.collectInterfaces(PackageNode("wasi", "http", "0.2.5"), "proxy", HashSet(), exports, imports)
        println("Exports: $exports")
        println("Imports: $imports")
    }
}

fun WitProject.Companion.create(direction: File): WitProject {
    val main = WitPackage.create(direction)
    val deps = direction.resolve("deps").listFiles()
        .filter { it.isDirectory }
        .map { file -> WitPackage.create(file) }
    return WitProject((listOf(main) + deps).associateBy { it.packageName })
}

fun WitPackage.Companion.create(direction: File): WitPackage {
    val list = direction.listFiles()
        .filter {
            it.isFile && it.name.endsWith(".wit")
        }.map { file ->
            val wit = WitNode(null, emptyList())
            file.inputStream().reader().use {
                WitKotlinCodegenTask.InputTokenizer(it).use { tokenizer ->
                    try {
                        WitReader.parse(tokenizer = BufferedTokenizer(100, tokenizer), fileRootVisitor = wit)
                    } catch (e: Throwable) {
                        throw IllegalStateException("Can't parse file ${file.absolutePath}", e)
                    }
                }
            }
            wit
        }.toList()
    return WitPackage.create(list)
}