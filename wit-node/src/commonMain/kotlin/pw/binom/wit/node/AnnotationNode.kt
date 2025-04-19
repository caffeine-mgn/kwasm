package pw.binom.wit.node

import pw.binom.wit.visitors.AnnotationVisitor

data class AnnotationNode(var name: String, var values: List<Pair<String, String>>) : AnnotationVisitor {
    private var argsList: ArrayList<Pair<String, String>>? = null
    override fun start(name: String) {
        argsList = ArrayList()
        this.name = name
    }

    override fun field(name: String, value: String) {
        argsList!! += name to value
    }

    override fun end() {
        values = argsList!!
        argsList = null
    }

    fun accept(visitor: AnnotationVisitor) {
        visitor.start(name)
        values.forEach { (key, value) ->
            visitor.field(name = key, value = value)
        }
        visitor.end()
    }
}