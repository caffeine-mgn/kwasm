package pw.binom.wit.writers

import pw.binom.wit.visitors.MultipleReturnVisitor
import pw.binom.wit.visitors.TypeVisitor

class MultipleReturnWriter(private val sb: TextWriter) : MultipleReturnVisitor {
    companion object {
        private const val NONE = 0
        private const val STARTED = 1
        private const val ELEMENTS = 2
    }

    private var status = NONE
    override fun start() {
        check(status == NONE)
        status = STARTED
        sb.append("(")
    }

    override fun arg(name: String): TypeVisitor {
        if (status == STARTED) {
            sb.append(name).append(": ")
            status = ELEMENTS
            return TypeWriter(sb)
        }
        check(status == ELEMENTS)
        sb.append(", ").append(name).append(": ")
        return TypeWriter(sb)
    }

    override fun end() {
        check(status == STARTED || status == ELEMENTS)
        status = NONE
        sb.append(")")
    }
}