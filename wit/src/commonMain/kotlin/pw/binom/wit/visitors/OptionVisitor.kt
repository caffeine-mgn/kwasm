package pw.binom.wit.visitors

interface OptionVisitor {
    companion object {
        val EMPTY: OptionVisitor = object : OptionVisitor {}
    }

    fun start() {}
    fun type(): TypeVisitor = TypeVisitor.EMPTY
    fun end() {}
}