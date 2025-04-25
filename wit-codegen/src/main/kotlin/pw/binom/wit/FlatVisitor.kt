package pw.binom.wit

interface FlatVisitor {
    fun visit(name: String, type: FinalType.Primitive)
}