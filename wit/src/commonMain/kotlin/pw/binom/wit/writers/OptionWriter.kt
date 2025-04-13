package pw.binom.wit.writers

import pw.binom.wit.visitors.ListVisitor
import pw.binom.wit.visitors.OptionVisitor
import pw.binom.wit.visitors.TypeVisitor

class OptionWriter(private val sb: TextWriter) : OptionVisitor {
    companion object {
        private const val STATUS_NONE = 0
        private const val STATUS_STARED = 1
        private const val STATUS_TYPE = 2
    }

    private var status = STATUS_NONE
    override fun start() {
        check(status == STATUS_NONE)
        status = STATUS_STARED
        sb.append("option")
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