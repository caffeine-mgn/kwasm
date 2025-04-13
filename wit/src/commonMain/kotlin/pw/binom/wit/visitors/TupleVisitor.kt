package pw.binom.wit.visitors

interface TupleVisitor {
    fun start()
    fun first(): TypeVisitor
    fun second(): TypeVisitor
    fun third(): TypeVisitor
    fun end()
}