package pw.binom.wit

sealed interface Type {
    data class List(val element: Type) : Type
    data class Option(val element: Type) : Type
    data class Result(val first: Type, val second: Type) : Type
    data class Tuple(val first: Type, val second: Type, val third: Type) : Type
    data class Field(val name: String, val type: Type)
    interface Ref : Type {
        val name: String
        data class Record(override val name: String) : Ref
        data class Variant(override val name: String, val types: kotlin.collections.List<Type>) : Ref
    }

    data class Enum(val name: String, val elements: kotlin.collections.List<String>) : Type

    sealed interface Primitive : Type {
        data object BOOL : Primitive
        data object S8 : Primitive
        data object S16 : Primitive
        data object S32 : Primitive
        data object S64 : Primitive
        data object U8 : Primitive
        data object U16 : Primitive
        data object U32 : Primitive
        data object U64 : Primitive
        data object F32 : Primitive
        data object F64 : Primitive
        data object CHAR : Primitive
        data object STRING : Primitive
    }
}