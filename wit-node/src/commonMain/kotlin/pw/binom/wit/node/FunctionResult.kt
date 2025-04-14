package pw.binom.wit.node

import pw.binom.wit.visitors.FuncVisitor
import pw.binom.wit.visitors.MultipleReturnVisitor
import pw.binom.wit.visitors.TypeVisitor

sealed interface FunctionResult {
    fun accept(visitor: FuncVisitor)

    data object VoidResult : FunctionResult {
        override fun accept(visitor: FuncVisitor) {
            visitor.resultVoid()
        }
    }

    data class SingleResult(val type: Type) : FunctionResult {
        override fun accept(visitor: FuncVisitor) {
            type.accept(visitor.result())
        }
    }

    class Multiple(var results: List<Pair<String, Type>>) : FunctionResult, MultipleReturnVisitor {

        private var argsList: ArrayList<Pair<String, Type>>? = null

        fun accept(visitor: MultipleReturnVisitor) {
            visitor.start()
            results.forEach { (name, type) ->
                type.accept(visitor.arg(name))
            }
            visitor.end()
        }

        override fun accept(visitor: FuncVisitor) {
            accept(visitor.resultMultiple())
        }

        override fun start() {
            argsList = ArrayList()
        }

        override fun arg(name: String): TypeVisitor = Type.Visitor { argsList!! += name to it }

        override fun end() {
            results = argsList!!
            argsList = null
        }
    }
}