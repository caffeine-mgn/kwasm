package pw.binom.wit

interface StructVisitor {
    fun visit(name: String, struct: Struct)
}