package pw.binom.wit.visitors

interface EnumVisitor {
    fun start(value: String)
    fun element(value: String)
    fun end()
}