package pw.binom.wit.visitors

interface UseVisitor {
    fun start(name: String)
    fun type(name: String)
    fun end()
}