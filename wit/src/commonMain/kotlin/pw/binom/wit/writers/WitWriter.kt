package pw.binom.wit.writers

import pw.binom.wit.visitors.*

class WitWriter(private val sb: TextWriter) : WitVisitor {
    private val packageWriter = PackageWriter(sb)
    private val interfaceWriter = InterfaceWriter(sb)
    private val worldWriter = WorldWriter(sb)
    private val sinceWriter = AnnotationWriter(sb)
    override fun start() {
    }

    override fun lineComment(text: String) {
        sb.append("//").append(text).appendLine()
    }

    override fun annotation(): AnnotationVisitor = sinceWriter
    override fun multilineComment(text: String) {
        sb.append("/*").append(text).append("*/")
    }

    override fun witPackage(): PackageVisitor = packageWriter

    override fun witInterface(): InterfaceVisitor = interfaceWriter
    override fun world(): WorldVisitor = worldWriter
    override fun end() {
    }
}