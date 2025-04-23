package pw.binom.wit

import pw.binom.wit.node.Type

interface Scope2 {
    fun findType(type: Type.Id): TypeRef
}