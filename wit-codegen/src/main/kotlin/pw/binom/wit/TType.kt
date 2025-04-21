package pw.binom.wit

import pw.binom.wit.node.UseNode

sealed interface TType {
    class Alias(val name: String) : TType
    class Enum(val name: String) : TType
    class Flags(val name: String) : TType
    class Record(val name: String) : TType
    class Variant(val name: String) : TType
    class Resource(val name: String) : TType
    class ExternalUse(
        val ref: UseNode.Id,
        val typeName: String,
        val name: String,
    ) : TType
}