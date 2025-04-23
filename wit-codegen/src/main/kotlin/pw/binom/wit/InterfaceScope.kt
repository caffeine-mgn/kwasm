package pw.binom.wit

import pw.binom.wit.node.*

class InterfaceScope(val node: InterfaceNode, val world: PackageScope) : Scope2 {
    private val internalTypes = HashMap<String, TType>()
    private val internalFunctions = HashSet<FuncNode>()
    val types: Map<String, TType>
        get() = internalTypes

    init {
        node.elements
            .asSequence()
            .forEach { el ->
                when (el) {
                    is EnumNode -> internalTypes[el.name] = TType.Enum(el.name)
                    is FlagsNode -> internalTypes[el.name] = TType.Flags(el.name)
                    is FuncNode -> internalFunctions += el
                    is RecordNode -> internalTypes[el.name] = TType.Record(el.name)
                    is ResourceNode -> internalTypes[el.name] = TType.Resource(el.name)
                    is TypeAliasNode -> internalTypes[el.name] = TType.Alias(el.name)
                    is UseNode -> {
                        el.types.forEach { (name, alias) ->
                            internalTypes[alias ?: name] = TType.ExternalUse(
                                ref = el.name,
                                typeName = name,
                                name = alias ?: name,
                            )
                        }
                    }

                    is VariantNode -> internalTypes[el.name] = TType.Record(el.name)
                }
            }
    }

    override fun findType(type: Type.Id): TType {
        TODO("Not yet implemented")
    }
}