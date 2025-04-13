package pw.binom.wit.writers

import pw.binom.wit.visitors.ListVisitor
import pw.binom.wit.visitors.TypeVisitor

class ListWriter(private val sb: TextWriter) : ListVisitor {
    companion object {
        private const val STATUS_NONE = 0
        private const val STATUS_STARED = 1
        private const val STATUS_TYPE = 2
    }

    private var status = STATUS_NONE
    override fun start() {
        check(status == STATUS_NONE)
        status = STATUS_STARED
        sb.append("list")
    }

    override fun type(): TypeVisitor {
        check(status == STATUS_STARED)
        status = STATUS_TYPE
        sb.append("<")
        return TypeWriter(sb)
    }

    override fun end() {
        check(status == STATUS_TYPE)
        status = STATUS_NONE
        sb.append(">")
    }

}