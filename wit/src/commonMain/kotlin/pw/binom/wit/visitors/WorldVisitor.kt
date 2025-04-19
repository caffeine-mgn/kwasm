package pw.binom.wit.visitors

interface WorldVisitor : BaseVisitor {
    fun start(name: String)
    fun import(): WorldElementVisitor
    fun export(): WorldElementVisitor
    fun annotation(): AnnotationVisitor
    fun include(worldName: String)
    fun include(packageName: String, packageField: String, interfaceName: String)
    fun include(packageName: String, packageField: String, interfaceName: String, version: String)
    fun end()
}