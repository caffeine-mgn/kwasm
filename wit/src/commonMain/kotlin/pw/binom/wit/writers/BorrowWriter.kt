package pw.binom.wit.writers

import pw.binom.wit.visitors.BorrowVisitor
import pw.binom.wit.visitors.TypeVisitor

class BorrowWriter(private val sb: TextWriter) : BorrowVisitor {
    companion object {
        const val NONE = 0
        const val STARTED = 1
        const val TYPE = 2
    }

    private var status = NONE

    override fun start() {
        check(status == NONE)
        status = STARTED
        sb.append("borrow<")
    }

    override fun type(): TypeVisitor {
        check(status == STARTED)
        status = TYPE
        return TypeWriter(sb)
    }

    override fun end() {
        check(status == TYPE)
        status = NONE
        sb.append(">")
    }
}