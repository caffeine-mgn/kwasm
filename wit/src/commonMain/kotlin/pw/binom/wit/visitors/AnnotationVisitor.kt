package pw.binom.wit.visitors

interface AnnotationVisitor {
    companion object {
        val EMPTY = object : AnnotationVisitor {}
    }

    fun start(name: String) {}
    fun field(name: String, value: String) {}
    fun end() {}
}