package pw.binom.wit.visitors

interface AnnotationVisitor {
    fun start(name: String)
    fun field(name: String, value: String)
    fun end()
}