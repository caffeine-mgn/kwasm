package pw.binom.wit.node

import pw.binom.wit.visitors.OptionVisitor

data class OptionNode(var type: Type) {
    fun accept(visitor: OptionVisitor) {
        visitor.start()
        type.accept(visitor.type())
        visitor.end()
    }
}