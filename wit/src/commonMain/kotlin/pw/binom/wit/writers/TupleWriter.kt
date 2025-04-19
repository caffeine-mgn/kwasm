package pw.binom.wit.writers

import pw.binom.wit.visitors.TupleVisitor
import pw.binom.wit.visitors.TypeVisitor

class TupleWriter(private val sb: TextWriter) : TupleVisitor {
    companion object {
        private const val STATUS_NONE = 0
        private const val STATUS_STARTED = 1
        private const val STATUS_ELEMENTS = 2
    }

    private var status = STATUS_NONE

    override fun start() {
        check(status == STATUS_NONE)
        status = STATUS_STARTED
        sb.append("tuple")
    }

    override fun element(): TypeVisitor {
        if (status == STATUS_STARTED) {
            sb.append("<")
            status = STATUS_ELEMENTS
            return TypeWriter(sb)
        }
        check(status == STATUS_ELEMENTS)
        sb.append(", ")
        return TypeWriter(sb)
    }

    override fun end() {
        check(status == STATUS_ELEMENTS)
        status = STATUS_NONE
        sb.append(">")
    }
}