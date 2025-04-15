package pw.binom.wit.writers

import pw.binom.wit.visitors.TypeVisitor
import pw.binom.wit.visitors.VariantVisitor

class VariantWriter(private val sb: TextWriter) : VariantVisitor {
    companion object {
        private const val NONE = 0
        private const val STARTED = 1
        private const val ELEMENTS = 2
    }

    private var status = NONE

    override fun start(name: String) {
        check(status == NONE)
        status = STARTED
        sb.append("variant ").append(name).append(" {").appendLine().levelInc()
    }

    private fun append(name: String) {
        if (status == STARTED) {
            sb.append(name)
            status = ELEMENTS
            return
        }
        check(status == ELEMENTS)
        sb.append(",").appendLine().append(name)
    }

    override fun element(name: String) {
        append(name)
    }

    override fun elementWithType(name: String): TypeVisitor {
        append(name)
        sb.append("(")
        val visitor = TypeWriter(sb)
        return object : TypeVisitor by visitor {
            override fun end() {
                visitor.end()
                sb.append(")")
            }
        }
    }

    override fun end() {
        check(status == STARTED || status == ELEMENTS)
        sb.appendLine()
            .levelDec()
            .append("}")
            .appendLine()
        status = NONE
    }
}