package pw.binom.wit

sealed interface Scope {
    fun getType(name: String): NonFinalType
    fun getType(pack: Package, interfaceName: String, name: String): NonFinalType
}