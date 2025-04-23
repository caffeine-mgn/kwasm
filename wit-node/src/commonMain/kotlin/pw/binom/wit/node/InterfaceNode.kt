package pw.binom.wit.node

import pw.binom.wit.visitors.*

data class InterfaceNode(
    var name: String,
    var elements: List<InterfaceElement>,
    var annotations: List<AnnotationNode>,
) : WitElement, InterfaceVisitor {
    private var tmpElements: ArrayList<InterfaceElement>? = null
    private var tmpAnnotations: ArrayList<AnnotationNode>? = null
    fun accept(visitor: InterfaceVisitor) {
        visitor.start(name)
        elements.forEach {
            it.accept(visitor)
        }
        annotations.forEach {
            it.accept(visitor.annotation())
        }
        visitor.end()
    }

    override fun accept(visitor: WitVisitor) {
        accept(visitor.witInterface())
    }

    override fun start(name: String) {
        this.name = name
        tmpElements = ArrayList()
        tmpAnnotations = ArrayList()
    }

    override fun typeAlias(): TypeAliasVisitor {
        val f = TypeAliasNode("", Type.Primitive.S8)
        tmpElements!! += f
        return f
    }

    override fun annotation(): AnnotationVisitor {
        val r = AnnotationNode("", emptyList())
        tmpAnnotations!! += r
        return r
    }

    override fun record(): RecordVisitor {
        val r = RecordNode("", emptyMap(), tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        tmpElements!! += r
        return r
    }

    override fun flags(): FlagsVisitor {
        val r = FlagsNode("", emptyList(), tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        tmpElements!! += r
        return r
    }

    override fun use(): UseVisitor {
        val r = UseNode(UseNode.Id.Internal(""), emptyList(), tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        tmpElements!! += r
        return r
    }

    override fun enum(): EnumVisitor {
        val r = EnumNode("", emptyList(), tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        tmpElements!! += r
        return r
    }

    override fun resource(): ResourceVisitor {
        val r = ResourceNode("", emptyList(), tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        tmpElements!! += r
        return r
    }

    override fun variant(): VariantVisitor {
        val r = VariantNode("", emptyList(), tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        tmpElements!! += r
        return r
    }

    override fun func(name: String): FuncVisitor {
        val f = FuncNode(name, emptyList(), FunctionResult.VoidResult/*, tmpAnnotations!!*/)
        tmpAnnotations = ArrayList()
        tmpElements!! += f
        return f
    }

    override fun end() {
        annotations = tmpAnnotations!!
        tmpAnnotations = null
        elements = tmpElements!!
        tmpElements = null
    }
}