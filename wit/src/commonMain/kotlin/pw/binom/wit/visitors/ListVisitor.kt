package pw.binom.wit.visitors

interface ListVisitor {
    companion object {
        val EMPTY = object : ListVisitor {}
    }

    fun start() {}
    fun type(): TypeVisitor = TypeVisitor.EMPTY
    fun end() {}
}