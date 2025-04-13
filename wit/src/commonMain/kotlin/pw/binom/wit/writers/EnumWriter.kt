package pw.binom.wit.writers

import pw.binom.wit.visitors.EnumVisitor

class EnumWriter(val sb: TextWriter) : EnumVisitor {
    companion object {
        const val STATUS_NONE = 0
        const val STATUS_NAME = 1
        const val STATUS_ELEMENT = 2
    }

    private var status = STATUS_NONE

    override fun start(value: String) {
        check(status == STATUS_NONE)
        sb.append("enum ")
        .append(value).append(" {").levelInc()
        status = STATUS_NAME
    }

    override fun element(value: String) {
        if (status == STATUS_NAME) {
            sb.appendLine().append(value)
            status = STATUS_ELEMENT
            return
        }
        check(status == STATUS_ELEMENT)
        sb.append(",")
            .appendLine()
            .append(value)
    }

    override fun end() {
        check(status == STATUS_ELEMENT)
        sb.appendLine()
        sb.levelDec().append("}").appendLine()
        status = STATUS_NONE
    }
}