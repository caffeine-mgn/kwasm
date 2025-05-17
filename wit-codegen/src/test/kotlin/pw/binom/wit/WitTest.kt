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

//        val p = ProjectScope.create(File("/tmp/ff/wasi-http/wit"))
        val p = ProjectScope.create(File("/home/subochev/projects/WORK/kwasm/tmp-wit"))
        val artifacts = p.getArtifacts(
//            packageName = PackageNode("wasi", "http", "0.2.5"),
            packageName = PackageNode("binom", "pipeline",null),
            worldName = "test"
        )
        val resolved = Resolver.resolve(artifacts)
//        resolved.types.forEach {
//            println("${it.pack}/${it.name} -> $it")
//        }
//        resolved.exportFunctions.forEach { t, u ->
//            println("EXPORT ${t.first} -> ${t.second}")
//        }
//        resolved.importFunctions.forEach { t, u ->
//            println("IMPORT ${t.first} -> ${t.second}")
//        }
        WasmGenerator(System.out,WasmGenerator.WordSize.X32).gen(resolved)
//        println(p)
//        println(artifacts)
//        val dd = p.get(PackageNode("wasi", "http", "0.2.5"), "proxy")
//        val exports = HashSet<InterfacePath>()
//        val imports = HashSet<InterfacePath>()
//        p.collectInterfaces(PackageNode("wasi", "http", "0.2.5"), "proxy", HashSet(), exports, imports)
//        println("Exports: $exports")
//        println("Imports: $imports")
    }
}

fun ProjectScope.Companion.create(direction: File): ProjectScope {
    val main = WitPackage.create(direction)
    val deps = direction.resolve("deps").listFiles()
        .filter { it.isDirectory }
        .map { file -> WitPackage.create(file) }
    return ProjectScope((listOf(main) + deps).associateBy { it.packageName })
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