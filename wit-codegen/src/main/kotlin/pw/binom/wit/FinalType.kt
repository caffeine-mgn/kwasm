package pw.binom.wit

sealed interface FinalType : NonFinalType {
    sealed interface Primitive : FinalType {
        data object S8 : Primitive
        data object U8 : Primitive
        data object S16 : Primitive
        data object U16 : Primitive
        data object S32 : Primitive
        data object U32 : Primitive
        data object S64 : Primitive
        data object U64 : Primitive
        data object F32 : Primitive
        data object F64 : Primitive
        data object BOOL : Primitive
        data object STRING : Primitive
        data object CHAR : Primitive

        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
        }
    }

    data class Result(
        val first: NonFinalType?,
        val second: NonFinalType?,
        val scope: Scope,
    ) : FinalType {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
            first?.accept(visitor)
            second?.accept(visitor)
        }

    }

    data class Tuple(
        val types: List<NonFinalType>,
        val scope: Scope,
    ) : FinalType {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
            types.forEach { it.accept(visitor) }
        }
    }

    data class Enum(
        val elements: List<String>,
        override val scope: Scope,
        override val name: String,
    ) : FinalType, Struct {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
        }

    }

    data class Flags(
        val elements: List<String>,
        override val scope: Scope,
        override val name: String,
    ) : FinalType, Struct {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
        }
    }

    data class Record(
        val fields: List<Field>,
        override val scope: Scope,
        override val name: String,
    ) : FinalType, Struct {
        data class Field(val name: String, val type: NonFinalType)

        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
            fields.forEach { (_, type) ->
                type.accept(visitor)
            }
        }

        fun eachFields(prefix: String = ""): Sequence<Pair<String, NonFinalType>> =
            sequence {
                fields.forEach { (name, type) ->
                    val fieldName = if (prefix.isEmpty()) {
                        name
                    } else {
                        "$prefix.$name"
                    }
                    if (type is Record) {
                        yieldAll(type.eachFields("$fieldName."))
                    } else {
                        yield(fieldName to type)
                    }
                }
            }
    }

    data class Option(
        val type: NonFinalType,
        val scope: Scope,
    ) : FinalType {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
            type.accept(visitor)
        }
    }

    data class List2(
        val type: NonFinalType,
        val scope: Scope,
    ) : FinalType {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
            type.accept(visitor)
        }
    }

    data class Borrow(
        val type: NonFinalType,
        val scope: Scope,
    ) : FinalType {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
            type.accept(visitor)
        }
    }

    data class Resource(
        val functions: Map<String, Callable.Function>,
        val staticFunctions: Map<String, Callable.Function>,
        val constructors: List<Callable.Constructor>,
        override val scope: Scope,
        override val name: String,
    ) : FinalType, Scope, Struct {
        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
            functions.forEach { (_, func) ->
                func.accept(this, visitor)
            }
            staticFunctions.forEach { (_, func) ->
                func.accept(this, visitor)
            }
            constructors.forEach { func ->
                func.accept(scope, visitor)
            }
        }


        override fun getType(name: String): NonFinalType =
            scope.getType(name)

        override fun getType(pack: Package, interfaceName: String, name: String): NonFinalType =
            scope.getType(pack = pack, interfaceName = interfaceName, name = name)
    }

    data class Variant(
        override val scope: Scope,
        val elements: Map<String, Element>,
        override val name: String,
    ) : FinalType, Struct {
        sealed interface Element {
            data object Named : Element
            data class Typed(val type: NonFinalType) : Element
        }

        override fun accept(visitor: TypeVisitor) {
            visitor.visit(this)
            elements.forEach { (_, element) ->
                if (element is Element.Typed) {
                    element.type.accept(visitor)
                }
            }
        }
    }

    override fun resolve(): FinalType = this
}