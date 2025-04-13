package pw.binom.wit.visitors

interface WitVisitor {
    fun witPackage(): PackageVisitor
    fun witInterface(): InterfaceVisitor
    fun world(): WorldVisitor
}