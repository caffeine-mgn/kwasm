package pw.binom.wit.node

import pw.binom.wit.visitors.FlagsVisitor
import pw.binom.wit.visitors.InterfaceVisitor

class FlagsNode(
    var name: String,
    var elements: List<String>,
    var annotations: List<AnnotationNode>,
) : FlagsVisitor, InterfaceElement {
    private var tmp: ArrayList<String>? = null

    fun accept(visitor: FlagsVisitor) {
        visitor.start(name)
        elements.forEach {
            visitor.element(it)
        }
        visitor.end()
    }

    override fun start(name: String) {
        tmp = ArrayList()
        this.name = name
    }

    override fun element(value: String) {
        tmp!! += value
    }

    override fun end() {
        elements = tmp!!
        tmp = null
    }

    override fun accept(visitor: InterfaceVisitor) {
        annotations.forEach {
            it.accept(visitor.annotation())
        }
        accept(visitor.flags())
    }
}