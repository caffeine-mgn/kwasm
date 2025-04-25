package pw.binom.wit

interface TypeVisitor {
    fun visit(type: FinalType)
}