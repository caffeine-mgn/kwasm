package pw.binom.wit.node

import pw.binom.wit.visitors.ConstructorVisitor
import pw.binom.wit.visitors.ResourceVisitor
import pw.binom.wit.visitors.TypeVisitor

class ConstructorNode(
    var args: List<Pair<String, Type>>,
) : ConstructorVisitor {

    private var argsList: ArrayList<Pair<String, Type>>? = null

    fun accept(visitor: ResourceVisitor) {
        accept(visitor.init())
    }

    fun accept(visitor: ConstructorVisitor) {
        visitor.start()
        args.forEach { (name, type) ->
            type.accept(visitor.arg(name))
        }
        visitor.end()
    }

    override fun start() {
        argsList = ArrayList()
    }

    override fun arg(name: String): TypeVisitor =
        Type.Visitor { argsList!! += name to it }

    override fun end() {
        args = argsList!!
        argsList = null
    }
}