package pw.binom.wit.visitors

interface ListVisitor {
    fun start()
    fun type(): TypeVisitor
    fun end()
}