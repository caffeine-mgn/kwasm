package pw.binom.wit.visitors

interface WorldElementVisitor {
    fun id(name: String)
    fun func(name: String): FuncVisitor
}