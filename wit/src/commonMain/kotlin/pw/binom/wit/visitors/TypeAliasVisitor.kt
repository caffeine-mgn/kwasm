package pw.binom.wit.visitors

interface TypeAliasVisitor {
    fun start(name: String)
    fun type(): TypeVisitor
    fun end()
}