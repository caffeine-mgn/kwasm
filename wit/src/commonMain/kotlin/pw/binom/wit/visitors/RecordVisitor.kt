package pw.binom.wit.visitors

interface RecordVisitor {
    fun start(name: String)
    fun field(name: String): TypeVisitor
    fun end()
}