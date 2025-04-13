package pw.binom.wit.visitors

interface MultipleReturnVisitor {
    fun start()
    fun arg(name: String): TypeVisitor
    fun end()
}