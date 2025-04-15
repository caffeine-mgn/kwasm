package pw.binom.wit.visitors

interface VariantVisitor : BaseVisitor {
    fun start(name: String)
    fun element(name: String)
    fun elementWithType(name: String): TypeVisitor
    fun end()
}