package pw.binom.wit.node

import pw.binom.wit.visitors.EnumVisitor
import pw.binom.wit.visitors.InterfaceVisitor

class EnumNode(var name: String, var elements: List<String>) : InterfaceElement, EnumVisitor {
    private var argsList: ArrayList<String>? = null

    fun accept(visitor: EnumVisitor) {
        visitor.start(name)
        elements.forEach {
            visitor.element(it)
        }
        visitor.end()
    }

    override fun accept(visitor: InterfaceVisitor) {
        accept(visitor.enum())
    }

    override fun start(value: String) {
        this.name = value
        argsList = ArrayList()
    }

    override fun element(value: String) {
        argsList!! += value
    }

    override fun end() {
        elements = argsList!!
        argsList = null
    }
}