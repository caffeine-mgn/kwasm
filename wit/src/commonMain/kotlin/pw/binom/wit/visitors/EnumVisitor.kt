package pw.binom.wit.visitors

interface EnumVisitor:BaseVisitor {
    fun start(value: String)
    fun element(value: String)
    fun end()
}