package pw.binom.wit.writers

import pw.binom.wit.visitors.FlagsVisitor

class FlagsWriter(private val sb: TextWriter) : FlagsVisitor {
    companion object {
        private const val NONE = 0
        private const val STARTED = 1
        private const val ELEMENTS = 2
    }

    private var status = NONE
    override fun start(name: String) {
        check(status == NONE)
        sb.append("flags ").append(name).append(" {").appendLine().levelInc()
        status = STARTED
    }

    override fun element(value: String) {
        if (status == STARTED) {
            status = ELEMENTS
            sb.append(value)
            return
        }
        sb.append(",").appendLine().append(value)
    }

    override fun end() {
        check(status == STARTED || status == ELEMENTS)
        status = NONE
        sb.appendLine().append("}").levelDec().appendLine()

    }
}