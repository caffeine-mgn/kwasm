package pw.binom.wit.visitors

interface ConstructorVisitor {
    fun start()
    fun arg(name: String): TypeVisitor
    fun end()
}