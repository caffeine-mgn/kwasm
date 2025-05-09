package pw.binom.wit.node

import pw.binom.wit.visitors.*
import kotlin.js.JsName

sealed interface Type {
    private class ListVisitorVisitor(
        val subType: List,
        val result: (Type) -> Unit,
    ) : ListVisitor by subType {
        override fun end() {
            subType.end()
            result(subType)
        }
    }

    private class OptionVisitorVisitor(val subType: Option, val result: (Type) -> Unit) : OptionVisitor by subType {
        override fun end() {
            subType.end()
            result(subType)
        }
    }

    private class BorrowVisitorVisitor(val subType: Borrow, val result: (Type) -> Unit) : BorrowVisitor by subType {
        override fun end() {
            subType.end()
            result(subType)
        }
    }

    private class TupleVisitorVisitor(val subType: Tuple, val result: (Type) -> Unit) : TupleVisitor by subType {
        override fun end() {
            subType.end()
            result(subType)
        }
    }

    private class ResultVisitorVisitor(val subType: Result, val result: (Type) -> Unit) : ResultVisitor by subType {
        override fun end() {
            subType.end()
            result(subType)
        }
    }

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

        override fun result(): ResultVisitor = ResultVisitorVisitor(Result(Primitive.S8, Primitive.S8), result)

        override fun list(): ListVisitor = ListVisitorVisitor(List(Primitive.S8), result)

        override fun tuple(): TupleVisitor = TupleVisitorVisitor(Tuple(emptyList()), result)

        override fun option(): OptionVisitor = OptionVisitorVisitor(Option(Primitive.U8), result)

        override fun borrow(): BorrowVisitor = BorrowVisitorVisitor(Borrow(Primitive.U8), result)
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
        var elements: kotlin.collections.List<Type>,
    ) : Type, TupleVisitor {
        private var tmp: ArrayList<Type>? = null
        override fun start() {
            tmp = ArrayList()
        }

        override fun accept(visitor: TypeVisitor) {
            val v = visitor.tuple()
            v.start()
            elements.forEach {
                it.accept(v.element())
            }
            v.end()
        }

        override fun element(): TypeVisitor = Visitor {
            tmp!! += it
        }

        override fun end() {
            elements = tmp!!
            tmp = null
            super.end()
        }
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