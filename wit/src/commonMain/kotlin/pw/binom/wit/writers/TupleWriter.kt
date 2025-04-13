package pw.binom.wit.writers

import pw.binom.wit.visitors.TupleVisitor
import pw.binom.wit.visitors.TypeVisitor

class TupleWriter(private val sb: TextWriter) : TupleVisitor {
    companion object {
        private const val STATUS_NONE = 0
        private const val STATUS_STARTED = 1
        private const val STATUS_FIRST = 2
        private const val STATUS_SECOND = 3
        private const val STATUS_THIRD = 4
    }

    private var status = STATUS_NONE

    override fun start() {
        check(status == STATUS_NONE)
        status = STATUS_STARTED
        sb.append("tuple")
    }

    override fun first(): TypeVisitor {
        check(status == STATUS_STARTED)
        status = STATUS_FIRST
        sb.append("<")
        return TypeWriter(sb)
    }

    override fun second(): TypeVisitor {
        check(status == STATUS_FIRST)
        status = STATUS_SECOND
        sb.append(",")
        return TypeWriter(sb)
    }

    override fun third(): TypeVisitor {
        check(status == STATUS_SECOND)
        status = STATUS_THIRD
        return TypeWriter(sb)
    }

    override fun end() {
        check(status == STATUS_THIRD)
        status = STATUS_NONE
        sb.append(">")
    }
}