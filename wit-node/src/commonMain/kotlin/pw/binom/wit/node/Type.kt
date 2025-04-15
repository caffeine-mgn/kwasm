package pw.binom.wit.node

import pw.binom.wit.visitors.*
import kotlin.js.JsName

sealed interface Type {
    class Visitor(
        @JsName("result2")
        val result: (Type) -> Unit,
    ) : TypeVisitor {

        override fun u8() {
            result(Primitive.U8)
        }

        override fun s8() {
            result(Primitive.S8)
        }

        override fun u16() {
            result(Primitive.U16)
        }

        override fun s16() {
            result(Primitive.S16)
        }

        override fun u32() {
            result(Primitive.U32)
        }

        override fun s32() {
            result(Primitive.S32)
        }

        override fun u64() {
            result(Primitive.U64)
        }

        override fun s64() {
            result(Primitive.S64)
        }

        override fun f32() {
            result(Primitive.F32)
        }

        override fun f64() {
            result(Primitive.F64)
        }

        override fun string() {
            result(Primitive.STRING)
        }

        override fun bool() {
            result(Primitive.BOOL)
        }

        override fun char() {
            result(Primitive.CHAR)
        }

        override fun something() {
            result(Something)
        }

        override fun id(value: String) {
            result(Id(value))
        }

        override fun result(): ResultVisitor = Result(Primitive.S8, Primitive.S8)

        override fun list(): ListVisitor = List(Primitive.S8)

        override fun tuple(): TupleVisitor = Tuple(Primitive.S8, Primitive.S8, Primitive.S8)

        override fun option(): OptionVisitor = Option(Primitive.U8)

        override fun borrow(): BorrowVisitor = Borrow(Primitive.U8)
    }

    fun accept(visitor: TypeVisitor)
    data class List(var element: Type) : Type, ListVisitor {
        override fun accept(visitor: TypeVisitor) {
            val v = visitor.list()
            v.start()
            element.accept(v.type())
            v.end()
        }

        override fun type(): TypeVisitor = Visitor { element = it }
    }

    data class Borrow(var element: Type) : Type, BorrowVisitor {
        override fun accept(visitor: TypeVisitor) {
            val v = visitor.borrow()
            v.start()
            element.accept(v.type())
            v.end()
        }

        override fun type(): TypeVisitor = Visitor { element = it }
    }

    data class Option(var element: Type) : Type, OptionVisitor {
        override fun accept(visitor: TypeVisitor) {
            val v = visitor.option()
            v.start()
            element.accept(v.type())
            v.end()
        }

        override fun type(): TypeVisitor = Visitor { element = it }
    }

    data class Result(@JsName("first2") var first: Type?, @JsName("second2") var second: Type?) : Type, ResultVisitor {
        override fun start() {
            first = null
            second = null
        }

        override fun accept(visitor: TypeVisitor) {
            val v = visitor.result()
            v.start()
            first?.accept(v.first())
            second?.accept(v.second())
            v.end()
        }

        override fun first(): TypeVisitor = Visitor { first = it }
        override fun second(): TypeVisitor = Visitor { second = it }
    }

    data class Tuple(
        @JsName("first2")
        var first: Type,
        @JsName("second2")
        var second: Type,
        @JsName("third2")
        var third: Type?,
    ) : Type, TupleVisitor {
        override fun start() {
            third = null
        }

        override fun accept(visitor: TypeVisitor) {
            val v = visitor.tuple()
            v.start()
            first.accept(v.first())
            second.accept(v.second())
            third?.accept(v.third())
            v.end()
        }

        override fun first(): TypeVisitor = Visitor { first = it }
        override fun second(): TypeVisitor = Visitor { second = it }
        override fun third(): TypeVisitor = Visitor { third = it }
    }

    data class Id(val name: String) : Type {
        override fun accept(visitor: TypeVisitor) {
            visitor.id(name)
        }
    }

    data object Something : Type {
        override fun accept(visitor: TypeVisitor) {
            visitor.something()
        }

    }

    sealed interface Primitive : Type {
        data object BOOL : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.bool()
            }
        }

        data object S8 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.s8()
            }
        }

        data object S16 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.s16()
            }
        }

        data object S32 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.s16()
            }
        }

        data object S64 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.s32()
            }
        }

        data object U8 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.u8()
            }
        }

        data object U16 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.u16()
            }
        }

        data object U32 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.u32()
            }
        }

        data object U64 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.u64()
            }
        }

        data object F32 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.f32()
            }
        }

        data object F64 : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.f64()
            }
        }

        data object CHAR : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.char()
            }
        }

        data object STRING : Primitive {
            override fun accept(visitor: TypeVisitor) {
                visitor.string()
            }
        }
    }
}