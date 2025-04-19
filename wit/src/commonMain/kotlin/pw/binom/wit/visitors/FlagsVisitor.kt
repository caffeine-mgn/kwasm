package pw.binom.wit.visitors

interface FlagsVisitor : BaseVisitor {
    fun start(name: String)
    fun element(value: String)
    fun end()
}