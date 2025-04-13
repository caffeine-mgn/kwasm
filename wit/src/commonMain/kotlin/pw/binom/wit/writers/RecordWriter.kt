package pw.binom.wit.writers

import pw.binom.wit.visitors.RecordVisitor
import pw.binom.wit.visitors.TypeVisitor

class RecordWriter(private val sb: TextWriter) : RecordVisitor {
    companion object {
        private const val NONE = 0
        private const val NAME = 1
        private const val FIELDS = 2
    }

    private var status = NONE
    override fun start(name: String) {
        check(status == NONE)
        status = NAME
        sb.append("record ").append(name).append(" {").appendLine().levelInc()
    }

    override fun field(name: String): TypeVisitor {
        if (status == NAME) {
            sb.append(name).append(": ")
            status = FIELDS
            return TypeWriter(sb)
        }
        check(status == FIELDS)
        status = FIELDS
        sb.append(",").appendLine()
            .append(name).append(": ")
        return TypeWriter(sb)
    }

    override fun end() {
        check(status == NAME || status == FIELDS)
        if (status == FIELDS) {
            sb.appendLine()
        }
        status = NONE
        sb.levelDec().append("}").appendLine()
    }
}