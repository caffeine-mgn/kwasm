package pw.binom.wit.node

import pw.binom.wit.visitors.InterfaceVisitor
import pw.binom.wit.visitors.TypeVisitor
import pw.binom.wit.visitors.VariantVisitor

data class VariantNode(
    var name: String,
    var items: List<Item>,
    var annotations: List<AnnotationNode>,
) : VariantVisitor, InterfaceElement {
    class Item(val name: String, val type: Type?) {
        fun accept(visitor: VariantVisitor) {
            if (type == null) {
                visitor.element(name)
            } else {
                type.accept(visitor.elementWithType(name))
            }
        }
    }

    private var tmpList: ArrayList<Item>? = null

    fun accept(visitor: VariantVisitor) {
        visitor.start(name)
        visitor.end()
    }

    override fun accept(visitor: InterfaceVisitor) {
        annotations.forEach {
            it.accept(visitor.annotation())
        }
        accept(visitor.variant())
    }

    override fun start(name: String) {
        this.name = name
        tmpList = ArrayList()
    }

    override fun element(name: String) {
        tmpList!! += Item(name = name, type = null)
    }

    override fun elementWithType(name: String): TypeVisitor =
        Type.Visitor {
            tmpList!! += Item(name = name, type = it)
        }

    override fun end() {
        items = tmpList!!
        tmpList = null
    }
}