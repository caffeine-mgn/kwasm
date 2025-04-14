package pw.binom.wit.node

import pw.binom.wit.visitors.*

class InterfaceNode(var name: String, var elements: List<InterfaceElement>) : WitElement, InterfaceVisitor {
    private var argsList: ArrayList<InterfaceElement>? = null
    private val annotations = ArrayList<AnnotationNode>()
    fun accept(visitor: InterfaceVisitor) {
        visitor.start(name)
        elements.forEach {
            it.accept(visitor)
        }
        visitor.end()
    }

    override fun accept(visitor: WitVisitor) {
        accept(visitor.witInterface())
    }

    override fun start(name: String) {
        this.name = name
        argsList = ArrayList()
    }

    override fun typeAlias(): TypeAliasVisitor {
        val f = TypeAliasNode("", Type.Primitive.S8)
        argsList!! += f
        return f
    }

    override fun annotation(): AnnotationVisitor {
        val r = AnnotationNode("", emptyList())
        annotations += r
        return r
    }

    override fun record(): RecordVisitor {
        val r = RecordNode("", emptyList())
        argsList!! += r
        return r
    }

    override fun use(): UseVisitor {
        val r = UseNode("", emptyList())
        argsList!! += r
        return r
    }

    override fun enum(): EnumVisitor {
        val r = EnumNode("", emptyList())
        argsList!! += r
        return r
    }

    override fun resource(): ResourceVisitor {
        val r = ResourceNode("", emptyList())
        argsList!! += r
        return r
    }

    override fun variant(): VariantVisitor {
        TODO("Not yet implemented")
    }

    override fun func(name: String): FuncVisitor {
        val f = FuncNode(name, emptyList(), FunctionResult.VoidResult)
        argsList!! += f
        return f
    }

    override fun end() {
        elements = argsList!!
        argsList = null
    }
}