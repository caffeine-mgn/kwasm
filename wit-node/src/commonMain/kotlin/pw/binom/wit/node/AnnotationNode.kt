package pw.binom.wit.node

import pw.binom.wit.visitors.AnnotationVisitor

class AnnotationNode(var name: String, var values: List<Pair<String, String>>) : AnnotationVisitor {
    private var argsList: ArrayList<Pair<String, String>>? = null
    override fun start(name: String) {
        argsList = ArrayList()
    }

    override fun field(name: String, value: String) {
        argsList!! += name to value
    }

    override fun end() {
        values = argsList!!
        argsList = null
    }
}