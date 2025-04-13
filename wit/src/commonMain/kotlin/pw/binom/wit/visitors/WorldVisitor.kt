package pw.binom.wit.visitors

interface WorldVisitor {
    fun start(name: String)
    fun import(): WorldElementVisitor
    fun export(): WorldElementVisitor
    fun end()
}