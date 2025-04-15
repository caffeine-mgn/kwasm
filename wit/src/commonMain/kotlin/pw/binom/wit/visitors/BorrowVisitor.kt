package pw.binom.wit.visitors

interface BorrowVisitor {
    companion object {
        val EMPTY = object : BorrowVisitor {}
    }

    fun start() {}
    fun type(): TypeVisitor = TypeVisitor.EMPTY
    fun end() {}
}