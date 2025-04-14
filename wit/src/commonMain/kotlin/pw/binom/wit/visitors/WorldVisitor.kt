package pw.binom.wit.visitors

interface WorldVisitor : BaseVisitor {
    fun start(name: String)
    fun import(): WorldElementVisitor
    fun export(): WorldElementVisitor
    fun annotation(): AnnotationVisitor
    fun include(worldName: String)
    fun end()
}