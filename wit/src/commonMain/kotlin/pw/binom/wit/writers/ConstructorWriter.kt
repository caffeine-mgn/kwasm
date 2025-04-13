package pw.binom.wit.writers

import pw.binom.wit.visitors.ConstructorVisitor
import pw.binom.wit.visitors.TypeVisitor
import pw.binom.wit.writers.FuncWriter.Companion

class ConstructorWriter(private val sb: TextWriter) : ConstructorVisitor {

    companion object {
        private const val NONE = 0
        private const val STARTED = 1
        private const val ARGS = 2
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
            status = ARGS
            return TypeWriter(sb)
        }
        check(status == ARGS)
        sb.append(", ").append(name).append(": ")
        return TypeWriter(sb)
    }

    override fun end() {
        check(status == STARTED || status == ARGS)
        sb.append(")")
        status = NONE
    }
}