package pw.binom.wit.node

import pw.binom.wit.visitors.*

class ResourceNode(var name: String, var list: List<Item>) : ResourceVisitor, InterfaceElement {
    private var argsList: ArrayList<Item>? = null

    sealed interface Item {
        class Constructor(val data: ConstructorNode) : Item {
            override fun accept(visitor: ResourceVisitor) {
                data.accept(visitor.init())
            }
        }

        class Function(val data: FuncNode) : Item {
            override fun accept(visitor: ResourceVisitor) {
                data.accept(visitor.func(data.name))
            }
        }

        class StaticFunction(val data: FuncNode) : Item {
            override fun accept(visitor: ResourceVisitor) {
                data.accept(visitor.funcStatic(data.name))
            }
        }

        fun accept(visitor: ResourceVisitor)
    }

    fun accept(visitor: ResourceVisitor) {
        visitor.start(name)
        list.forEach {
            it.accept(visitor)
        }
        visitor.end()
    }

    override fun start(name: String) {
        this.name = name
        argsList = ArrayList()
    }

    override fun init(): ConstructorVisitor {
        val r = ConstructorNode(emptyList())
        argsList!! += Item.Constructor(r)
        return r
    }

    override fun annotation(): AnnotationVisitor = AnnotationVisitor.EMPTY

    override fun func(name: String): FuncVisitor {
        val r = FuncNode(name, emptyList(), FunctionResult.VoidResult)
        argsList!! += Item.Function(r)
        return r
    }

    override fun funcStatic(name: String): FuncVisitor {
        val r = FuncNode(name, emptyList(), FunctionResult.VoidResult)
        argsList!! += Item.StaticFunction(r)
        return r
    }

    override fun end() {
        list = argsList!!
        argsList = null
    }

    override fun accept(visitor: InterfaceVisitor) {
        accept(visitor.resource())
    }
}