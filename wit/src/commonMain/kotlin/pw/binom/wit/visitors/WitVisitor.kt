package pw.binom.wit.visitors

interface WitVisitor : BaseVisitor {
    fun start()
    fun witPackage(): PackageVisitor
    fun witInterface(): InterfaceVisitor
    fun world(): WorldVisitor
    fun annotation(): AnnotationVisitor
    fun end()
}