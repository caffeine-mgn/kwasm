package pw.binom.wit.writers

import pw.binom.wit.visitors.TypeAliasVisitor
import pw.binom.wit.visitors.TypeVisitor

class TypeAliasWriter(private val sb: TextWriter) : TypeAliasVisitor {
    companion object {
        private const val NONE = 0
        private const val START = 1
        private const val TYPE = 2
    }

    private var status = NONE

    override fun start(name: String) {
        check(status == NONE)
        status = START
        sb.append("type ").append(name)
    }

    override fun type(): TypeVisitor {
        check(status == START)
        status = TYPE
        sb.append(" = ")
        return TypeWriter(sb)
    }

    override fun end() {
        check(status == TYPE)
        status = NONE
    }
}