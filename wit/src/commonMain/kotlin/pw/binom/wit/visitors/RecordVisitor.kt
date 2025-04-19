package pw.binom.wit.visitors

interface RecordVisitor:BaseVisitor {
    fun start(name: String)
    fun field(name: String): TypeVisitor
    fun end()
}