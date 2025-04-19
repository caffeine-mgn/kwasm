package pw.binom.wit.node

import pw.binom.wit.visitors.*

class ResourceNode(
    var name: String,
    var list: List<Item>,
    var annotations: List<AnnotationNode>,
) : ResourceVisitor, InterfaceElement {
    private var argsList: ArrayList<Item>? = null
    private var tmpAnnotations: ArrayList<AnnotationNode>? = null

    sealed interface Item {
        var annotations: List<AnnotationNode>

        class Constructor(val data: ConstructorNode, override var annotations: List<AnnotationNode>) : Item {
            override fun accept(visitor: ResourceVisitor) {
                data.accept(visitor.init())
            }
        }

        class Function(val data: FuncNode, override var annotations: List<AnnotationNode>) : Item {
            override fun accept(visitor: ResourceVisitor) {
                data.accept(visitor.func(data.name))
            }
        }

        class StaticFunction(val data: FuncNode, override var annotations: List<AnnotationNode>) : Item {
            override fun accept(visitor: ResourceVisitor) {
                data.accept(visitor.funcStatic(data.name))
            }
        }

        fun accept(visitor: ResourceVisitor)
    }

    fun accept(visitor: ResourceVisitor) {
        visitor.start(name)
        list.forEach {
            it.annotations.forEach {
                it.accept(visitor.annotation())
            }
            it.accept(visitor)
        }
        visitor.end()
    }

    override fun start(name: String) {
        this.name = name
        argsList = ArrayList()
        tmpAnnotations = ArrayList()
    }

    override fun init(): ConstructorVisitor {
        val r = ConstructorNode(emptyList())
        argsList!! += Item.Constructor(r, tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        return r
    }

    override fun annotation(): AnnotationVisitor {
        val r = AnnotationNode("", emptyList())
        tmpAnnotations!! += r
        return r
    }

    override fun func(name: String): FuncVisitor {
        val r = FuncNode(name, emptyList(), FunctionResult.VoidResult)
        argsList!! += Item.Function(r, tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        return r
    }

    override fun funcStatic(name: String): FuncVisitor {
        val r = FuncNode(name, emptyList(), FunctionResult.VoidResult)
        argsList!! += Item.StaticFunction(r, tmpAnnotations!!)
        tmpAnnotations = ArrayList()
        return r
    }

    override fun end() {
        list = argsList!!
        argsList = null
        tmpAnnotations = null
    }

    override fun accept(visitor: InterfaceVisitor) {
        annotations.forEach {
            it.accept(visitor.annotation())
        }
        accept(visitor.resource())
    }
}