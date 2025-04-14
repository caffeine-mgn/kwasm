package pw.binom.wit.node

import pw.binom.wit.visitors.FuncVisitor
import pw.binom.wit.visitors.InterfaceVisitor
import pw.binom.wit.visitors.MultipleReturnVisitor
import pw.binom.wit.visitors.TypeVisitor
import kotlin.js.JsName

class FuncNode(
    var name: String,
    var args: List<Pair<String, Type>>,
    @JsName("result2")
    var result: FunctionResult,
) : InterfaceElement, FuncVisitor {

    private var argsList: ArrayList<Pair<String, Type>>? = null

    fun accept(visitor: FuncVisitor) {
        visitor.start()
        args.forEach { (name, type) ->
            type.accept(visitor.arg(name))
        }
        result.accept(visitor)
        visitor.end()
    }

    override fun accept(visitor: InterfaceVisitor) {
        accept(visitor.func(name))
    }


    override fun start() {
        argsList = ArrayList()
    }

    override fun arg(name: String): TypeVisitor =
        Type.Visitor { argsList!! += name to it }

    override fun result(): TypeVisitor =
        Type.Visitor { FunctionResult.SingleResult(it) }

    override fun resultMultiple(): MultipleReturnVisitor {
        val r = FunctionResult.Multiple(emptyList())
        result = r
        return r
    }

    override fun resultVoid() {
        result = FunctionResult.VoidResult
    }

    override fun end() {
        args = argsList!!
        argsList = null
    }

}