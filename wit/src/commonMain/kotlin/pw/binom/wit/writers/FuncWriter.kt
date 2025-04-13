package pw.binom.wit.writers

import pw.binom.wit.visitors.FuncVisitor
import pw.binom.wit.visitors.MultipleReturnVisitor
import pw.binom.wit.visitors.TypeVisitor

class FuncWriter(private val sb: TextWriter) : FuncVisitor {
    companion object {
        private const val NONE = 0
        private const val STARTED = 1
        private const val ARGS = 2
        private const val RESULT = 3
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

    override fun result(): TypeVisitor {
        check(status == ARGS || status == STARTED)
        status = RESULT
        sb.append(") -> ")
        return TypeWriter(sb)
    }

    override fun resultMultiple(): MultipleReturnVisitor {
        check(status == ARGS || status == STARTED)
        status = RESULT
        sb.append(") -> ")
        return MultipleReturnWriter(sb)
    }

    override fun resultVoid() {
        check(status == ARGS || status == STARTED)
        status = RESULT
        sb.append(")")
    }

    override fun end() {
        check(status == RESULT)
        status = NONE
    }
}