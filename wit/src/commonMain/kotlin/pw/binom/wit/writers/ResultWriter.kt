package pw.binom.wit.writers

import pw.binom.wit.visitors.ResultVisitor
import pw.binom.wit.visitors.TypeVisitor

class ResultWriter(protected val sb: TextWriter) : ResultVisitor {
    companion object {
        private const val STATUS_NONE = 0
        private const val STATUS_STARTED = 1
        private const val STATUS_FIRST = 2
        private const val STATUS_SECOND = 3
    }

    private var status = STATUS_NONE

    override fun start() {
        check(status == STATUS_NONE)
        status = STATUS_STARTED
        sb.append("result")
    }

    override fun first(): TypeVisitor {
        check(status == STATUS_STARTED)
        status = STATUS_FIRST
        sb.append("<")
        return TypeWriter(sb)
    }

    override fun second(): TypeVisitor {
        check(status == STATUS_FIRST)
        sb.append(", ")
        status = STATUS_SECOND
        return TypeWriter(sb)
    }

    override fun end() {
        if (status == STATUS_STARTED) {
            status = STATUS_NONE
            return
        }
        check(status == STATUS_FIRST || status == STATUS_SECOND)
        sb.append(">")
        status = STATUS_NONE
    }
}