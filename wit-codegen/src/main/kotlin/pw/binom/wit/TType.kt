package pw.binom.wit

import pw.binom.wit.node.UseNode

sealed interface TType {
    data class Alias(val name: String) : TType
    data class Enum(val name: String) : TType
    data class Flags(val name: String) : TType
    data class Record(val name: String) : TType
    data class Variant(val name: String) : TType
    class Resource(val name: String) : TType
    data class ExternalUse(
        val ref: UseNode.Id,
        val typeName: String,
        val name: String,
    ) : TType
}