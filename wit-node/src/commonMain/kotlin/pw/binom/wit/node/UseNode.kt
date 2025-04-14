package pw.binom.wit.node

import pw.binom.wit.visitors.InterfaceVisitor
import pw.binom.wit.visitors.UseVisitor

data class UseNode(var name: Id, var types: List<Pair<String, String?>>) : UseVisitor, InterfaceElement {
    sealed interface Id {
        fun accept(visitor: UseVisitor)
        data class Internal(val name: String) : Id {
            override fun accept(visitor: UseVisitor) {
                visitor.start(name)
            }
        }

        data class External(
            val module: String,
            val field: String,
            val interfaceName: String,
            val version: String?,
        ) : Id {
            override fun accept(visitor: UseVisitor) {
                visitor.start(
                    module = module,
                    name = field,
                    interfaceName = interfaceName,
                    version = version,
                )
            }
        }
    }

    private var argsList: ArrayList<Pair<String, String?>>? = null
    fun accept(visitor: UseVisitor) {
        name.accept(visitor)
        types.forEach { (name, alias) ->
            if (alias == null) {
                visitor.type(name)
            } else {
                visitor.type(name, alias)
            }
        }
        visitor.end()
    }

    override fun accept(visitor: InterfaceVisitor) {
        accept(visitor.use())
    }

    override fun start(name: String) {
        this.name = Id.Internal(name)
        argsList = ArrayList()
    }

    override fun start(module: String, name: String, interfaceName: String, version: String?) {
        this.name = Id.External(
            module = module,
            field = name,
            interfaceName = interfaceName,
            version = version
        )
        argsList = ArrayList()
    }

    override fun type(name: String) {
        argsList!! += name to null
    }

    override fun type(name: String, alias: String) {
        argsList!! += name to alias
    }

    override fun end() {
        types = argsList!!
        argsList = null
    }
}