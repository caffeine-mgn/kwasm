package pw.binom.wit

import pw.binom.wit.node.*

class InterfaceScope(val node: InterfaceNode, val world: PackageScope) : Scope2 {
    private val internalTypes = HashMap<String, TypeRef>()
    private val internalFunctions = HashSet<FuncNode>()
    val types: Map<String, TypeRef>
        get() = internalTypes

    init {
        node.elements
            .asSequence()
            .forEach { el ->
                when (el) {
                    is EnumNode -> internalTypes[el.name] = TypeRef.Enum(el.name)
                    is FlagsNode -> internalTypes[el.name] = TypeRef.Flags(el.name)
                    is FuncNode -> internalFunctions += el
                    is RecordNode -> {
                        internalTypes[el.name] = TypeRef.Record(el.name)
                    }
                    is ResourceNode -> internalTypes[el.name] = TypeRef.Resource(el.name)
                    is TypeAliasNode -> internalTypes[el.name] = TypeRef.Alias(el.name)
                    is UseNode -> {
                        el.types.forEach { (name, alias) ->
                            internalTypes[alias ?: name] = TypeRef.ExternalUse(
                                ref = el.name,
                                typeName = name,
                                name = alias ?: name,
                            )
                        }
                    }

                    is VariantNode -> internalTypes[el.name] = TypeRef.Variant(el.name)
                }
            }
    }

    fun resolve(){

    }

    override fun findType(type: Type.Id): TypeRef {
        TODO("Not yet implemented")
    }
}