package pw.binom.wit.visitors

interface WorldElementVisitor {
    fun id(name: String)
    fun func(name: String): FuncVisitor
    fun externalInterface(packageModule: String, packageName: String, interfaceName: String, version: String?)
}