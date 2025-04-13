package pw.binom.wit.visitors

interface PackageVisitor {
    fun start()
    fun moduleName(value: String)
    fun fieldName(value: String)
    fun version(value: String)
    fun end()
}
