package pw.binom.wit.writers

import pw.binom.wit.visitors.InterfaceVisitor
import pw.binom.wit.visitors.PackageVisitor
import pw.binom.wit.visitors.WitVisitor
import pw.binom.wit.visitors.WorldVisitor

class WitWriter(sb: TextWriter) : WitVisitor {
    private val packageWriter = PackageWriter(sb)
    private val interfaceWriter = InterfaceWriter(sb)
    private val worldWriter = WorldWriter(sb)

    override fun witPackage(): PackageVisitor = packageWriter

    override fun witInterface(): InterfaceVisitor = interfaceWriter
    override fun world(): WorldVisitor = worldWriter
}