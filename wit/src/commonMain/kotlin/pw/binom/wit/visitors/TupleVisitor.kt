package pw.binom.wit.visitors

interface TupleVisitor {
    companion object {
        val EMPTY: TupleVisitor = object : TupleVisitor {}
    }
    fun start() {}
    fun element(): TypeVisitor = TypeVisitor.EMPTY
    fun end() {}
}