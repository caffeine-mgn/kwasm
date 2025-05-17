package pw.binom.wit

import pw.binom.wit.node.*

class InterfaceScope(node: InterfaceNode, val parent: PackageScope) : Scope {
    val name = node.name

    private val internalFunctions = HashMap<String, Callable.Function>()
    private val internalAleases = HashMap<String, NonFinalType>()
    val types2: Map<String, NonFinalType>
        get() = internalAleases
    val functions: Map<String, Callable.Function>
        get() = internalFunctions


    init {
        val pack = parent.name
        node.elements.asSequence().forEach { el ->
                when (el) {
                    is EnumNode -> {
                        internalAleases[el.name] = FinalType.Enum(
                            elements = el.elements,
                            scope = this,
                            name = el.name,
                        )
                    }

                    is FlagsNode -> {
                        internalAleases[el.name] = FinalType.Flags(
                            elements = el.elements,
                            scope = this,
                            name = el.name,
                        )
                    }

                    is FuncNode -> {
                        internalFunctions[el.name] = el.toCallable(this)
                    }

                    is RecordNode -> {
                        val res = FinalType.Record(
                            fields = el.fields.map {
                                FinalType.Record.Field(
                                    name = it.first,
                                    type = it.second.toFinalType(this),
                                )
                            },
                            scope = this,
                            name = el.name,
                        )
                        internalAleases[el.name] = res
                    }

                    is ResourceNode -> {
                        val funcs = HashMap<String, Callable.Function>()
                        val staticFuncs = HashMap<String, Callable.Function>()
                        val constructors = ArrayList<Callable.Constructor>()
                        el.list.forEach {
                            when (val r = it) {
                                is ResourceNode.Item.Constructor -> constructors += r.data.toCallable(this)
                                is ResourceNode.Item.Function -> funcs[r.data.name] = r.data.toCallable(this)
                                is ResourceNode.Item.StaticFunction -> staticFuncs[r.data.name] =
                                    r.data.toCallable(this)
                            }
                        }
                        val res = FinalType.Resource(
                            functions = funcs,
                            staticFunctions = staticFuncs,
                            constructors = constructors,
                            scope = this,
                            name = el.name,
                        )
                        internalAleases[el.name] = res
                    }

                    is TypeAliasNode -> {
                        internalAleases[el.name] = NonFinalType.Alias(el.type.toFinalType(this))
                    }

                    is UseNode -> {
                        el.types.forEach { (name, alias) ->
                            val use = when (val name2 = el.name) {
                                is UseNode.Id.External -> NonFinalType.Use.External(
                                    packageName = name2.pack,
                                    interfaceName = name2.interfaceName,
                                    typeName = name,
                                    scope = this,
                                )

                                is UseNode.Id.Internal -> NonFinalType.Use.Internal(
                                    interfaceName = name2.name,
                                    typeName = name,
                                    scope = this,
                                )
                            }
                            internalAleases[alias ?: name] = use
                        }
                    }

                    is VariantNode -> {
                        val elements = el.items.associate {
                            if (it.type == null) {
                                it.name to FinalType.Variant.Element.Named
                            } else {
                                it.name to FinalType.Variant.Element.Typed(it.type!!.toFinalType(this))
                            }
                        }
                        val res = FinalType.Variant(
                            scope = this,
                            elements = elements,
                            name = el.name,
                        )
                        internalAleases[el.name] = res
                    }
                }
            }
    }

    fun resolve() {

    }

    override fun getType(name: String) =
        internalAleases[name] ?: TODO("Type ${parent.name}/${this.name}::$name not found")

    override fun getType(pack: Package, interfaceName: String, name: String): NonFinalType {
        if (pack == this.parent.name && interfaceName == name) {
            return getType(name)
        }
        return this.parent.getType(pack = pack, interfaceName = interfaceName, name = name)
    }

    fun accept(visitor: StructVisitor) {
        internalAleases.forEach { name, type ->
            if (type is Struct) {
                visitor.visit(name, type)
            }
        }
    }
}