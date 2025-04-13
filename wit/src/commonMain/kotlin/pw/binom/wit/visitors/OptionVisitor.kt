package pw.binom.wit.visitors

interface OptionVisitor {
    fun start()
    fun type(): TypeVisitor
    fun end()
}