package pw.binom.wit.node

import pw.binom.wit.visitors.InterfaceVisitor
import pw.binom.wit.visitors.RecordVisitor
import pw.binom.wit.visitors.TypeVisitor

data class RecordNode(
    var name: String,
    var fields: List<Pair<String, Type>>,
    var annotations: List<AnnotationNode>,
) : InterfaceElement, RecordVisitor {

    private var argsList: ArrayList<Pair<String, Type>>? = null

    fun accept(visitor: RecordVisitor) {
        visitor.start(name)
        fields.forEach { (name, type) ->
            type.accept(visitor.field(name))
        }
        visitor.end()
    }

    override fun accept(visitor: InterfaceVisitor) {
        annotations.forEach {
            it.accept(visitor.annotation())
        }
        accept(visitor.record())
    }

    override fun start(name: String) {
        this.name = name
        argsList = ArrayList()
    }

    override fun field(name: String): TypeVisitor = Type.Visitor { argsList!! += name to it }

    override fun end() {
        fields = argsList!!
        argsList = null
    }
}