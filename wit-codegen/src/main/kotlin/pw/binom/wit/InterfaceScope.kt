package pw.binom.wit

import pw.binom.wit.node.*

class InterfaceScope(val node: InterfaceNode, val world: PackageScope) : Scope2 {
    private val types = HashMap<String, TType>()
    private val functions = HashSet<FuncNode>()

    init {
        node.elements
            .asSequence()
            .forEach { el ->
                when (el) {
                    is EnumNode -> TType.Enum(el.name)
                    is FlagsNode -> TType.Flags(el.name)
                    is FuncNode -> functions += el
                    is RecordNode -> TType.Record(el.name)
                    is ResourceNode -> TType.Resource(el.name)
                    is TypeAliasNode -> TType.Alias(el.name)
                    is UseNode -> {
                        el.types.forEach { (name, alias) ->
                            types[alias ?: name] = TType.ExternalUse(
                                ref = el.name,
                                typeName = name,
                                name = alias ?: name,
                            )
                        }
                    }

                    is VariantNode -> TType.Record(el.name)
                }
            }
    }

    override fun findType(type: Type.Id): TType {
        TODO("Not yet implemented")
    }
}