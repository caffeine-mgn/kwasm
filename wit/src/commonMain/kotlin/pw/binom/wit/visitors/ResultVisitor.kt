package pw.binom.wit.visitors

interface ResultVisitor {
    fun start()
    fun first():TypeVisitor
    fun second():TypeVisitor
    fun end()
}