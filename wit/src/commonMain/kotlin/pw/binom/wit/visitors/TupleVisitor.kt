package pw.binom.wit.visitors

interface TupleVisitor {
    companion object {
        val EMPTY: TupleVisitor = object : TupleVisitor {}
    }
    fun start() {}
    fun first(): TypeVisitor = TypeVisitor.EMPTY
    fun second(): TypeVisitor = TypeVisitor.EMPTY
    fun third(): TypeVisitor = TypeVisitor.EMPTY
    fun end() {}
}