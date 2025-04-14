package pw.binom.wit.visitors

interface ResultVisitor {
    companion object {
        val EMPTY = object : ResultVisitor {}
    }

    fun start() {}
    fun first(): TypeVisitor = TypeVisitor.EMPTY
    fun second(): TypeVisitor = TypeVisitor.EMPTY
    fun end() {}
}