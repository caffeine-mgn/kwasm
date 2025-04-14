package pw.binom.wit.writers

import pw.binom.wit.visitors.AnnotationVisitor

class AnnotationWriter(private val sb: TextWriter) : AnnotationVisitor {
    companion object {
        private const val NONE = 0
        private const val STARTED = 1
        private const val FIELDS = 2
    }

    private var status = NONE

    override fun start(name: String) {
        check(status == NONE)
        status = STARTED
        sb.append("@").append(name).append("(")
    }

    override fun field(name: String, value: String) {
        if (status == STARTED) {
            sb.append(name).append(" = ").append(value)
            status = FIELDS
            return
        }
        check(status == FIELDS)
        sb.append(", ").append(name).append(" = ").append(value)
    }

    override fun end() {
        check(status == STARTED || status == FIELDS)
        sb.append(")").appendLine()
        status = NONE
    }
}