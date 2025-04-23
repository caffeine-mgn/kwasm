package pw.binom.wit.node

import pw.binom.wit.visitors.InterfaceVisitor
import pw.binom.wit.visitors.TypeAliasVisitor
import pw.binom.wit.visitors.TypeVisitor
import kotlin.js.JsName

data class TypeAliasNode(
    var name: String,
    @JsName("type2") var type: Type,
) : InterfaceElement, TypeAliasVisitor {
    fun accept(visitor: TypeAliasVisitor) {
        visitor.start(name)
        type.accept(visitor.type())
        visitor.end()
    }

    override fun start(name: String) {
        this.name = name
    }

    override fun type(): TypeVisitor = Type.Visitor { type = it }

    override fun end() {
    }

    override fun accept(visitor: InterfaceVisitor) {
        accept(visitor.typeAlias())
    }
}