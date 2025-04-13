package pw.binom.wit.parser

import pw.binom.wit.Type

sealed interface InterfaceRoot {
    class TypeAlias(val name: String,val type: Type) : InterfaceRoot
}