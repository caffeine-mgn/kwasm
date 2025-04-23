package pw.binom.wit

import pw.binom.wit.node.UseNode

sealed interface TypeRef {
    data class Alias(val name: String) : TypeRef
    data class Enum(val name: String) : TypeRef
    data class Flags(val name: String) : TypeRef
    data class Record(val name: String) : TypeRef
    data class Variant(val name: String) : TypeRef
    class Resource(val name: String) : TypeRef
    data class ExternalUse(
        val ref: UseNode.Id,
        val typeName: String,
        val name: String,
    ) : TypeRef
}