package pw.binom.wit.parser

sealed interface FileRoot {
    class Package(val module: String, val field: String, val version: String?) : FileRoot
    class Interface(val name: String) : FileRoot
}